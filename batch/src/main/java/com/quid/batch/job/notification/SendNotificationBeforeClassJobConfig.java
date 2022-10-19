package com.quid.batch.job.notification;

import com.quid.batch.booking.model.Booking;
import com.quid.batch.notification.model.Notification;
import com.quid.batch.notification.model.NotificationEvent;
import com.quid.batch.notification.model.NotificationModelMapper;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class SendNotificationBeforeClassJobConfig {

    private final int CHUNK_SIZE = 10;

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    private final SendNotificationItemWriter sendNotificationItemWriter;

    @Bean
    public Job sendNotiJob() {
        return this.jobBuilderFactory.get("sendNotiJob")
            .start(addNotiStep())
            .next(sendNotiStep())
            .build();
    }

    @Bean
    public Step addNotiStep() {
        return this.stepBuilderFactory.get("addNotiStep")
            .<Booking, Notification>chunk(CHUNK_SIZE).reader(addNotiItemReader())
            .processor(addNotificationItemProcessor()).writer(addNotificationItemWriter()).build();
    }

    private JpaPagingItemReader<Booking> addNotiItemReader() {
        return new JpaPagingItemReaderBuilder<Booking>()
            .name("addNotiItemReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString(
                "select b from Booking b join fetch b.user where b.status = :status and b.startedAt <= :startedAt order by b.bookingSeq")
            .build();
    }

    @Bean
    public ItemProcessor<Booking, Notification> addNotificationItemProcessor() {
        return booking -> NotificationModelMapper.INSTANCE.toNotificationEntity(booking,
            NotificationEvent.BEFORE_CLASS);
    }

    @Bean
    public JpaItemWriter<Notification> addNotificationItemWriter() {
        return new JpaItemWriterBuilder<Notification>()
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

    @Bean
    public Step sendNotiStep() {
        return this.stepBuilderFactory.get("sendNotificationStep")
            .<Notification, Notification>chunk(CHUNK_SIZE)
            .reader(sendNotificationItemReader())
            .writer(sendNotificationItemWriter)
            .taskExecutor(new SimpleAsyncTaskExecutor()) // 가장 간단한 멀티쓰레드 TaskExecutor를 선언하였습니다.
            .build();
    }

    @Bean
    public SynchronizedItemStreamReader<Notification> sendNotificationItemReader() {
        JpaCursorItemReader<Notification> itemReader = new JpaCursorItemReaderBuilder<Notification>()
            .name("sendNotificationItemReader")
            .entityManagerFactory(entityManagerFactory)
            // 이벤트(event)가 수업 전이며, 발송 여부(sent)가 미발송인 알람이 조회 대상이 됩니다.
            .queryString("select n from NotificationEntity n where n.event = :event and n.sent = :sent")
            .parameterValues(Map.of("event", NotificationEvent.BEFORE_CLASS, "sent", false))
            .build();

        return new SynchronizedItemStreamReaderBuilder<Notification>()
            .delegate(itemReader)
            .build();

    }

}

package com.quid.batch.job;

import com.quid.batch.pass.entity.Pass;
import com.quid.batch.pass.entity.PassStatus;
import java.time.LocalDateTime;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExpiredPassesJobConfig {

    private final int CHUNK_SIZE = 5;

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;


    @Bean
    public Job expiredPassesJob() {
        return jobBuilderFactory.get("expiredPassesJob").start(expiredPassesStep())
            .build();
    }

    @Bean
    public Step expiredPassesStep() {
        return stepBuilderFactory.get("expiredPassesStep").<Pass, Pass>chunk(CHUNK_SIZE)
            .reader(expirePassesItemReader())
            .processor(expirePassesItemProcessor())
            .writer(expirePassesItemWriter())
            .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<Pass> expirePassesItemReader() {
        return new JpaCursorItemReaderBuilder<Pass>()
            .name("expirePassesItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select p from Pass p where p.status = :status and p.endedAt <= :endedAt")
            .parameterValues(
                Map.of("status", PassStatus.PROGRESSED, "endedAt", LocalDateTime.now()))
            .build();
    }

    @Bean
    public ItemProcessor<Pass, Pass> expirePassesItemProcessor() {
        return pass -> pass.expire();
    }

    @Bean
    public JpaItemWriter<Pass> expirePassesItemWriter() {
        return new JpaItemWriterBuilder<Pass>()
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

}

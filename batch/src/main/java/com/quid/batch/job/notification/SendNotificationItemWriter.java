package com.quid.batch.job.notification;

import com.quid.batch.notification.model.Notification;
import com.quid.batch.notification.repository.NotificationRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendNotificationItemWriter implements ItemWriter<Notification> {
    private final NotificationRepository notificationRepository;
//    private final KakaoTalkMessageAdapter kakaoTalkMessageAdapter;

    @Override
    public void write(List<? extends Notification> notificationEntities) throws Exception {
        int count = 0;

        for (Notification notificationEntity : notificationEntities) {
//            boolean successful = kakaoTalkMessageAdapter.sendKakaoTalkMessage(notificationEntity.getUuid(), notificationEntity.getText());
            boolean successful = true;

            if (successful) {
                notificationEntity.setSent(true);
                notificationEntity.setSentAt(LocalDateTime.now());
                notificationRepository.save(notificationEntity);
                count++;
            }

        }
        log.info("SendNotificationItemWriter - write: 수업 전 알람 {}/{}건 전송 성공", count, notificationEntities.size());

    }

}
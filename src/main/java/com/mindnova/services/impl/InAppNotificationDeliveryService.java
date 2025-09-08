package com.mindnova.services.impl;

import com.mindnova.common.NotificationChannelEnum;
import com.mindnova.common.NotificationStatusEnum;
import com.mindnova.dto.NotificationEventDto;
import com.mindnova.entities.Notification;
import com.mindnova.entities.NotificationDelivery;
import com.mindnova.entities.User;
import com.mindnova.repositories.NotificationDeliveryRepository;
import com.mindnova.repositories.NotificationRepository;
import com.mindnova.repositories.UserRepository;
import com.mindnova.services.NotificationDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class InAppNotificationDeliveryService implements NotificationDeliveryService {

    private final NotificationRepository notificationRepository;
    private final NotificationDeliveryRepository notificationDeliveryRepository;
    private final UserRepository userRepository;

    @Override
    public NotificationChannelEnum getChannel() {
        return NotificationChannelEnum.IN_APP;
    }

    @Override
    public void send(NotificationEventDto dto) {
        try {
            User user = userRepository.getReferenceById(dto.userId());

            Notification notification = Notification.builder()
                    .user(user)
                    .title(dto.title())
                    .content(dto.content())
                    .isRead(false)
                    .createdAt(Instant.now())
                    .build();

            notificationRepository.save(notification);

            NotificationDelivery delivery = NotificationDelivery.builder()
                    .notification(notification)
                    .user(user)
                    .channel(NotificationChannelEnum.IN_APP)
                    .status(NotificationStatusEnum.SENT)
                    .sentAt(Instant.now())
                    .build();

            notificationDeliveryRepository.save(delivery);

            System.out.printf("🔔 [In-App] Saved notification for user=%d | title=%s%n",
                    dto.userId(), dto.title());

        } catch (Exception e) {
            User user = userRepository.getReferenceById(dto.userId());
            NotificationDelivery delivery = NotificationDelivery.builder()
                    .user(user)
                    .channel(NotificationChannelEnum.IN_APP)
                    .status(NotificationStatusEnum.FAILED)
                    .errorMessage(e.getMessage())
                    .sentAt(Instant.now())
                    .build();

            notificationDeliveryRepository.save(delivery);

            System.err.printf("❌ [In-App] Failed to send notification for user=%d | title=%s | error=%s%n",
                    dto.userId(), dto.title(), e.getMessage());
        }
    }
}

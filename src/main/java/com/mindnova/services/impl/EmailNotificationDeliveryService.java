package com.mindnova.services.impl;

import com.mindnova.common.NotificationChannelEnum;
import com.mindnova.common.NotificationStatusEnum;
import com.mindnova.dto.NotificationEventDto;
import com.mindnova.entities.NotificationDelivery;
import com.mindnova.entities.User;
import com.mindnova.repositories.NotificationDeliveryRepository;
import com.mindnova.repositories.UserRepository;
import com.mindnova.services.NotificationDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class EmailNotificationDeliveryService implements NotificationDeliveryService {
    private final NotificationDeliveryRepository notificationDeliveryRepository;
    private final UserRepository userRepository;
    @Override
    public NotificationChannelEnum getChannel() {
        return NotificationChannelEnum.EMAIL;
    }

    @Override
    public void send(NotificationEventDto dto) {
        String email = dto.userEmail();
        try {
            // Thay phần mock bằng logic thật
            // emailSender.send(email, dto.title(), dto.content());

            System.out.printf("[Mock Email] To: %s | Subject: %s | Content: %s%n", email, dto.title(), dto.content());
            // Log delivery
            NotificationDelivery delivery = NotificationDelivery.builder()
                    .user(userRepository.getReferenceById(dto.userId()))
                    .channel(NotificationChannelEnum.EMAIL)
                    .status(NotificationStatusEnum.SENT)
                    .sentAt(Instant.now())
                    .build();

            notificationDeliveryRepository.save(delivery);
        } catch (Exception e) {
            NotificationDelivery delivery = NotificationDelivery.builder()
                    .user(userRepository.getReferenceById(dto.userId()))
                    .channel(NotificationChannelEnum.EMAIL)
                    .status(NotificationStatusEnum.FAILED)
                    .errorMessage(e.getMessage())
                    .sentAt(Instant.now())
                    .build();
            notificationDeliveryRepository.save(delivery);
        }
    }

}

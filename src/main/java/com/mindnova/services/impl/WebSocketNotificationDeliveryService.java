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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketNotificationDeliveryService implements NotificationDeliveryService {

    private final NotificationDeliveryRepository notificationDeliveryRepository;
    private final UserRepository userRepository;

    @Override
    public NotificationChannelEnum getChannel() {
        return NotificationChannelEnum.WEB_SOCKET;
    }

    @Override
    public void send(NotificationEventDto dto) {
        User user = userRepository.getReferenceById(dto.userId());

        try {
            // Logic gửi WebSocket (mock)
            log.info("[Mock WebSocket] Push notification to userId={} | title={} | content={}",
                    dto.userId(), dto.title(), dto.content());

            // Log thành công
            NotificationDelivery delivery = NotificationDelivery.builder()
                    .user(user)
                    .channel(NotificationChannelEnum.WEB_SOCKET)
                    .status(NotificationStatusEnum.SENT)
                    .sentAt(Instant.now())
                    .build();

            notificationDeliveryRepository.save(delivery);

        } catch (Exception e) {
            NotificationDelivery delivery = NotificationDelivery.builder()
                    .user(user)
                    .channel(NotificationChannelEnum.WEB_SOCKET)
                    .status(NotificationStatusEnum.FAILED)
                    .errorMessage(e.getMessage())
                    .sentAt(Instant.now())
                    .build();

            notificationDeliveryRepository.save(delivery);

            log.error("❌ [WebSocket] Failed to send notification to userId={} | title={} | error={}",
                    dto.userId(), dto.title(), e.getMessage());
        }
    }
}

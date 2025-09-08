package com.mindnova.observer;

import com.mindnova.common.NotificationChannelEnum;
import com.mindnova.dto.NotificationEventDto;
import com.mindnova.factories.NotificationDeliveryFactory;
import com.mindnova.services.NotificationDeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationObserver implements NotificationObserver {

    private final NotificationDeliveryFactory notificationDeliveryFactory;

    @Override
    @Async
    public void update(NotificationEventDto dto) {
        if (dto.channels().contains(NotificationChannelEnum.EMAIL)) {
            NotificationDeliveryService service = notificationDeliveryFactory.getService(NotificationChannelEnum.EMAIL);
            service.send(dto);
        }
    }
}

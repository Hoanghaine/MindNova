package com.mindnova.observer;

import com.mindnova.common.NotificationChannelEnum;
import com.mindnova.dto.NotificationEventDto;
import com.mindnova.factories.NotificationDeliveryFactory;
import com.mindnova.services.NotificationDeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class InAppNotificationObserver implements NotificationObserver {

    private final NotificationDeliveryFactory notificationDeliveryFactory;

    @Override
    @Async
    public void update(NotificationEventDto dto) {
        if (dto.channels().contains(NotificationChannelEnum.IN_APP)) {
            NotificationDeliveryService service = notificationDeliveryFactory.getService(NotificationChannelEnum.IN_APP);
            service.send(dto);
        }
    }
}

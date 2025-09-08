package com.mindnova.factories;

import com.mindnova.common.NotificationChannelEnum;
import com.mindnova.services.NotificationDeliveryService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationDeliveryFactory {
    private final Map<NotificationChannelEnum, NotificationDeliveryService> serviceMap;

    // dau vao la tat ca cac impl cua NotificationDeliveryService
    public NotificationDeliveryFactory(List<NotificationDeliveryService> services) {
        this.serviceMap = services.stream()
                .collect(Collectors.toMap(
                        service -> service.getChannel(),
                        service -> service
                ));
    }

    public NotificationDeliveryService getService(NotificationChannelEnum channel) {
        return serviceMap.get(channel);
    }
}

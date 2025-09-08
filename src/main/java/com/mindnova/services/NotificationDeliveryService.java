package com.mindnova.services;

import com.mindnova.common.NotificationChannelEnum;
import com.mindnova.dto.NotificationEventDto;

public interface NotificationDeliveryService {
    NotificationChannelEnum getChannel();
    void send(NotificationEventDto dto);
}
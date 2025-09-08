package com.mindnova.publisher;

import com.mindnova.dto.NotificationEventDto;
public interface NotificationPublisher {
    void publish(NotificationEventDto dto);
}
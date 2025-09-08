package com.mindnova.observer;

import com.mindnova.dto.NotificationEventDto;
import com.mindnova.entities.Notification;

public interface NotificationObserver {
    void update(NotificationEventDto notificationEventDto);
}
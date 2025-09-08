package com.mindnova.publisher.impl;

import com.mindnova.dto.NotificationEventDto;
import com.mindnova.observer.NotificationObserver;
import com.mindnova.publisher.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class NotificationPublisherImpl implements NotificationPublisher {
    private final List<NotificationObserver> observers;

    public void publish(NotificationEventDto dto) {
        observers.forEach(o -> o.update(dto));
    }
}

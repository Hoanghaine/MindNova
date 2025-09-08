package com.mindnova.manager;

import com.mindnova.dto.NotificationEventDto;
import com.mindnova.publisher.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationObserverManager {

    private final NotificationPublisher publisher;

    public void notifyUser(NotificationEventDto dto) {
        publisher.publish(dto);
    }
}

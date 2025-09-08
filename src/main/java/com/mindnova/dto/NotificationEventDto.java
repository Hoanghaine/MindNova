package com.mindnova.dto;

import com.mindnova.common.NotificationChannelEnum;
import lombok.Builder;

import java.util.Set;

@Builder
public record NotificationEventDto(
        Integer userId,
        String title,
        String userEmail,
        String content,
        Set<NotificationChannelEnum> channels
) {}

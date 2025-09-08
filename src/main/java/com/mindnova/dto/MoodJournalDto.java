package com.mindnova.dto;

import com.mindnova.common.EmotionEnum;
import com.mindnova.common.MoodEnum;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoodJournalDto {
    private Integer id;
    private Integer userId;
    private MoodEnum mood;
    private Set<EmotionEnum> emotions;
    private String note;
    private Instant createdAt;
}
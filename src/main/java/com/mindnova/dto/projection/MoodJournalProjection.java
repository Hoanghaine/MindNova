package com.mindnova.dto.projection;

import com.mindnova.common.EmotionEnum;
import com.mindnova.common.MoodEnum;

import java.time.Instant;
import java.util.Set;

public interface MoodJournalProjection {
    Integer getId();
    MoodEnum getMood();
    Set<EmotionEnum> getEmotions();
    String getNote();
    Instant getCreatedAt();
}

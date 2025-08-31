package com.mindnova.dto.projection;

import com.mindnova.common.EmotionEnum;
import com.mindnova.common.MoodEnum;
import lombok.*;

import java.time.Instant;
import java.util.Set;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoodJournalProjection {
//    Integer getId();
//    MoodEnum getMood();
//    Set<EmotionEnum> getEmotions();
//    String getNote();
//    Instant getCreatedAt();

    private Integer id;
    private MoodEnum mood;
    private Set<EmotionEnum> emotions;
    private String note;
    private Instant createdAt;

}

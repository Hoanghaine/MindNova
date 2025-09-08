package com.mindnova.mappers;

import com.mindnova.dto.MoodJournalDto;
import com.mindnova.entities.MoodJournal;

public class MoodJournalMapper {
    public static MoodJournalDto toDto(MoodJournal journal) {
        return MoodJournalDto.builder()
                .id(journal.getId())
                .userId(journal.getUser().getId())
                .mood(journal.getMood())
                .emotions(journal.getEmotions())
                .note(journal.getNote())
                .build();
    }
}

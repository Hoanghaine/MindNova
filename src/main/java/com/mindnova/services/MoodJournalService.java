package com.mindnova.services;

import com.mindnova.dto.MoodJournalDto;
import com.mindnova.dto.projection.MoodJournalProjection;
import com.mindnova.entities.MoodJournal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface MoodJournalService {
    Page<MoodJournalProjection> findAllByUserId(Integer userId, Pageable pageable);
    Optional<MoodJournalDto> create(MoodJournalDto journalDto);
    Optional<MoodJournal> update(Integer id, MoodJournal journal);
    void delete(Integer id);
}

package com.mindnova.repositories;

import com.mindnova.dto.projection.MoodJournalProjection;
import com.mindnova.entities.MoodJournal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodJournalRepository extends JpaRepository<MoodJournal, Integer> {
    Page<MoodJournalProjection> findAllByUserId(Integer userId, Pageable pageable);

}


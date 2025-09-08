package com.mindnova.services.impl;

import com.mindnova.dto.MoodJournalDto;
import com.mindnova.dto.projection.MoodJournalProjection;
import com.mindnova.entities.MoodJournal;
import com.mindnova.entities.User;
import com.mindnova.mappers.MoodJournalMapper;
import com.mindnova.repositories.MoodJournalRepository;
import com.mindnova.repositories.UserRepository;
import com.mindnova.services.MoodJournalService;
import com.mindnova.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoodJournalServiceImpl implements MoodJournalService {

    private final MoodJournalRepository moodJournalRepository;
    private final UserRepository userRepository;
    @Override
    public Page<MoodJournalProjection> findAllByUserId(Integer userId, Pageable pageable) {
        return moodJournalRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public Optional<MoodJournalDto> create(MoodJournalDto journalDto) {
        User user = userRepository.findById(journalDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        MoodJournal journal = MoodJournal.builder()
                .user(user)
                .mood(journalDto.getMood())
                .emotions(journalDto.getEmotions())
                .note(journalDto.getNote())
                .createdAt(Instant.now())
                .build();

        MoodJournal saved = moodJournalRepository.save(journal);

        return Optional.ofNullable(MoodJournalMapper.toDto(saved));
    }
    @Override
    public Optional<MoodJournal> update(Integer id, MoodJournal journal) {
        MoodJournal existing = moodJournalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal not found"));
        existing.setMood(journal.getMood());
        existing.setEmotions(journal.getEmotions());
        existing.setNote(journal.getNote());
        return Optional.of(moodJournalRepository.save(existing));
    }

    @Override
    public void delete(Integer id) {
        if (!moodJournalRepository.existsById(id)) {
            throw new RuntimeException("Journal not found");
        }
        moodJournalRepository.deleteById(id);
    }
}

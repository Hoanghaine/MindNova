package com.mindnova.services.impl;

import com.mindnova.common.EmotionEnum;
import com.mindnova.common.MoodEnum;
import com.mindnova.dto.MoodJournalDto;
import com.mindnova.dto.projection.MoodJournalProjection;
import com.mindnova.entities.MoodJournal;
import com.mindnova.entities.User;
import com.mindnova.repositories.MoodJournalRepository;
import com.mindnova.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoodJournalServiceImplTest {

    @Mock
    private MoodJournalRepository moodJournalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MoodJournalServiceImpl moodJournalService;

    private User user;
    private MoodJournal journal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder().id(1).email("test@example.com").build();
        journal = MoodJournal.builder()
                .id(1)
                .user(user)
                .mood(MoodEnum.GOOD)
                .emotions(Collections.singleton(EmotionEnum.ANGRY))
                .note("Feeling good")
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void testFindAllByUserId() {
        Pageable pageable = PageRequest.of(0, 10);
        MoodJournalProjection projection = mock(MoodJournalProjection.class);
        Page<MoodJournalProjection> page = new PageImpl<>(List.of(projection));

        when(moodJournalRepository.findAllByUserId(1, pageable)).thenReturn(page);

        Page<MoodJournalProjection> result = moodJournalService.findAllByUserId(1, pageable);

        assertThat(result).hasSize(1);
        verify(moodJournalRepository, times(1)).findAllByUserId(1, pageable);
    }

    @Test
    void testCreate_Success() {
        MoodJournalDto dto = MoodJournalDto.builder()
                .userId(1)
                .mood(MoodEnum.GREAT)
                .emotions(Collections.singleton(EmotionEnum.ANGRY))
                .note("Feeling good")
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(moodJournalRepository.save(any(MoodJournal.class))).thenReturn(journal);

        Optional<MoodJournalDto> result = moodJournalService.create(dto);

        assertThat(result).isPresent();
        assertThat(result.get().getMood()).isEqualTo(MoodEnum.GOOD);
        verify(userRepository, times(1)).findById(1);
        verify(moodJournalRepository, times(1)).save(any(MoodJournal.class));
    }

    @Test
    void testCreate_UserNotFound() {
        MoodJournalDto dto = MoodJournalDto.builder().userId(99).build();

        when(userRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> moodJournalService.create(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }

    @Test
    void testUpdate_Success() {
        MoodJournal updateData = MoodJournal.builder()
                .mood(MoodEnum.BAD)
                .emotions(Collections.singleton(EmotionEnum.ANGRY))
                .note("Bad day")
                .build();

        when(moodJournalRepository.findById(1)).thenReturn(Optional.of(journal));
        when(moodJournalRepository.save(any(MoodJournal.class))).thenReturn(journal);

        Optional<MoodJournal> result = moodJournalService.update(1, updateData);

        assertThat(result).isPresent();
        assertThat(result.get().getMood()).isEqualTo(MoodEnum.BAD);
        assertThat(result.get().getEmotions()).isEqualTo(Collections.singleton(EmotionEnum.ANGRY));
        verify(moodJournalRepository, times(1)).findById(1);
        verify(moodJournalRepository, times(1)).save(any(MoodJournal.class));
    }

    @Test
    void testUpdate_NotFound() {
        MoodJournal updateData = MoodJournal.builder().mood(MoodEnum.AWFUL).build();

        when(moodJournalRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> moodJournalService.update(99, updateData))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Journal not found");
    }

    @Test
    void testDelete_Success() {
        when(moodJournalRepository.existsById(1)).thenReturn(true);

        moodJournalService.delete(1);

        verify(moodJournalRepository, times(1)).deleteById(1);
    }

    @Test
    void testDelete_NotFound() {
        when(moodJournalRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> moodJournalService.delete(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Journal not found");
    }
}

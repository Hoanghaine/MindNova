package com.mindnova.entities;

import com.mindnova.common.EmotionEnum;
import com.mindnova.common.MoodEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mood_journal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoodJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private MoodEnum mood;

    @ElementCollection(targetClass = EmotionEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "mood_journal_emotions",
            joinColumns = @JoinColumn(name = "journal_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "emotion") // tên cột trong bảng phụ
    private Set<EmotionEnum> emotions = new HashSet<>();

    @Column(length = 1000)
    private String note;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

}

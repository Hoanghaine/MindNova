package com.mindnova.controllers;

import com.mindnova.dto.MoodJournalDto;
import com.mindnova.dto.projection.MoodJournalProjection;
import com.mindnova.dto.response.ApiResponse;
import com.mindnova.entities.MoodJournal;
import com.mindnova.services.MoodJournalService;
import com.mindnova.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mood-journals")
@RequiredArgsConstructor
public class MoodJournalController {

    private final MoodJournalService moodJournalService;
    private final JwtUtils jwtUtils;
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<MoodJournalProjection>>> getMyJournals(
           @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Integer userId =   jwtUtils.extractAccountIdFromAuthHeader(authHeader);

        Page<MoodJournalProjection> journalPage = moodJournalService.findAllByUserId(userId, PageRequest.of(page, size));

        ApiResponse<List<MoodJournalProjection>> response = new ApiResponse<>(
                true,
                journalPage.getContent(),
                "Fetched emotion journals successfully",
                new ApiResponse.Pagination(
                        journalPage.getNumber(),
                        journalPage.getSize(),
                        journalPage.getTotalElements(),
                        journalPage.getTotalPages()
                )
        );
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<Optional<MoodJournalDto>> create(@RequestBody MoodJournalDto journalDto,@RequestHeader("Authorization") String authHeader) {
        Integer userId =   jwtUtils.extractAccountIdFromAuthHeader(authHeader);
        journalDto.setUserId(userId);
        Optional<MoodJournalDto> createdJournal = moodJournalService.create(journalDto);
        return ResponseEntity.ok(createdJournal);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<MoodJournal>> update(@PathVariable Integer id, @RequestBody MoodJournal journal) {

        return ResponseEntity.ok(moodJournalService.update(id, journal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        moodJournalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

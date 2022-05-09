package com.team7.project.interview.controller;

import com.team7.project.interview.dto.*;
import com.team7.project.interview.model.Interview;
import com.team7.project.interview.service.InterviewGeneralService;
import com.team7.project.interview.service.InterviewPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class InterviewController {
    private final InterviewPostService interviewPostService;
    private final InterviewGeneralService interviewGeneralService;


    @GetMapping("/api/interviews")
    public ResponseEntity<InterviewListResponseDto> readInterviews(@RequestParam(value = "per", defaultValue = "8") int per, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "sort", defaultValue = "new") String sort) {
//      assume user id is 1
        // note that pageable start with 0
        Pageable pageable = PageRequest.of(page - 1, per, Sort.by("createdAt").descending());
        InterviewListResponseDto body = interviewGeneralService.readAllInterviews(pageable);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/api/interviews/{interviewId}")
    public ResponseEntity<InterviewResponseDto> readOneInterview(@PathVariable Long interviewId) {
//       assume user id is 1
        InterviewResponseDto body = interviewGeneralService.readOneInterview(interviewId);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/api/interviews/draft")
    public ResponseEntity<InterviewDraftResponseDto> createInterviewDraft() {
//       assume user id is 1
        Interview interview = interviewPostService.createInterviewDraft(1L);

        String videoUrl = interviewPostService.generatePresignedPost(interview.getVideoKey());
        String thumbnailUrl = interviewPostService.generatePresignedPost(interview.getThumbnailKey());

        InterviewDraftResponseDto body = new InterviewDraftResponseDto(new InterviewDraftResponseDto.InterviewDraftBody(interview.getId()),
                new InterviewDraftResponseDto.UrlBody(videoUrl, thumbnailUrl));

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/api/interviews/{interviewId}")
    public ResponseEntity<InterviewResponseDto> completeInterview(@PathVariable Long interviewId, @RequestBody InterviewPostRequestDto requestDto) {
//       assume user id is 1, nickname is TestNickname
        InterviewResponseDto body = interviewPostService.completeInterview(1L, interviewId, requestDto);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PutMapping("/api/interviews/{interviewId}")
    public ResponseEntity<InterviewResponseDto> update(@PathVariable Long interviewId, @RequestBody InterviewUpdateRequestDto requestDto) {
//       assume user id is 1, nickname is TestNickname
        InterviewResponseDto body = interviewGeneralService.updateInterview(interviewId, requestDto);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @DeleteMapping("/api/interviews/{interviewId}")
    public ResponseEntity<InterviewResponseDto> deleteInterview(@PathVariable Long interviewId) {
//       assume user id is 1, nickname is TestNickname
        InterviewResponseDto body = interviewGeneralService.deleteInterview(interviewId, 1L);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}

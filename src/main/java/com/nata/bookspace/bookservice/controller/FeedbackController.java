package com.nata.bookspace.bookservice.controller;

import com.nata.bookspace.bookservice.config.JwtService;
import com.nata.bookspace.bookservice.dto.FeedbackDTO;
import com.nata.bookspace.bookservice.entity.Feedback;
import com.nata.bookspace.bookservice.service.FeedbackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback/")
public class FeedbackController {

    private final FeedbackServiceImpl feedbackService;

    @GetMapping("{theBookId}")
    @PreAuthorize("hasAuthority('ADMIN')or hasAuthority('USER')")
    @ResponseBody
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByBook(@PathVariable  long theBookId) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByBookId(theBookId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')or hasAuthority('USER')")
    @ResponseBody
    public ResponseEntity<Feedback> createFeedbackOnBook(@RequestBody  FeedbackDTO feedbackDTO) {
        return ResponseEntity.ok(feedbackService.saveFeedback(feedbackDTO));
    }

    @DeleteMapping("{feedback_id}")
    @PreAuthorize("hasAuthority('ADMIN')or hasAuthority('USER')")
    @ResponseBody
    public ResponseEntity<Void> editFeedbackOnBook(@PathVariable long feedback_id) {
        feedbackService.deleteFeedback(feedback_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("average_mark/{theBookId}")
    @PreAuthorize("hasAuthority('ADMIN')or hasAuthority('USER')")
    @ResponseBody
    public ResponseEntity<Double> getAverageMarkByBook(@PathVariable  long theBookId) {
        return ResponseEntity.ok(feedbackService.getAverageMark(theBookId));
    }
}

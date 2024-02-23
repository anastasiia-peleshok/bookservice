package com.nata.bookspace.bookservice.controller;

import com.nata.bookspace.bookservice.dto.FeedbackDTO;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.Feedback;
import com.nata.bookspace.bookservice.service.FeedbackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedbacks/")
public class FeedbackController {

    private final FeedbackServiceImpl feedbackService;

    @GetMapping("{theBookId}")
    @ResponseBody
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByBook(@PathVariable  long theBookId) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByBookId(theBookId));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Feedback> createFeedbackOnBook(@RequestBody  FeedbackDTO feedbackDTO) {
        return ResponseEntity.ok(feedbackService.saveFeedback(feedbackDTO));
    }
    @GetMapping("average_mark/{theBookId}")
    @ResponseBody
    public ResponseEntity<Double> getAverageMarkByBook(@PathVariable  long theBookId) {
        return ResponseEntity.ok(feedbackService.getAverageMark(theBookId));
    }
}

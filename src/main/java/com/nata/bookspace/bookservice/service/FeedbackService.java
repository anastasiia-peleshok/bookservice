package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.FeedbackDTO;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    List<Feedback> getFeedbacks();
    List<FeedbackDTO> getFeedbacksByBookId(long theBookId);
    Optional<Feedback> getFeedbackById(long theId);
    Feedback saveFeedback(FeedbackDTO theFeedbackDTO);
    double getAverageMark(long theBookId);
    void deleteFeedback(long theId);
}

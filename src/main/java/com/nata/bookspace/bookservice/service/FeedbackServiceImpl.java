package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.FeedbackDTO;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.Feedback;
import com.nata.bookspace.bookservice.mapper.FeedbackMapper;
import com.nata.bookspace.bookservice.repository.FeedbackRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;


    @Override
    public List<FeedbackDTO> getFeedbacksByBookId(long theBookId) {
        return feedbackRepository.findFeedbacksByBook_Id(theBookId).stream().map(FeedbackMapper::mapToFeedbackDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Feedback saveFeedback(FeedbackDTO theFeedbackDTO) {
        Feedback feedbackToSave = FeedbackMapper.mapToFeedback(theFeedbackDTO);
        return feedbackRepository.save(feedbackToSave);

    }

    @Override
    public double getAverageMark(long theBookId) {
        return feedbackRepository.findFeedbacksByBook_Id(theBookId)
                .stream()
                .mapToInt(Feedback::getMark)
                .average()
                .orElseThrow(() -> new NoSuchElementException("Book with id: " + theBookId+" is not found"));
    }

    @Override
    public void deleteFeedback(long theFeedbackId) {
        Feedback feedbackToDelete = feedbackRepository.findById(theFeedbackId).orElseThrow(() -> new NoSuchElementException("Feedback with id " + theFeedbackId + " is not found"));
        feedbackRepository.delete(feedbackToDelete);
    }

}

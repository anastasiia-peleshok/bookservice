package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.FeedbackDTO;
import com.nata.bookspace.bookservice.entity.Feedback;
import com.nata.bookspace.bookservice.mapper.FeedbackMapper;
import com.nata.bookspace.bookservice.repository.FeedbackRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> getFeedbacks() {
        return null;
    }

    @Override
    public List<FeedbackDTO> getFeedbacksByBookId(long theBookId) {
        return feedbackRepository.findFeedbacksByBook_Id(theBookId).stream().map(a -> FeedbackMapper.mapToFeedbackDTO(a)).collect(Collectors.toList());
    }

    @Override
    public Optional<Feedback> getFeedbackById(long theId) {
        return Optional.empty();
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
                .orElse(0.0);}

    @Override
    public void deleteFeedback(long theId) {

    }
}

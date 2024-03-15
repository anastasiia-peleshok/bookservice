package com.nata.bookspace.bookservice.mapper;

import com.nata.bookspace.bookservice.dto.FeedbackDTO;
import com.nata.bookspace.bookservice.entity.*;
import com.nata.bookspace.bookservice.repository.BookRepository;
import com.nata.bookspace.bookservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeedbackMapper {

    public static FeedbackDTO mapToFeedbackDTO(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO(
                feedback.getId(),
                feedback.getUser().getId(),
                feedback.getBook().getId(),
                feedback.getMark(),
                feedback.getFeedbackDescription()
        );

        return feedbackDTO;
    }

    public static Feedback mapToFeedback(FeedbackDTO feedbackDTO) {

        Feedback feedback = new Feedback();
        feedback.setId(feedbackDTO.getId());
        feedback.setMark(feedbackDTO.getMark());
        feedback.setFeedbackDescription(feedbackDTO.getFeedbackDescription());

        User user = new User();
        user.setId(feedbackDTO.getUserId());
        feedback.setUser(user);

        Book book = new Book();
        book.setId(feedbackDTO.getBookId());
        feedback.setBook(book);

        return feedback;
    }

}

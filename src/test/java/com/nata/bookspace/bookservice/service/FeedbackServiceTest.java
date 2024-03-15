package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.FeedbackDTO;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.Feedback;
import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.repository.FeedbackRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {
    @Mock
    private FeedbackRepository feedbackRepository;
    @InjectMocks
    private FeedbackServiceImpl feedbackService;


    @Test
    public void getFeedbacksByBookIdSuccessfully() {
        Book book = new Book();
        long theBookId = 1l;
        book.setId(theBookId);
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        Feedback feedback1 = new Feedback();
        feedback1.setId(1L);
        feedback1.setFeedbackDescription("Good book!");
        feedback1.setBook(book);
        feedback1.setUser(user1);
        feedback1.setMark(5);
        Feedback feedback2 = new Feedback();
        feedback2.setId(2L);
        feedback2.setFeedbackDescription("Awesome read!");
        feedback2.setBook(book);
        feedback2.setUser(user2);
        feedback2.setMark(5);

        List<Feedback> mockFeedbacks = new ArrayList<>();
        mockFeedbacks.add(feedback1);
        mockFeedbacks.add(feedback2);

        when(feedbackRepository.findFeedbacksByBook_Id(theBookId)).thenReturn(mockFeedbacks);

        List<FeedbackDTO> result = feedbackService.getFeedbacksByBookId(theBookId);

        assertNotNull(result);
        assertEquals(2, result.size());

        List<String> feedbacks = result.stream().map(FeedbackDTO::getFeedbackDescription).collect(Collectors.toList());
        assertTrue(feedbacks.contains("Good book!"));
        assertTrue(feedbacks.contains("Awesome read!"));
    }

    @Test
    @Transactional
    public void saveFeedbackSuccessfully() {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setFeedbackDescription("Great book!");
        feedbackDTO.setMark(5);
        feedbackDTO.setBookId(1L);
        feedbackDTO.setUserId(1L);

        User user = new User();
        user.setId(1L);
        Book book = new Book();
        book.setId(1L);
        Feedback savedFeedback = new Feedback();
        savedFeedback.setId(1L);
        savedFeedback.setFeedbackDescription("Great book!");
        savedFeedback.setMark(5);
        savedFeedback.setUser(user);
        savedFeedback.setBook(book);

        Mockito.when(feedbackRepository.save(Mockito.any(Feedback.class))).thenReturn(savedFeedback);

        Feedback resultFeedback = feedbackService.saveFeedback(feedbackDTO);

        assertNotNull(resultFeedback);
        assertEquals("Great book!", resultFeedback.getFeedbackDescription());
        assertEquals(5, resultFeedback.getMark());

        // Optional: Verify that the save method was called
        Mockito.verify(feedbackRepository, Mockito.times(1)).save(Mockito.any(Feedback.class));
    }

    @Test
    public void testGetAverageMark() {
        // Given
        long bookId = 1L;
        Feedback feedback1 = new Feedback();
        feedback1.setMark(4);
        Feedback feedback2 = new Feedback();
        feedback2.setMark(5);
        List<Feedback> feedbackList = Arrays.asList(feedback1, feedback2);

        Mockito.when(feedbackRepository.findFeedbacksByBook_Id(bookId)).thenReturn(feedbackList);

        double averageMark = feedbackService.getAverageMark(bookId);

        assertEquals(4.5, averageMark);

        Mockito.verify(feedbackRepository, Mockito.times(1)).findFeedbacksByBook_Id(bookId);
    }

    @Test
    public void testGetAverageMarkNoFeedbacks() {
        long nonExistentBookId = 999L;

        Mockito.when(feedbackRepository.findFeedbacksByBook_Id(nonExistentBookId)).thenReturn(List.of());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> feedbackService.getAverageMark(nonExistentBookId));
        assertEquals("Book with id: " + nonExistentBookId + " is not found", exception.getMessage());

        Mockito.verify(feedbackRepository, Mockito.times(1)).findFeedbacksByBook_Id(nonExistentBookId);
    }

    @Test
    @Transactional
    public void testDeleteFeedback() {

        long feedbackId = 1L;
        Feedback feedbackToDelete = new Feedback();
        feedbackToDelete.setId(feedbackId);

        Mockito.when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedbackToDelete));

        feedbackService.deleteFeedback(feedbackId);
        Mockito.verify(feedbackRepository, Mockito.times(1)).delete(feedbackToDelete);
    }

    @Test
    @Transactional
    public void testDeleteNonExistentFeedback() {
        long nonExistentFeedbackId = 999L;

        Mockito.when(feedbackRepository.findById(nonExistentFeedbackId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> feedbackService.deleteFeedback(nonExistentFeedbackId));
        assertEquals("Feedback with id " + nonExistentFeedbackId + " is not found", exception.getMessage());

        Mockito.verify(feedbackRepository, Mockito.never()).delete(Mockito.any());
    }
}

package com.nata.bookspace.bookservice.repository;

import com.nata.bookspace.bookservice.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findFeedbacksByBook_Id(long theBookId);
}

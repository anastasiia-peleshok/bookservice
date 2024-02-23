package com.nata.bookspace.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
    private long id;
    private long userId;
    private long bookId;
    private int mark;
    private String feedbackDescription;

}

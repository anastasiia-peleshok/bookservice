package com.nata.bookspace.bookservice.dto;

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

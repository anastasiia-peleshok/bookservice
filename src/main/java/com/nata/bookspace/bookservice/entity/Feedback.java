package com.nata.bookspace.bookservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name = "book_id")
    private Book book;

    private int mark;
    @Column(name = "feedback_description")
    private String feedbackDescription;


}

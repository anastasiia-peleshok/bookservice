package com.nata.bookspace.bookservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String author;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "annotation_id", referencedColumnName = "id")
    @JsonManagedReference
    private Annotation annotation;

 //   @JsonManagedReference
// @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
// private List<Feedback> feedback;

//    public Book(long id, String name, String author, Genre genre, Annotation annotation) {
//        this.id = id;
//        this.name = name;
//        this.author = author;
//        this.genre = genre;
//        this.annotation = annotation;
//    }
//    public void addFeedback(Feedback theFeedback){
//        feedback.add(theFeedback);
//    }

}

package com.nata.bookspace.bookservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToOne(cascade = {CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name = "annotation_id", referencedColumnName = "id")
    @JsonManagedReference
    private Annotation annotation;

}

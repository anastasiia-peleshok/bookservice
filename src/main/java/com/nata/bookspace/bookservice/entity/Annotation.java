package com.nata.bookspace.bookservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Annotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = {CascadeType.REFRESH,CascadeType.REMOVE}, mappedBy = "annotation")
    @JsonBackReference
    private Book book;
    private String description;

}

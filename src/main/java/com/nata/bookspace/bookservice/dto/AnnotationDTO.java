package com.nata.bookspace.bookservice.dto;

import com.nata.bookspace.bookservice.entity.Book;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationDTO {
    private long id;
    private String bookName;
    private String description;

}
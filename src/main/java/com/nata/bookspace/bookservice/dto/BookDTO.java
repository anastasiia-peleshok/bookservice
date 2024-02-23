package com.nata.bookspace.bookservice.dto;

import com.nata.bookspace.bookservice.entity.Annotation;
import com.nata.bookspace.bookservice.entity.Feedback;
import com.nata.bookspace.bookservice.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private long id;
    private String name;
    private String author;
    private Genre genre;
    private String annotation;
}

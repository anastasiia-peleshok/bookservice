package com.nata.bookspace.bookservice.dto;

import com.nata.bookspace.bookservice.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private long id;
    private String title;
    private String author;
    private Genre genre;
    private String annotation;
}

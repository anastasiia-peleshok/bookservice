package com.nata.bookspace.bookservice.mapper;

import com.nata.bookspace.bookservice.dto.AnnotationDTO;
import com.nata.bookspace.bookservice.dto.BookDTO;
import com.nata.bookspace.bookservice.entity.Annotation;
import com.nata.bookspace.bookservice.entity.Book;

public class AnnotationMapper {
    public static AnnotationDTO mapToAnnotationDTO(Annotation annotation) {
        AnnotationDTO annotationDTO = new AnnotationDTO(
                annotation.getId(),
                annotation.getBook().getName(),
                annotation.getDescription()
        );

        return annotationDTO;
    }
}


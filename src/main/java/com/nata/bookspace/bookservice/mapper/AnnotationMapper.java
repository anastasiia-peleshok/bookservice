package com.nata.bookspace.bookservice.mapper;

import com.nata.bookspace.bookservice.dto.AnnotationDTO;
import com.nata.bookspace.bookservice.entity.Annotation;

public class AnnotationMapper {
    public static AnnotationDTO mapToAnnotationDTO(Annotation annotation) {
        AnnotationDTO annotationDTO = new AnnotationDTO(
                annotation.getId(),
                annotation.getBook().getTitle(),
                annotation.getDescription()
        );

        return annotationDTO;
    }
}


package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.AnnotationDTO;
import com.nata.bookspace.bookservice.entity.Annotation;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface AnnotationService {
    List<AnnotationDTO> getAnnotations();
    Annotation getAnnotationById(long theId);
}

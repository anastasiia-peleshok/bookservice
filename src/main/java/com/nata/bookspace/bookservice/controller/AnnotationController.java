package com.nata.bookspace.bookservice.controller;

import com.nata.bookspace.bookservice.dto.AnnotationDTO;
import com.nata.bookspace.bookservice.entity.Annotation;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.service.AnnotationServiceImpl;
import com.nata.bookspace.bookservice.service.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/annotation/")
@RequiredArgsConstructor
public class AnnotationController {

    private final AnnotationServiceImpl annotationService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<AnnotationDTO>> getAnnotations() {
        return ResponseEntity.ok(annotationService.getAnnotations());
    }

}

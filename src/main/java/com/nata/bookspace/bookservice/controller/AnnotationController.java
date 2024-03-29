package com.nata.bookspace.bookservice.controller;

import com.nata.bookspace.bookservice.dto.AnnotationDTO;
import com.nata.bookspace.bookservice.service.AnnotationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/annotation/")
@RequiredArgsConstructor
public class AnnotationController {

    private final AnnotationServiceImpl annotationService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')or hasAuthority('USER')")
    @ResponseBody
    public ResponseEntity<List<AnnotationDTO>> getAnnotations() {
        return ResponseEntity.ok(annotationService.getAnnotations());
    }

}

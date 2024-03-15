package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.AnnotationDTO;
import com.nata.bookspace.bookservice.entity.Annotation;
import com.nata.bookspace.bookservice.mapper.AnnotationMapper;
import com.nata.bookspace.bookservice.repository.AnnotationRepository;
import com.nata.bookspace.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnotationServiceImpl implements AnnotationService {

    private final AnnotationRepository annotationRepository;


    @Override
    public List<AnnotationDTO> getAnnotations() {
        return annotationRepository.findAll().stream().map(a -> AnnotationMapper.mapToAnnotationDTO(a)).collect(Collectors.toList());
    }

    @Override
    public Annotation getAnnotationById(long theAnnotationId) {
        return annotationRepository.findById(theAnnotationId).orElseThrow(() -> new NoSuchElementException("Annotation with id: " + theAnnotationId+" is not found"));

    }
}

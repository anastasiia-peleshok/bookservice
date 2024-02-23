package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.AnnotationDTO;
import com.nata.bookspace.bookservice.entity.Annotation;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.mapper.AnnotationMapper;
import com.nata.bookspace.bookservice.repository.AnnotationRepository;
import com.nata.bookspace.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnotationServiceImpl implements AnnotationService {

    private final AnnotationRepository annotationRepository;
    private final BookRepository bookRepository;

    @Override
    public List<AnnotationDTO> getAnnotations() {
        return annotationRepository.findAll().stream().map(a-> AnnotationMapper.mapToAnnotationDTO(a)).collect(Collectors.toList());
    }

    @Override
    public Optional<Annotation> getAnnotationById(long theId) {
        return Optional.empty();
    }

//    @Override
//    public Annotation saveAnnotation(Annotation theAnnotation) {
//        Book theBook = theAnnotation.getBook();
//        Annotation annotation=annotationRepository.save(theAnnotation);
//        theBook.setAnnotation(annotation);
//        return annotation;
//    }

    @Override
    public void deleteAnnotation(long theId) {

    }
}

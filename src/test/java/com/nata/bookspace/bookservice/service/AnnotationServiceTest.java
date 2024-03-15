package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.AnnotationDTO;
import com.nata.bookspace.bookservice.entity.Annotation;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.repository.AnnotationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AnnotationServiceTest {
    @Mock
    private AnnotationRepository annotationRepository;

    @InjectMocks
    private AnnotationServiceImpl annotationService;

    @Test
    public void testGetAnnotations_NotEmptyList() {
        List<Annotation> annotations = new ArrayList<>();
        Book book1=new Book();
        book1.setId(1L);
        Book book2=new Book();
        book2.setId(2L);
        Annotation annotation1=new Annotation();
        annotation1.setId(1L);
        annotation1.setBook(book1);
        annotation1.setDescription("Annotation 1");
        Annotation annotation2=new Annotation();
        annotation2.setId(2L);
        annotation2.setBook(book2);
        annotation2.setDescription("Annotation 2");
        annotations.add(annotation1);
        annotations.add(annotation2);

        given(annotationRepository.findAll()).willReturn(annotations);

        List<AnnotationDTO> annotationDTOs = annotationService.getAnnotations();

        assertNotNull(annotationDTOs);
        assertFalse(annotationDTOs.isEmpty());
        assertEquals(annotations.size(), annotationDTOs.size());
        assertEquals(annotations.get(0).getId(), annotationDTOs.get(0).getId());
        assertEquals(annotations.get(0).getDescription(), annotationDTOs.get(0).getDescription());
        assertEquals(annotations.get(1).getId(), annotationDTOs.get(1).getId());
        assertEquals(annotations.get(1).getDescription(), annotationDTOs.get(1).getDescription());
    }

    @Test
    public void testGetAnnotations_EmptyList() {
        List<Annotation> emptyList = new ArrayList<>();

        given(annotationRepository.findAll()).willReturn(emptyList);

        List<AnnotationDTO> annotationDTOs = annotationService.getAnnotations();

        assertNotNull(annotationDTOs);
        assertTrue(annotationDTOs.isEmpty());
    }
    @Test
    public void testGetAnnotationById_ExistingId() {
        long annotationId = 1L;
        Book book1=new Book();
        book1.setId(1L);
        Annotation annotation = new Annotation(annotationId,book1, "Test Annotation");

        given(annotationRepository.findById(annotationId)).willReturn(Optional.of(annotation));

        Annotation result = annotationService.getAnnotationById(annotationId);

        assertNotNull(result);
        assertEquals(annotation.getId(), result.getId());
        assertEquals(annotation.getDescription(), result.getDescription());
    }

    @Test
    public void testGetAnnotationById_NonExistingId() {
        long nonExistingId = 100L;

        given(annotationRepository.findById(nonExistingId)).willReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> annotationService.getAnnotationById(nonExistingId));
    }
}

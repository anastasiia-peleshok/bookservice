package com.nata.bookspace.bookservice.repository;

import com.nata.bookspace.bookservice.entity.Annotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, Long> {
}

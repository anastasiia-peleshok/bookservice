package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.BookDTO;
import com.nata.bookspace.bookservice.entity.Annotation;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.Genre;
import com.nata.bookspace.bookservice.mapper.BookMapper;
import com.nata.bookspace.bookservice.repository.AnnotationRepository;
import com.nata.bookspace.bookservice.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AnnotationRepository annotationRepository;

    @Override
    public List<BookDTO> getBooks() {
        return bookRepository.findAll().stream().map(a -> BookMapper.mapToBookDTO(a)).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByGenre(Genre genre) {
        return bookRepository.findBooksByGenre(genre).stream().map(a -> BookMapper.mapToBookDTO(a)).collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(long theBookId) {
        return bookRepository.findById(theBookId).map(a -> BookMapper.mapToBookDTO(a)).orElseThrow(() -> new NoSuchElementException("Book with id: " + theBookId+" is not found"));
    }

    @Override
    @Transactional
    public Book saveBook(BookDTO theBookDTO) {
        if (bookRepository.findByTitle(theBookDTO.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Book with title " + theBookDTO.getTitle() + " already exists");
        }
        Book theBook = BookMapper.mapToBook(theBookDTO);
        theBook = bookRepository.save(theBook);
        Annotation annotation = theBook.getAnnotation();
        if (annotation != null) {
            annotation.setBook(theBook);
            annotationRepository.save(annotation);
        }
        return theBook;
    }

    @Transactional
    @Override
    public Book editBook(long theBookId, BookDTO theBookDTO) {
        Book existingBook = bookRepository.findById(theBookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id: " + theBookId+" is not found"));

        existingBook.setTitle(theBookDTO.getTitle());
        existingBook.setAuthor(theBookDTO.getAuthor());
        existingBook.setGenre(theBookDTO.getGenre());

        Annotation annotation = annotationRepository.findById(theBookId).get();
        annotation.setDescription(theBookDTO.getAnnotation());
        existingBook.setAnnotation(annotationRepository.save(annotation));

        return bookRepository.save(existingBook);
    }

    @Transactional
    @Override
    public void deleteBook(long theBookId) {
        Book bookToDelete = bookRepository.findById(theBookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id: " + theBookId+" is not found"));
        bookRepository.delete(bookToDelete);
    }
}

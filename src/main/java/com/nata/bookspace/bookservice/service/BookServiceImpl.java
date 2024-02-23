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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AnnotationRepository annotationRepository;

    @Override
    public List<BookDTO> getBooks() {
        return bookRepository.findAll().stream().map(a-> BookMapper.mapToBookDTO(a)).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByGenre(Genre genre) {
        return bookRepository.findBooksByGenre(genre).stream().map(a-> BookMapper.mapToBookDTO(a)).collect(Collectors.toList());
    }

    @Override
    public Optional<BookDTO> getBookById(long theId) {

        return bookRepository.findById(theId).map(a-> BookMapper.mapToBookDTO(a));

    }

    @Override
    @Transactional
    public Book saveBook(BookDTO theBookDTO) {
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
        Book theBook = BookMapper.mapToBook(theBookDTO);
        Book existingBook = bookRepository.findById(theBookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + theBookId));

        // Оновлення властивостей книги з BookDTO
        existingBook.setName(theBook.getName());
        existingBook.setAuthor(theBook.getAuthor());
        existingBook.setGenre(theBook.getGenre());

        Annotation annotation=annotationRepository.findById(theBookId).get();
        annotation.setDescription(theBook.getAnnotation().getDescription());
        existingBook.setAnnotation(annotationRepository.save(annotation));

        // Збереження оновленої книги у базі даних
        return bookRepository.save(existingBook);
    }
    @Transactional
    @Override
    public void deleteBook(long theId) {

    }
}

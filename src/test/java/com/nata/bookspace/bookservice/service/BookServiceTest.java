package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.BookDTO;
import com.nata.bookspace.bookservice.entity.Annotation;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.Genre;
import com.nata.bookspace.bookservice.repository.AnnotationRepository;
import com.nata.bookspace.bookservice.repository.BookRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AnnotationRepository annotationRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetBooksNotEmptyList() {
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book1");
        book1.setAuthor("Author 1");
        Annotation annotation = new Annotation();
        annotation.setId(1L);
        annotation.setDescription("Description 1");
        annotation.setBook(book1);
        book1.setAnnotation(annotation);
        book1.setGenre(Genre.COMEDY);
        books.add(book1);
        given(bookRepository.findAll()).willReturn(books);

        List<BookDTO> result = bookService.getBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertFalse(result.isEmpty());

    }
    @Test
    public void testGetBooksEmptyList() {
        List<Book> emptyList = new ArrayList<>();
        given(bookRepository.findAll()).willReturn(emptyList);

        List<BookDTO> result = bookService.getBooks();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBooksByGenreBooksFound() {
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book1");
        book1.setAuthor("Author 1");
        Annotation annotation1 = new Annotation();
        annotation1.setId(1L);
        annotation1.setDescription("Description 1");
        annotation1.setBook(book1);
        book1.setAnnotation(annotation1);
        book1.setGenre(Genre.COMEDY);
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book2");
        book2.setAuthor("Author 2");
        Annotation annotation2 = new Annotation();
        annotation2.setId(1L);
        annotation2.setDescription("Description 2");
        annotation2.setBook(book2);
        book2.setAnnotation(annotation2);
        book2.setGenre(Genre.COMEDY);
        Genre genre=Genre.COMEDY;
        books.add(book1);
        books.add(book2);
        given(bookRepository.findBooksByGenre(genre)).willReturn(books);

        List<BookDTO> result = bookService.getBooksByGenre(genre);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }
    @Test
    public void testGetBooksByGenreNoBooksFound() {
        Genre genre=Genre.COMEDY;
        List<Book> emptyList = new ArrayList<>();
        given(bookRepository.findBooksByGenre(genre)).willReturn(emptyList);

        List<BookDTO> result = bookService.getBooksByGenre(genre);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testGetBookByIdBookFound() {
        long bookId = 1L;
        Book book1 = new Book();
        book1.setId(bookId);
        book1.setTitle("Book1");
        book1.setAuthor("Author1");
        Annotation annotation1 = new Annotation();
        annotation1.setId(1L);
        annotation1.setDescription("Description1");
        annotation1.setBook(book1);
        book1.setAnnotation(annotation1);
        book1.setGenre(Genre.COMEDY);

        given(bookRepository.findById(bookId)).willReturn(Optional.of(book1));

        BookDTO result = bookService.getBookById(bookId);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Book1", result.getTitle());
        assertEquals("Author1", result.getAuthor());
        assertEquals("Description1", result.getAnnotation());
    }
    @Test
    public void testGetBookById_BookNotFound() {
        long bookId = 1L;
        given(bookRepository.findById(bookId)).willReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            bookService.getBookById(bookId);
        });
    }
    @Test
    public void testSaveBookBookNotExists() {
        BookDTO bookDTO = new BookDTO(1L,"New Book", "Author",  Genre.COMEDY, "Annotation");
        Annotation annotation=new Annotation(1L, null,"Annotation");
        Book savedBook = new Book(1L, "New Book", "Author", Genre.COMEDY, annotation);

        given(bookRepository.findByTitle("New Book")).willReturn(Optional.empty());
        given(bookRepository.save(any())).willReturn(savedBook);
        given(annotationRepository.save(any())).willReturn(annotation);

        Book result = bookService.saveBook(bookDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Book", result.getTitle());
        assertEquals("Author", result.getAuthor());
    }
    @Test
    public void testSaveBookBookExists() {
        BookDTO bookDTO = new BookDTO(1L,"New Book", "Author",  Genre.COMEDY, "Annotation");

        given(bookRepository.findByTitle("New Book")).willReturn(Optional.of(new Book()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bookService.saveBook(bookDTO));
        assertEquals("Book with title " + bookDTO.getTitle() + " already exists", exception.getMessage());
    }
    @Test
    public void testEditBookBookExists() {
        long bookId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookId);
        bookDTO.setTitle("New Title");
        bookDTO.setAuthor("New Author");
        bookDTO.setAnnotation("New Annotation");
        bookDTO.setGenre(Genre.COMEDY);

        Annotation newAnnotation = new Annotation();
        newAnnotation.setId(1L);
        newAnnotation.setDescription("New Annotation");

        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setTitle("Old Book");
        existingBook.setAuthor("Old Author");
        existingBook.setGenre(Genre.COMEDY);
        existingBook.setAnnotation(new Annotation());

        given(bookRepository.findById(bookId)).willReturn(Optional.of(existingBook));
        given(annotationRepository.findById(bookId)).willReturn(Optional.of(new Annotation()));
        given(annotationRepository.save(any())).willReturn(newAnnotation);
        given(bookRepository.save(existingBook)).willReturn(existingBook);

        Book result = bookService.editBook(bookId, bookDTO);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("New Title", result.getTitle());
        assertEquals("New Author", result.getAuthor());
        assertEquals("New Annotation", result.getAnnotation().getDescription());
        assertEquals(Genre.COMEDY, result.getGenre());
    }
    @Test
    public void testEditBookBookNotExists() {
        long bookId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookId);
        bookDTO.setTitle("New Title");
        bookDTO.setAuthor("New Author");
        bookDTO.setAnnotation("New Annotation");
        bookDTO.setGenre(Genre.COMEDY);

        given(bookRepository.findById(bookId)).willReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            bookService.editBook(bookId, bookDTO);
        });
    }
    @Test
    public void testDeleteBookBookExists() {
        long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setId(bookId);

        given(bookRepository.findById(bookId)).willReturn(Optional.of(existingBook));

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).delete(existingBook);
    }

    @Test
    public void testDeleteBookBookNotExists() {
        long bookId = 1L;

        given(bookRepository.findById(bookId)).willReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> bookService.deleteBook(bookId)
        );

        assertEquals("Book with id: " + bookId + " is not found", exception.getMessage());
    }
}

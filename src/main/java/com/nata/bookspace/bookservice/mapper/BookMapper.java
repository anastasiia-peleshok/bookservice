package com.nata.bookspace.bookservice.mapper;

import com.nata.bookspace.bookservice.dto.BookDTO;
import com.nata.bookspace.bookservice.dto.FeedbackDTO;
import com.nata.bookspace.bookservice.entity.Annotation;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.Feedback;
import com.nata.bookspace.bookservice.entity.Genre;

public class BookMapper {
    public static BookDTO mapToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                book.getGenre(),
                book.getAnnotation().getDescription()
        );

        return bookDTO;
    }

    public static Book mapToBook(BookDTO bookDto) {
        Long id = bookDto.getId();
        String name = bookDto.getName();
        String author = bookDto.getAuthor();
        Genre genre = bookDto.getGenre();
        String annotationDescription = bookDto.getAnnotation(); // Отримуємо стрічкове представлення анотації

        // Мапимо стрічку анотації на об'єкт Annotation
        Annotation annotation = new Annotation();
        annotation.setDescription(annotationDescription);

        // Створюємо об'єкт Book з анотацією
        Book book = new Book(id, name, author, genre, annotation);

        // Встановлюємо зв'язок Book з Annotation
        annotation.setBook(book);

        return book;
    }
}

package com.nata.bookspace.bookservice.mapper;

import com.nata.bookspace.bookservice.dto.BookDTO;
import com.nata.bookspace.bookservice.entity.Annotation;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.Genre;

public class BookMapper {
    public static BookDTO mapToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getAnnotation().getDescription()
        );

        return bookDTO;
    }

    public static Book mapToBook(BookDTO bookDto) {
        Long id = bookDto.getId();
        String name = bookDto.getTitle();
        String author = bookDto.getAuthor();
        Genre genre = bookDto.getGenre();
        String annotationDescription = bookDto.getAnnotation();

        Annotation annotation = new Annotation();
        annotation.setDescription(annotationDescription);

        Book book = new Book(id, name, author, genre, annotation);

        annotation.setBook(book);

        return book;
    }
}

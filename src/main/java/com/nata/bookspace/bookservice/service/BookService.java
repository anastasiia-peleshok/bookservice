package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.BookDTO;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDTO> getBooks();
    List<BookDTO> getBooksByGenre(Genre genre);
    Optional<BookDTO> getBookById(long theId);
    Book saveBook(BookDTO theBookDTO);
    Book editBook(long theBookId,BookDTO theBookDTO);
    void deleteBook(long theId);
}

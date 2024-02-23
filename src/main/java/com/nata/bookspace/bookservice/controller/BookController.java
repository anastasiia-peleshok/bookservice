package com.nata.bookspace.bookservice.controller;

import com.nata.bookspace.bookservice.dto.BookDTO;
import com.nata.bookspace.bookservice.entity.Book;
import com.nata.bookspace.bookservice.entity.Genre;
import com.nata.bookspace.bookservice.service.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books/")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<BookDTO>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("{book_id}")
    @ResponseBody
    public ResponseEntity<BookDTO> getBookById(@PathVariable long book_id) {
        return ResponseEntity.ok(bookService.getBookById(book_id).get());
    }

    @GetMapping("genre/{genre}")
    @ResponseBody
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@PathVariable Genre genre) {
        return ResponseEntity.ok(bookService.getBooksByGenre(genre));
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<Book> saveBook(@RequestBody BookDTO theBookDTO) {
        return ResponseEntity.ok(bookService.saveBook(theBookDTO));
    }

    @PutMapping("{book_id}")
    @ResponseBody
    public ResponseEntity<Book> editBook(@PathVariable long book_id, @RequestBody BookDTO theBookDTO) {
        return ResponseEntity.ok(bookService.editBook(book_id, theBookDTO));
    }
}

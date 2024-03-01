package com.prakhar.booksystem.controller;

import com.prakhar.booksystem.model.Book;
import com.prakhar.booksystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity getBook(@PathVariable int id) {
        return bookService.getBook(id);
    }

    @PostMapping
    public ResponseEntity addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBook(@PathVariable int id, @RequestBody Book updateBook) {
        return bookService.updateBook(id, updateBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable int id) {
        return bookService.deleteBook(id);
    }

}

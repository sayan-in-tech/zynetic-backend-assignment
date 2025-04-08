package com.sayan.bookstore.controller;

import com.sayan.bookstore.model.Book;
import com.sayan.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/books")
@PreAuthorize("isAuthenticated()")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }


    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(title));
    }


    @GetMapping("/filter/author")
    public ResponseEntity<List<Book>> filterBooksByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.filterBooksByAuthor(author));
    }


    @GetMapping("/filter/category")
    public ResponseEntity<List<Book>> filterBooksByCategory(@RequestParam String category) {
        return ResponseEntity.ok(bookService.filterBooksByCategory(category));
    }


    @GetMapping("/filter/rating")
    public ResponseEntity<List<Book>> filterBooksByRating(@RequestParam double rating) {
        return ResponseEntity.ok(bookService.filterBooksByRating(rating));
    }
}

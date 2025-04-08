package com.sayan.bookstore.service;


import com.sayan.bookstore.model.Book;
import com.sayan.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(String id, Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setCategory(updatedBook.getCategory());
            book.setPrice(updatedBook.getPrice());
            book.setRating(updatedBook.getRating());
            book.setPublishedDate(updatedBook.getPublishedDate());
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public List<Book> filterBooksByAuthor(String author) {
        return bookRepository.findByAuthorIgnoreCase(author);
    }

    public List<Book> filterBooksByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category);
    }

    public List<Book> filterBooksByRating(double rating) {
        return bookRepository.findByRatingGreaterThanEqual(rating);
    }
}
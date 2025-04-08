package com.sayan.bookstore.repository;


import com.sayan.bookstore.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {


    @Query("{'title': { $regex: ?0, $options: 'i' }}")
    List<Book> findByTitleContaining(String title);


    List<Book> findByAuthorIgnoreCase(String author);
    List<Book> findByCategoryIgnoreCase(String category);
    List<Book> findByRatingGreaterThanEqual(double rating);
}

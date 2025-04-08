package com.sayan.bookstore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String category;
    private double price;
    private double rating;
    private LocalDate publishedDate;
}
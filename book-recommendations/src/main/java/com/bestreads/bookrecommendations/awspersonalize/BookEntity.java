package com.bestreads.bookrecommendations.awspersonalize;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BookEntity {
    @Id
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private String thumbnail;

    public BookEntity() {

    }

    public BookEntity(String isbn, String title, String author, String genre, String publisher, String thumbnail) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }




}

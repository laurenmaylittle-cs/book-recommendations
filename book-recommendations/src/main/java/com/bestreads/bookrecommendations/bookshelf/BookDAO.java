package com.bestreads.bookrecommendations.bookshelf;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
class BookDAO implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String author;

  private String thumbnail;

  private String publishedDate;

  @Column(unique = true, length = 20)
  private String isbn;

  private String genre;

  private String publisher;

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPublishedDate() {
    return publishedDate;
  }

  public void setPublishedDate(String publishedDate) {
    this.publishedDate = publishedDate;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookDAO bookDAO = (BookDAO) o;
    return id.equals(bookDAO.id) && title.equals(bookDAO.title) && author.equals(bookDAO.author)
        && thumbnail.equals(bookDAO.thumbnail) && publishedDate.equals(bookDAO.publishedDate)
        && isbn.equals(bookDAO.isbn) && genre.equals(bookDAO.genre) && publisher.equals(
        bookDAO.publisher);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, author, thumbnail, publishedDate, isbn, genre, publisher);
  }
}

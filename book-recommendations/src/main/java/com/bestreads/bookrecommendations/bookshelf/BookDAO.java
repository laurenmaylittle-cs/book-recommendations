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

  @Column(unique = true, length = 20)
  private String isbn;

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
        && thumbnail.equals(bookDAO.thumbnail) && isbn.equals(bookDAO.isbn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, author, thumbnail, isbn);
  }
}

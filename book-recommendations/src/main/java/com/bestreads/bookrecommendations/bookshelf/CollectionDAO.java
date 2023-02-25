package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.book.BookDAO;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "collection")
class CollectionDAO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  private String userId;

  @ManyToMany
  @JoinTable(name = "books_collection",
      joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "isbn", referencedColumnName = "isbn"))
  private Set<BookDAO> bookDAOS = new LinkedHashSet<>();

  public CollectionDAO() {
  }

  public CollectionDAO(String name, String userId, Set<BookDAO> bookDAOS) {
    this.name = name;
    this.userId = userId;
    this.bookDAOS = bookDAOS;
  }

  public Set<BookDAO> getBookDAOS() {
    return bookDAOS;
  }

  public Set<BookDAO> getBookDaos() {
    return bookDAOS;
  }

  public void setBookDaos(Set<BookDAO> bookDAOS) {
    this.bookDAOS = bookDAOS;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}

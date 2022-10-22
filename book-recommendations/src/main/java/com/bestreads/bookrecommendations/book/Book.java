package com.bestreads.bookrecommendations.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {

  private String title;

  private List<String> authors;
  private String publisher;
  private String publishedDate;
  private String description;
  private int pageCount;
  private ArrayList<String> categories;
  private ImageLinks imageLinks;
  private String language;
  private int averageRating;
  private int ratingsCount;

  public Book() {

  }

  public Book(String title, List<String> authors, String publisher, String publishedDate,
      String description, int pageCount, ArrayList<String> categories, ImageLinks imageLinks,
      String language, int averageRating, int ratingsCount) {
    this.title = title;
    this.authors = authors;
    this.publisher = publisher;
    this.publishedDate = publishedDate;
    this.description = description;
    this.pageCount = pageCount;
    this.categories = categories;
    this.imageLinks = imageLinks;
    this.language = language;
    this.averageRating = averageRating;

    this.ratingsCount = ratingsCount;
  }


  public String title() {
    return title;
  }

  public List<String> authors() {
    return authors;
  }

  public String publisher() {
    return publisher;
  }

  public String publishedDate() {
    return publishedDate;
  }

  public String description() {
    return description;
  }

  public int pageCount() {
    return pageCount;
  }

  public ArrayList<String> categories() {
    return categories;
  }

  public ImageLinks imageLinks() {
    return imageLinks;
  }

  public String language() {
    return language;
  }

  public int averageRating() {
    return averageRating;
  }

  public int ratingsCount() {
    return ratingsCount;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthors(List<String> authors) {
    this.authors = authors;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setPublishedDate(String publishedDate) {
    this.publishedDate = publishedDate;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public void setCategories(ArrayList<String> categories) {
    this.categories = categories;
  }

  public void setImageLinks(ImageLinks imageLinks) {
    this.imageLinks = imageLinks;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setAverageRating(int averageRating) {
    this.averageRating = averageRating;
  }

  public void setRatingsCount(int ratingsCount) {
    this.ratingsCount = ratingsCount;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (Book) obj;
    return Objects.equals(this.title, that.title) &&
        Objects.equals(this.authors, that.authors) &&
        Objects.equals(this.publisher, that.publisher) &&
        Objects.equals(this.publishedDate, that.publishedDate) &&
        Objects.equals(this.description, that.description) &&
        this.pageCount == that.pageCount &&
        Objects.equals(this.categories, that.categories) &&
        Objects.equals(this.imageLinks, that.imageLinks) &&
        Objects.equals(this.language, that.language) &&
        this.averageRating == that.averageRating &&
        this.ratingsCount == that.ratingsCount;
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, authors, publisher, publishedDate, description, pageCount,
        categories, imageLinks, language, averageRating, ratingsCount);
  }

  @Override
  public String toString() {
    return "Book[" +
        "title=" + title + ", " +
        "authors=" + authors + ", " +
        "publisher=" + publisher + ", " +
        "publishedDate=" + publishedDate + ", " +
        "description=" + description + ", " +
        "pageCount=" + pageCount + ", " +
        "categories=" + categories + ", " +
        "imageLinks=" + imageLinks + ", " +
        "language=" + language + ", " +
        "averageRating=" + averageRating + ", " +
        "ratingsCount=" + ratingsCount + ']';
  }

}

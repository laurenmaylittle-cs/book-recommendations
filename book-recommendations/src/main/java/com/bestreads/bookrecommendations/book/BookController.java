package com.bestreads.bookrecommendations.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class BookController {

  private final BookSearchService bookSearchService;

  @Autowired
  public BookController(BookSearchService bookSearchService) {
    this.bookSearchService = bookSearchService;
  }

  @GetMapping("/public/book")
  public Book getBookInfo(@Param("isbn") String isbn, @Param("title") String title,
      @Param("authors") String authors) {
    try {
      return bookSearchService.getBookData(isbn, title, authors);
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Book not found for given ISBN %s and title %s".formatted(isbn, title));
    }
  }
}

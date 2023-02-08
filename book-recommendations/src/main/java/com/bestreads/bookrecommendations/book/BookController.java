package com.bestreads.bookrecommendations.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    return bookSearchService.getBookData(isbn, title, authors);
  }
}

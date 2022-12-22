package com.bestreads.bookrecommendations.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {

  private final BookSearchService bookSearchService;
  private final RatingsService ratingsService;

  @Autowired
  public BookController(BookSearchService bookSearchService, RatingsService ratingsService) {
    this.bookSearchService = bookSearchService;
    this.ratingsService = ratingsService;
  }

  @GetMapping("/public/book")
  public Book getBookInfo(@Param("isbn") String isbn) {
    return bookSearchService.getBookByIsbn(isbn);
  }

  @GetMapping("private/user-rating")
  public Integer getUsersRating(@Param("isbn") String isbn, @Param("email") String email) {
    return ratingsService.getUsersRating(isbn, email);
  }

  @PostMapping("private/user-rating")
  public void saveUserRating(@Param("isbn") String isbn, @Param("email") String email,
      @Param("rating") Integer rating) {
    ratingsService.saveUsersRating(isbn, email, rating);
  }
}

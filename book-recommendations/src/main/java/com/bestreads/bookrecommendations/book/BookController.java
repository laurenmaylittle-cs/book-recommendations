package com.bestreads.bookrecommendations.book;

import com.bestreads.bookrecommendations.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class BookController {

  private final BookSearchService bookSearchService;
  private final BookDAOService bookDAOService;

  @Autowired
  public BookController(BookSearchService bookSearchService, BookDAOService bookDAOService) {
    this.bookSearchService = bookSearchService;
    this.bookDAOService = bookDAOService;
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

  @PostMapping("/private/book/add-book")
  public void addBookToDb(JwtAuthenticationToken jwtAuthenticationToken, @RequestBody Book book) {
    var userId = AuthUtils.getUserId(jwtAuthenticationToken).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
    });

    if (!book.isbn().isEmpty()) {
      bookDAOService.addBookToDb(book);
    }
  }
}

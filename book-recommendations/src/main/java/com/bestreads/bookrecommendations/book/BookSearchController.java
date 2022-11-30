package com.bestreads.bookrecommendations.book;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class BookSearchController {

  private final BookSearchService bookSearchService;

  @Autowired
  public BookSearchController(BookSearchService bookSearchService) {
    this.bookSearchService = bookSearchService;
  }

  @GetMapping("/author")
  public List<Book> searchByAuthor(@Param("author") String author) {
    return bookSearchService.searchByAuthor(author, 10);
  }
}

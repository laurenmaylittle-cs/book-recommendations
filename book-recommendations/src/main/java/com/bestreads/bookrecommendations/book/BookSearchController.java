package com.bestreads.bookrecommendations.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class BookSearchController {

  private final BookSearchService bookSearchService;

  @Autowired
  public BookSearchController(BookSearchService bookSearchService) {
    this.bookSearchService = bookSearchService;
  }

  @GetMapping("/author")
  public List<Book> searchByAuthor(@Param("author") String author,
                                   @Param("startIndex") int startIndex) {
    return bookSearchService.searchByAuthor(author, startIndex, 40);
  }

  @GetMapping("/title")
  public List<Book> searchByTitle(@Param("title") String title,
                                  @Param("startIndex") int startIndex) {
    return bookSearchService.searchByTitle(title, startIndex,  40);
  }
}

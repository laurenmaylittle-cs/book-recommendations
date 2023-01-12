package com.bestreads.bookrecommendations.book;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookSearchController.class)
class BookDaoSearchControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookSearchService bookSearchService;

  private static List<Book> bookList = new ArrayList<>();
  private static final String bookJson =
      """ 
              [{
              "title": "title",
                "authors": [
              "author1"
              ],
              "publisher": "publisher",
                "publishedDate": "2020",
                "description": "description",
                "pageCount": 200,
                "categories": null,
                "imageLinks": null,
                "language": "english",
                "averageRating": 5,
                "ratingsCount": 10
            }
          ]""";

  @BeforeAll
  static void setUp() {
    Book book = new Book("title", List.of("author1"), "publisher", "2020",
        "description", 200, null, null, "english", 5, 10);
    bookList = List.of(book);
  }

  @Test
  @WithMockUser
  void searchByAuthor() throws Exception {
    Mockito.when(bookSearchService.searchByAuthor("author1", 10))
        .thenReturn(bookList);
    mockMvc.perform(get("/api/search/author?author=author1"))
        .andExpect(content().json(bookJson));
  }
}

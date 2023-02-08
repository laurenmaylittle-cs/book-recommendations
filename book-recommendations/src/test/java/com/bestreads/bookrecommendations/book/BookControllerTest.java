package com.bestreads.bookrecommendations.book;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookSearchService bookSearchService;

  private static Book book;
  private static final String bookJson =
      """ 
              {
                     "title": "Gone Girl",
                     "authors": [
                       "Gillian Flynn"
                     ],
                     "publisher": "Hachette UK",
                     "publishedDate": "2012-05-24",
                      "description": "Test description",
                     "pageCount": 320,
                     "categories": [
                       "Fiction"
                     ],
                     "imageLinks": {
                       "smallThumbnail": "http://books.google.com/books/content?id=hxL2qWMAgv8C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                       "thumbnail": "http://books.google.com/books/content?id=hxL2qWMAgv8C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                     },
                     "language": "en",
                     "averageRating": 3,
                     "ratingsCount": 962
                   }
          """;

  String ISBN = "9780297859406";
  String bookTitle = "Gone Girl";
  String bookAuthor = "Gillian Flynn";

  @BeforeAll
  static void setUp() {
    book = new Book(
        "id",
        "Gone Girl",
        List.of("Gillian Flynn"),
        "Hachette UK",
        "2012-05-24",
        "Test description",
        320,
        List.of("Fiction"),
        new ImageLinks(
            "http://books.google.com/books/content?id=hxL2qWMAgv8C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
            "http://books.google.com/books/content?id=hxL2qWMAgv8C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"),
        "en",
        3,
        962,
        "9780735211292"
    );
  }

  @Test
  @WithMockUser
  void getBookInfo() throws Exception {
    when(bookSearchService.getBookData(ISBN, bookTitle, bookAuthor)).thenReturn(book);
    mockMvc.perform(
            get("/api/public/book")
                .param("isbn", ISBN)
                .param("title", bookTitle)
                .param("authors", bookAuthor))
        .andExpect(content().json(bookJson));
  }
}

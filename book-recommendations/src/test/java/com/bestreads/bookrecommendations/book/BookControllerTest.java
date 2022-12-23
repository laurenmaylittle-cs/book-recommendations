package com.bestreads.bookrecommendations.book;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

  @MockBean
  private RatingsService ratingsService;

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
  String email = "lml22@kent.ac.uk";
  Integer rating = 4;

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
        962
    );
  }

  @Test
  @WithMockUser
  void getBookInfo() throws Exception {
    when(bookSearchService.getBookByIsbn(ISBN)).thenReturn(book);
    mockMvc.perform(
            get("/api/public/book")
                .param("isbn", ISBN))
        .andExpect(content().json(bookJson));
  }

  @Test
  @WithMockUser
  void getUsersRating() throws Exception {
    when(ratingsService.getUsersRating(ISBN, email))
        .thenReturn(rating);
    mockMvc.perform(
            get("/api/private/user-rating")
                .param("isbn", ISBN)
                .param("email", email))
        .andExpect(content().string("4"));
  }

  @Test
  @WithMockUser
  void saveUserRating() throws Exception {
    mockMvc.perform(
            post("/api/private/user-rating")
                .with(csrf())
                .param("isbn", ISBN)
                .param("email", email)
                .param("rating", String.valueOf(rating))
        )
        .andExpect(status().is2xxSuccessful());
    verify(ratingsService).saveUsersRating(ISBN, email, rating);
  }

  @Test
  @WithMockUser
  void updateUserRating() throws Exception {
    mockMvc.perform(
        post("/api/private/update-user-rating")
            .with(csrf())
            .param("isbn", ISBN)
            .param("email", email)
            .param("rating", String.valueOf(rating))
    );
    verify(ratingsService).updateUsersRating(ISBN, email, rating);
  }
}

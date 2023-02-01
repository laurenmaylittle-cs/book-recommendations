package com.bestreads.bookrecommendations.homepage;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.ImageLinks;
import com.bestreads.bookrecommendations.nytimesapi.Category;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HomePageRestController.class)
class HomePageRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BestSellersService bestSellersService;

  @Test
  @WithMockUser
  void getListOfBestSellers() throws Exception {

    when(bestSellersService.getBestSellers()).thenReturn(
        List.of(
            new Category(
                123,
                "Hardcover Fiction",
                List.of(
                    new Book(
                        "Test Book",
                        List.of("Test Author"),
                        "Test Description",
                        "Test Publisher",
                        new ImageLinks("smallThumbnail", "thumbnail")
                        , "9780735211292"
                    )
                )
            )
        )
    );

    var expectedJson = """
        [
          {
            "list_id": 123,
            "list_name": "Hardcover Fiction",
            "books": [
              {
                "title": "Test Book",
                "authors": [
                  "Test Author"
                ],
                "publisher": "Test Description",
                "publishedDate": "Unknown",
                "description": "Test Publisher",
                "pageCount": 0,
                "categories": [],
                "imageLinks": {
                  "smallThumbnail": "smallThumbnail",
                  "thumbnail": "thumbnail"
                },
                "language": "Unknown",
                "averageRating": 0,
                "ratingsCount": 0
              }
            ]
          }
        ]
        """;

    mockMvc.perform(get("/api/home/best-sellers"))
        .andExpect(content().json(expectedJson));
  }

}

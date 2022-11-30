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

    @BeforeAll
    static void setUp() {
        ArrayList<String> genres = new ArrayList<>();
        genres.add("Fiction");
        book = new Book(
                "Gone Girl",
                List.of("Gillian Flynn"),
                "Hachette UK",
                "2012-05-24",
                "Test description",
                320,
                genres,
                new ImageLinks("http://books.google.com/books/content?id=hxL2qWMAgv8C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api", "http://books.google.com/books/content?id=hxL2qWMAgv8C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"),
                "en",
                3,
                962
        );
    }

    @Test
    @WithMockUser
    void getBookInfo() throws Exception {
        String ISBN = "9780297859406";
        Mockito.when(bookSearchService.getBookByIsbn(ISBN)).thenReturn(book);
        mockMvc.perform(get("/api/public/book").param("isbn", ISBN)).andExpect(content().json(bookJson));
    }
}
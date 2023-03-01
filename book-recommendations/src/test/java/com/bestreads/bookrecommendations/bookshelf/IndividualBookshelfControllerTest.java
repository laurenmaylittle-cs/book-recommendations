package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.book.BookDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IndividualBookshelfController.class)
class IndividualBookshelfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollectionsService collectionsService;

    private final String userId = "123";

    BookDAO book = new BookDAO();

    @BeforeEach
    void setup() {
        book.setId(1L);
        book.setTitle("Book");
        book.setAuthor("author");
        book.setIsbn("1234");
        book.setThumbnail("web.com");
        book.setPublishedDate("2017");
    }

    @Test
    void getBooksInCollection_whenUnauthenticatedThen401() throws Exception {
        mockMvc.perform(get("/api/private/bookshelf/books/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getBooksInCollection() throws Exception {

        when(collectionsService.getBooksInCollectionByIdAndUserOrBadRequest(anyLong(), any()))
                .thenReturn(List.of(book));
        var expectedJson = """
        [
          {
            "id": 1,
            "title": "Book",
            "author": "author",
            "isbn": "1234",
            "thumbnail": "web.com",
            "publishedDate": "2017"
          }
        ]
        """;

        mockMvc.perform(get("/api/private/bookshelf/books?bookshelfId=1")
                        .with(jwt().jwt((jwt) -> jwt.claim("sub", userId))))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getBooksInCollection_whenNoBooksInCollection() throws Exception {

        when(collectionsService.getBooksInCollectionByIdAndUserOrBadRequest(anyLong(), any()))
                .thenReturn(Collections.emptyList());
        var expectedJson = """
        [
        ]
        """;

        mockMvc.perform(get("/api/private/bookshelf/books?bookshelfId=1")
                        .with(jwt().jwt((jwt) -> jwt.claim("sub", userId))))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
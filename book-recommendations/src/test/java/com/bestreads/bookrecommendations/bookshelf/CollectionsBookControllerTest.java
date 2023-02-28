package com.bestreads.bookrecommendations.bookshelf;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CollectionsBookController.class)
class CollectionsBookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CollectionsBookService collectionsBookService;

  private String userId = "userId1";

  @Test
  void getCollectionsForBook_whenUnauthenticatedThen401() throws Exception {
    mockMvc.perform(get("/api/private/bookshelf/collections/book"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getCollectionsForBookTest() throws Exception {
    String isbn = "9780735211292";

    when(collectionsBookService.getCollectionsForBook(userId, isbn)).thenReturn(
        new LinkedHashSet<>(Set.of(new CollectionBookJson(1L, "Test Collection", true)))
    );

    var expectedJSON = """
        [
          {
            "id": 1,
            "name": "Test Collection",
            "enabled": true
          }
        ]
        """;

    mockMvc.perform(get("/api/private/bookshelf/collections/book")
            .with(jwt().jwt((jwt) -> jwt.claim("sub", userId)))
            .param("isbn", isbn))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedJSON));

  }

  @Test
  void updateCollectionsForBook_whenUnauthenticatedThen401() throws Exception {
    mockMvc.perform(put("/api/private/bookshelf/collections/book"))
        .andExpect(status().isForbidden());
  }

  @Test
  void updateCollectionsForBookTest() throws Exception {
    String isbn = "9780735211292";

    ArgumentCaptor<CollectionBookRootJson> collectionBookCaptor = ArgumentCaptor.forClass(
        CollectionBookRootJson.class);

    when(
        collectionsBookService.updateCollectionsForBook(eq(userId), collectionBookCaptor.capture(),
            eq(isbn)))
        .thenReturn(new HashSet<>(Set.of(new CollectionBookJson(1L, "Winter", true))));

    var requestBody = """
          {
            "collections": [
              {
                "id": 1,
                "name": "Winter",
                "enabled": true
              }
            ],
            "book": {
              "id": "rruOEAAAQBAJ",
              "title": "Atomic Habits",
              "authors": [
                "James Clear"
              ],
              "publisher": "National Geographic Books",
              "publishedDate": "2018-10-16",
              "description": "The #1 book",
              "pageCount": 0,
              "categories": [
                "Business & Economics"
              ],
              "imageLinks": {
                "smallThumbnail": "http://thubmnail_url1",
                "thumbnail": "http://thubmnail_url2"
              },
              "isbn": "9780735211292"
            }
          }                  
        """;

    var expectedJSON = """
        [
          {
            "id": 1,
            "name": "Winter",
            "enabled": true
          }
        ]
        """;

    mockMvc.perform(put("/api/private/bookshelf/collections/book")
            .with(jwt().jwt((jwt) -> jwt.claim("sub", userId)))
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedJSON));

    verify(collectionsBookService).updateCollectionsForBook(eq(userId),
        collectionBookCaptor.capture(),
        eq(isbn));

    CollectionBookRootJson capturedCollectionBook = collectionBookCaptor.getValue();
    assertTrue("1", capturedCollectionBook.collections().size() == 1);

    CollectionBookJson capturedCollection = capturedCollectionBook.collections().get(0);
    assertTrue("Winter", capturedCollection.name().equals("Winter"));
    assertTrue("true", capturedCollection.enabled().equals(true));
  }

}

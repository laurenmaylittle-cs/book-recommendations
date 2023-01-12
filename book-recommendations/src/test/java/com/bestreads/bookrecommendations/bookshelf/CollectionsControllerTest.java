package com.bestreads.bookrecommendations.bookshelf;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bestreads.bookrecommendations.bookshelf.json.CollectionJson;
import com.bestreads.bookrecommendations.bookshelf.model.Collection;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CollectionsController.class)
class CollectionsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CollectionsService collectionsService;

  private String userId = "123";

  @Test
  void getCollections_whenUnauthenticatedThen401() throws Exception {
    mockMvc.perform(get("/api/private/bookshelf/collections"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getCollections() throws Exception {
    when(collectionsService.getCollections(userId)).thenReturn(
        Set.of(new CollectionJson(1L, "Test Collection"))
    );

    var expectedJson = """
        [
          {
            "id": 1,
            "name": "Test Collection"
          }
        ]
        """;

    mockMvc.perform(get("/api/private/bookshelf/collections")
            .with(jwt().jwt((jwt) -> jwt.claim("sub", userId))))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedJson));
  }

  @Test
  void whenUserIdNotPresent_returnsBadRequest() throws Exception {
    mockMvc.perform(get("/api/private/bookshelf/collections")
            .with(jwt().jwt((jwt) -> jwt.claim("sub", null))))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getCollectionById_whenUnauthenticatedThen401() throws Exception {
    mockMvc.perform(get("/api/private/bookshelf/collections/1"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getCollectionById() throws Exception {
    var collection = new Collection();
    collection.setId(10L);
    collection.setName("Summer Reading");

    when(collectionsService.getCollectionById(10L)).thenReturn(
        collection
    );

    mockMvc.perform(get("/api/private/bookshelf/collections/10")
            .with(jwt().jwt((jwt) -> jwt.claim("sub", userId))))
        .andExpect(status().isOk())
        .andExpect(content().json("""
            {
              "id": 10,
              "name": "Summer Reading"
            }
            """));
  }

  @Test
  void createCollection() throws Exception {
    var baseURL = "http://localhost";

    var collection = new Collection();
    collection.setId(10L);
    collection.setName("Summer Reading");

    when(collectionsService.createNewCollection(userId, "Summer Reading")).thenReturn(
        collection
    );

    mockMvc.perform(post("/api/private/bookshelf/collections")
            .param("name", "Summer Reading")
            .with(jwt().jwt((jwt) -> jwt.claim("sub", userId))))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", baseURL + "/api/private/bookshelf/collections/10"));

  }

  @Test
  void createCollection_whenUserIdNotPresent() throws Exception {
    mockMvc.perform(post("/api/private/bookshelf/collections")
            .param("name", "Summer Reading")
            .with(jwt().jwt((jwt) -> jwt.claim("sub", null))))
        .andExpect(status().isBadRequest());
  }
}

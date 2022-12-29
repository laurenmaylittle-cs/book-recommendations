package com.bestreads.bookrecommendations.users;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.bestreads.bookrecommendations.auth0.Auth0Service;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private Auth0Service auth0Service;

  @Test
  @WithMockUser
  void searchUsers() throws Exception {

    when(auth0Service.searchUsersByEmail("lml22@kent.ac.uk")).thenReturn(
        List.of(
            new User(
                "lml22@kent.ac.uk",
                true,
                "lauren",
                "https://google.com")
        )
    );

    var expectedJson = """
        [
          {
            "email":"lml22@kent.ac.uk",
            "emailVerified":true,
            "name":"lauren",
            "picture":"https://google.com"
          }
        ]
          """;

    mockMvc.perform(get("/api/private/users")
            .param("email", "lml22@kent.ac.uk"))
        .andExpect(content().json(expectedJson));

//    mockMvc.perform(get("/api/private/users")
//        .param("email", "lml22@kent.ac.uk")).andReturn().getResponse().getContentAsString()
  }
}
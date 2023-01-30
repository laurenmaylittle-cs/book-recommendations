package com.bestreads.bookrecommendations.rating;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RatingsController.class)
class RatingsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RatingsService ratingsService;

  String ISBN = "9780297859406";
  String email = "lml@kent.ac.uk";
  Integer rating = 4;

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
        put("/api/private/update-user-rating")
            .with(csrf())
            .param("isbn", ISBN)
            .param("email", email)
            .param("rating", String.valueOf(rating))
    );
    verify(ratingsService).saveUsersRating(ISBN, email, rating);
  }
}
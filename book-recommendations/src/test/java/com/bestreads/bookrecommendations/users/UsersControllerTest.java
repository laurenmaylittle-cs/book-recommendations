package com.bestreads.bookrecommendations.users;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.bestreads.bookrecommendations.auth0.Auth0Service;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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

  @MockBean
  private UsersService usersService;

  private List<FollowersFollowing> followersFollowings;
  private static final String FOLLOWER_EMAIL = "lml@kent";
  private static final String FOLLOWING_EMAIL = "rm@kent";

  @BeforeEach
  void setUp() {
    var followers = new FollowersFollowing();
    followers.setFollowerEmail(FOLLOWER_EMAIL);
    followers.setFollowingEmail(FOLLOWING_EMAIL);

    followersFollowings = List.of(followers);
  }

  @Test
  @WithMockUser
  void searchUsers() throws Exception {

    when(auth0Service.searchUsersByName("lauren")).thenReturn(
        List.of(
            new User(
                "lml@kent.ac.uk",
                true,
                "lauren",
                "https://google.com",
                followersFollowings)
        )
    );

    var expectedJson = """
        [
          {
            "email":"lml@kent.ac.uk",
            "emailVerified":true,
            "name":"lauren",
            "picture":"https://google.com"
          }
        ]
          """;

    mockMvc.perform(get("/api/private/users")
            .param("name", "lauren"))
        .andExpect(content().json(expectedJson));

  }
}
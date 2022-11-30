package com.bestreads.bookrecommendations.Testing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bestreads.bookrecommendations.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TestingSecurityController.class)
@Import(SecurityConfig.class)
class TestingSecurityControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void returnPublic_openToPublic() throws Exception {
    mockMvc.perform(get("/api/public/get-public"))
        .andExpect(content().string("This is open to public"));
  }

  @Test
  void returnPrivate_whenUnauthenticatedThen401() throws Exception {
    mockMvc.perform(get("/api/private/get-private"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  void returnPrivate_whenAuthenticatedThenReturnsString() throws Exception {
    mockMvc.perform(get("/api/private/get-private"))
        .andExpect(status().isOk())
        .andExpect(content().string("This is only for private stuff"));
  }

  @Test
  void privateScoped_whenUnauthenticatedThen401() throws Exception {
    mockMvc.perform(get("/api/private-scoped/test"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  void privateScoped_whenNoPrivilegesThen403() throws Exception {
    mockMvc.perform(get("/api/private-scoped/test"))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(authorities = "SCOPE_read:messages")
  void privateScoped_withCorrectPrivilegesReturnsString() throws Exception {
    mockMvc.perform(get("/api/private-scoped/test"))
        .andExpect(status().isOk())
        .andExpect(content().string("Scoped private"));
  }
}

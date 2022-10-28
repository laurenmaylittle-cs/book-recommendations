package com.bestreads.bookrecommendations.branding;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BrandingController.class)
class BrandingControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BrandingService brandingService;

  @Test
  @WithMockUser
  void getBrandingForService() throws Exception {
    Mockito.when(brandingService.getServiceName()).thenReturn("BestReads");
    mockMvc.perform(get("/api/branding"))
        .andExpect(content().string("BestReads"));
  }
}
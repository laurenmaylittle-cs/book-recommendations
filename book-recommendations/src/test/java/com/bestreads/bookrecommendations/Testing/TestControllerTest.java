package com.bestreads.bookrecommendations.Testing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestBookRepository testBookRepository;

    @Test
    @WithMockUser
    void test() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(content().string("Hello"));
    }

    @Test
    @WithMockUser
    void getListOfBooks() throws Exception {
        var testBook = new TestBook();
        testBook.setId(1);
        testBook.setTitle("Test Book");
        when(testBookRepository.findAll()).thenReturn(List.of(testBook));

        mockMvc.perform(get("/get-list-of-books"))
                .andExpect(content().string("[{\"id\":1,\"title\":\"Test Book\"}]"));
    }
}

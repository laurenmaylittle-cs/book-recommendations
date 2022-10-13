package com.bestreads.bookrecommendations.Testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    public TestBookRepository testBookRepository;

    @Autowired
    public TestController(TestBookRepository testBookRepository) {
        this.testBookRepository = testBookRepository;
    }

    @GetMapping("/test")
    public String getTestPage() {
        return "Hello";
    }

    @GetMapping("/get-list-of-books")
    public List<TestBook> getListOfBooks() {
        return Streamable.of(testBookRepository.findAll())
                .stream()
                .toList();
    }
}

package com.bestreads.bookrecommendations.Testing;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    private final TestBookRepository testBookRepository;

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

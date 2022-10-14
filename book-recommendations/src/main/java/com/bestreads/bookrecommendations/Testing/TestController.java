package com.bestreads.bookrecommendations.Testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class TestController {

    TestBookRepository testBookRepository;

    @Autowired
    TestController(TestBookRepository testBookRepository) {
        this.testBookRepository = testBookRepository;
    }

    @GetMapping("/test")
    String getTestPage() {
        return "Hello";
    }

    @GetMapping("/get-list-of-books")
    List<TestBook> getListOfBooks() {
        return Streamable.of(testBookRepository.findAll())
                .stream()
                .toList();
    }
}

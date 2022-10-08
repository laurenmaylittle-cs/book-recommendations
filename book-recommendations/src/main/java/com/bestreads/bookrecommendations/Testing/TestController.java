package com.bestreads.bookrecommendations.Testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    public BookRepository bookRepository;

    @Autowired
    public TestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/test")
    public String getTestPage() {
        return "Hello";
    }

    @GetMapping("/get-list-of-books")
    public List<Book> getListOfBooks() {
        return bookRepository.findAll();
    }
}

package com.bestreads.bookrecommendations.book;

import com.bestreads.bookrecommendations.googlebooks.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndividualBookController {

    private final IndividualBookService individualBookService;

    @Autowired
    public IndividualBookController(IndividualBookService individualBookService) {
        this.individualBookService = individualBookService;
    }

    @GetMapping("/view-book")
    public Item getBookInfo(@Param("id") String id) {
        return individualBookService.viewBook(id);
    }
}

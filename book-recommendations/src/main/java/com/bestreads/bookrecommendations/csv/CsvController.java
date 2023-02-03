package com.bestreads.bookrecommendations.csv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CsvController {

    private final CsvService csvService;

    @Autowired
    public CsvController (CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("public/book")
    public void exportData(@Param("isbn") String isbn, @Param("title") String title,
                           @Param("author") String author, @Param("genre") String genre,
                           @Param("publisher") String publisher, @Param("userID") String userID) {
        csvService.printData(isbn, title, author, genre, publisher, userID);
    }
}

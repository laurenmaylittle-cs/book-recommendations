package com.bestreads.bookrecommendations.csv;

import com.bestreads.bookrecommendations.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.Instant;

@RestController
@RequestMapping("/api")
public class CsvController {

    private final CsvService csvService;

    @Autowired
    public CsvController (CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("public/book")
    public void exportData(JwtAuthenticationToken authenticationToken,
                           @Param("isbn") String isbn, @Param("title") String title,
                           @Param("author") String author, @Param("genre") String genre,
                           @Param("publisher") String publisher) throws IOException {
        var userId = AuthUtils.getUserId(authenticationToken).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
        });
        var timestamp = Instant.now().getEpochSecond();
        var bookCsv = isbn + "," + title + "," + author + "," + genre + "," + publisher + "\n";
        var interactionCsv = userId + "," + isbn + "," + timestamp + "\n";

        csvService.addToCsv(bookCsv, "books.csv");
        csvService.addToCsv(interactionCsv, "interactions.csv");
    }
}

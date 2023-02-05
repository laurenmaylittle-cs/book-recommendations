package com.bestreads.bookrecommendations.csv;

import com.bestreads.bookrecommendations.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileInputStream;
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
    public void exportData(@RequestBody JwtAuthenticationToken authenticationToken,
                           @RequestBody String isbn, @RequestBody String title,
                           @RequestBody String author, @RequestBody String genre,
                           @RequestBody String publisher) throws IOException {
        var userId = AuthUtils.getUserId(authenticationToken).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
        });
        var timestamp = Instant.now().getEpochSecond();
        var bookCsv = isbn + "," + title + "," + author + "," + genre + "," + publisher + "\n";
        var interactionCsv = userId + "," + isbn + "," + timestamp + "\n";

        if (!csvService.checkIfBookExists(isbn)) {
            csvService.addToCsv(bookCsv, "books.csv");
        }
        csvService.addToCsv(interactionCsv, "interactions.csv");
    }

    @GetMapping("downloadBooksCsv")
    public ResponseEntity<InputStreamResource> downloadBookCsv() throws IOException {
        File booksCsv = new File("books.csv");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(booksCsv));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + booksCsv.getName())
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }

    @GetMapping("downloadInteractionsCsv")
    public ResponseEntity<InputStreamResource> downloadInteractionsCsv() throws IOException {
        File booksCsv = new File("interactions.csv");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(booksCsv));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + booksCsv.getName())
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }

}

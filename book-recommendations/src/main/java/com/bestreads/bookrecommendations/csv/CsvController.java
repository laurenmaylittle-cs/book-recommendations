package com.bestreads.bookrecommendations.csv;

import static java.lang.String.join;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.utils.AuthUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/private/book-data")
public class CsvController {

  private final CsvService csvService;

  @Autowired
  public CsvController(CsvService csvService) {
    this.csvService = csvService;
  }

  @PostMapping
  public void exportData(JwtAuthenticationToken jwtAuthenticationToken, @RequestBody Book book) {
    var userId = AuthUtils.getUserId(jwtAuthenticationToken).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
    });

    var authors = join("/", book.authors());
    var categories = join("/", book.categories());

    var bookCsv = "%s,%s,%s,%s,%s\n".formatted(book.isbn(), book.title(), authors,
        categories, book.publisher());
    var interactionCsv = "%s,%s,%d\n".formatted(userId, book.isbn(),
        Instant.now().getEpochSecond());

    if (!csvService.checkIfBookExists(book.isbn())) {
      csvService.addToCsv(bookCsv, "books.csv");
    }

    csvService.addToCsv(interactionCsv, "interactions.csv");
  }

  @GetMapping("/books")
  public ResponseEntity<InputStreamResource> downloadBookCsv() {
    try {
      File booksCsv = new File("books.csv");
      InputStreamResource resource = new InputStreamResource(new FileInputStream(booksCsv));
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION,
              "attachment;filename=" + booksCsv.getName())
          .contentType(MediaType.TEXT_PLAIN)
          .body(resource);
    } catch (IOException ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error with csv creation");
    }
  }

  @GetMapping("/interactions")
  public ResponseEntity<InputStreamResource> downloadInteractionsCsv() {
    try {
      File booksCsv = new File("interactions.csv");
      InputStreamResource resource = new InputStreamResource(new FileInputStream(booksCsv));
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION,
              "attachment;filename=" + booksCsv.getName())
          .contentType(MediaType.TEXT_PLAIN)
          .body(resource);
    } catch (IOException ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error with csv creation");
    }
  }

}

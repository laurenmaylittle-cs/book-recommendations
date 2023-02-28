package com.bestreads.bookrecommendations.awspersonalize;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.BookDAO;
import com.bestreads.bookrecommendations.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AwsPersonalizeController {

  private final AwsPersonalizeService awsPersonalizeService;

  @Value("${AWS_IS_ENABLED}")
  private boolean awsEnabled;

  @Autowired
  public AwsPersonalizeController(AwsPersonalizeService awsPersonalizeService) {
    this.awsPersonalizeService = awsPersonalizeService;
  }

  @GetMapping("/public/book/get-recommendations")
  public List<BookDAO> getRecs(@Param("isbn") String isbn) {
    return awsPersonalizeService.getRecs(isbn);
  }

  @PostMapping("/private/book")
  public void addBookToDb(JwtAuthenticationToken jwtAuthenticationToken, @RequestBody Book book) {
    var userId = AuthUtils.getUserId(jwtAuthenticationToken).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
    });

    if (!book.isbn().isEmpty()) {
      awsPersonalizeService.addBookToDb(book);
    }
  }

  @GetMapping("/private/book/is-aws-enabled")
  public boolean isAwsEnabled() {
    return awsEnabled;
  }
}
package com.bestreads.bookrecommendations.awspersonalize;

import com.bestreads.bookrecommendations.book.BookDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping("/public/book/recommendations")
  public List<BookDAO> getRecs(@Param("isbn") String isbn) {
    return awsPersonalizeService.getRecommendations(isbn);
  }

  @GetMapping("/public/book/personalise-status")
  public boolean isAwsEnabled() {
    return this.awsEnabled;
  }
}

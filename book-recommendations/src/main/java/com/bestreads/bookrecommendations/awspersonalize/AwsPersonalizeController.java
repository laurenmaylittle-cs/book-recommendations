package com.bestreads.bookrecommendations.awspersonalize;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.bookshelf.BookDAO;
import java.util.List;
import javax.annotation.PostConstruct;

import com.bestreads.bookrecommendations.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.personalize.PersonalizeClient;
import software.amazon.awssdk.services.personalizeevents.PersonalizeEventsClient;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;

import static java.lang.String.join;

@RestController
@RequestMapping("/api")
public class AwsPersonalizeController {

  private final AwsPersonalizeService awsPersonalizeService;
  private PersonalizeClient personalizeClient;
  private PersonalizeRuntimeClient personalizeRuntimeClient;
  private PersonalizeEventsClient personalizeEventsClient;

  @Value("${AWS_EVENT_TRACKER_ID}")
  private String awsEventTrackerId;

  @Value("${AWS_CAMPAIGN_ARN}")
  private String campaignArn;

  @Value("${AWS_REGION}")
  private Region region;

  @Value("${AWS_BOOKS_DATASET_ARN}")
  private String booksDatasetArn;

  @Autowired
  public AwsPersonalizeController(AwsPersonalizeService awsPersonalizeService) {
    this.awsPersonalizeService = awsPersonalizeService;
  }
  @PostConstruct
  private void initialiseAmazon() {
    this.personalizeClient = PersonalizeClient.builder()
        .region(region)
        .build();
    this.personalizeRuntimeClient = PersonalizeRuntimeClient.builder()
        .region(region)
        .build();
    this.personalizeEventsClient = PersonalizeEventsClient.builder()
            .region(region)
            .build();
  }

  @GetMapping("/public/book/get-recs")
  public List<BookDAO> getRecs(@Param("isbn") String isbn) {
    return awsPersonalizeService.getRecs(this.personalizeRuntimeClient, campaignArn, isbn);
  }

  @PostMapping("/private/book")
  public void addBook(JwtAuthenticationToken jwtAuthenticationToken, @RequestBody Book book) {
    var userId = AuthUtils.getUserId(jwtAuthenticationToken).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
    });
    var authors = join("/", book.authors());
    var categories = join("/", book.categories());

    awsPersonalizeService.addBookToDb(book.isbn(), book.title(), authors, categories, book.publisher(), book.imageLinks().thumbnail());
    awsPersonalizeService.putEvents(this.personalizeEventsClient, awsEventTrackerId, userId, userId, book.isbn());
    awsPersonalizeService.putItems(this.personalizeEventsClient, booksDatasetArn, book.isbn(), book.title(), authors, categories, book.publisher(), book.imageLinks().thumbnail());
  }
}
package com.bestreads.bookrecommendations.awspersonalize;

import com.bestreads.bookrecommendations.auth0.Auth0Service;
import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.bookshelf.BookDAO;
import java.util.List;
import javax.annotation.PostConstruct;

import com.bestreads.bookrecommendations.users.User;
import com.bestreads.bookrecommendations.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.personalizeevents.PersonalizeEventsClient;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;

import static java.lang.String.join;

@RestController
@RequestMapping("/api")
public class AwsPersonalizeController {

  private final AwsPersonalizeService awsPersonalizeService;
  private final Auth0Service auth0Service;
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

  @Value(("${AWS_USERS_DATASET_ARN}"))
  private String usersDatasetArn;

  @Autowired
  public AwsPersonalizeController(AwsPersonalizeService awsPersonalizeService, Auth0Service auth0Service) {
    this.awsPersonalizeService = awsPersonalizeService;
    this.auth0Service = auth0Service;
  }
  @PostConstruct
  private void initialiseAmazon() {
    this.personalizeRuntimeClient = PersonalizeRuntimeClient.builder()
        .region(region)
        .build();
    this.personalizeEventsClient = PersonalizeEventsClient.builder()
            .region(region)
            .build();
  }

  @GetMapping("/public/book/recommendations")
  public List<BookDAO> getRecs(@Param("isbn") String isbn) {
    return awsPersonalizeService.getRecommendations(this.personalizeRuntimeClient, campaignArn, isbn);
  }

  @PostMapping("/private/book/add-book")
  public void addBook(JwtAuthenticationToken jwtAuthenticationToken, @RequestBody Book book) {
    var userId = AuthUtils.getUserId(jwtAuthenticationToken).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
    });
    var formattedId = userId.replace("|", "%7C");
    var userData = auth0Service.searchById(formattedId);

    var authors = join("/", book.authors());
    var categories = join("/", book.categories());

    //Adds book to our database
    awsPersonalizeService.addBookToDb(book.isbn(), book.title(), authors, categories, book.publisher(), book.imageLinks().thumbnail());

    //Updates interactions for recommendations
    awsPersonalizeService.putEvents(this.personalizeEventsClient, awsEventTrackerId, userId, userId, book.isbn());

    //Updates books for recommendations
    awsPersonalizeService.putItems(this.personalizeEventsClient, booksDatasetArn, book);

    //Updates users for recommendations
    if (userData.isPresent()) {
      User userToAdd = userData.get();
      awsPersonalizeService.putUsers(this.personalizeEventsClient, usersDatasetArn, userId, userToAdd);
    }
  }
}


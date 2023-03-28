package com.bestreads.bookrecommendations.awspersonalize;

import com.bestreads.bookrecommendations.book.BookDAO;
import java.util.List;

import com.bestreads.bookrecommendations.book.BookDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bestreads.bookrecommendations.auth0.Auth0Service;
import com.bestreads.bookrecommendations.book.Book;

import com.bestreads.bookrecommendations.users.User;
import com.bestreads.bookrecommendations.utils.AuthUtils;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class AwsPersonalizeController {

  private final AwsPersonalizeService awsPersonalizeService;
  private final Auth0Service auth0Service;

  @Value("${AWS_IS_ENABLED}")
  private boolean awsEnabled;

  @Autowired
  public AwsPersonalizeController(AwsPersonalizeService awsPersonalizeService, Auth0Service auth0Service) {
    this.awsPersonalizeService = awsPersonalizeService;
    this.auth0Service = auth0Service;
  }

  @GetMapping("/public/book/recommendations")
  public List<BookDAO> getRecs(@Param("isbn") String isbn) {
    return awsPersonalizeService.getRecommendations(isbn);
  }

  @PostMapping("/private/book/update-aws")
  public void addBook(JwtAuthenticationToken jwtAuthenticationToken, @RequestBody Book book) {
    var userId = AuthUtils.getUserIdOrBadRequest(jwtAuthenticationToken);
    var formattedId = userId.replace("|", "%7C");
    var userData = auth0Service.searchById(formattedId);

    //Updates interactions for recommendations
    awsPersonalizeService.putEvents(userId, userId, book.isbn());

    //Updates books for recommendations
    awsPersonalizeService.putItems(book);

    //Updates users for recommendations
    if (userData.isPresent()) {
      User userToAdd = userData.get();
      awsPersonalizeService.putUsers(userId, userToAdd);
    }
  }

  @GetMapping("/public/book/personalise-status")
  public boolean isAwsEnabled() {
    return this.awsEnabled;
  }
}

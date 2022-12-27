package com.bestreads.bookrecommendations.users;

import com.bestreads.bookrecommendations.auth0.Auth0Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/users")
public class UsersController {

  private final Auth0Service auth0Service;

  @Autowired
  public UsersController(Auth0Service auth0Service) {
    this.auth0Service = auth0Service;
  }

  @GetMapping
  public List<User> searchUsers(@Param("email") String email) {
    return auth0Service.searchUsersByEmail(email);
  }
}

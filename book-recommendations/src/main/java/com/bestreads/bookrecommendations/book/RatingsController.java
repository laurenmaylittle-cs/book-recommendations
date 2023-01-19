package com.bestreads.bookrecommendations.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RatingsController {

  private final RatingsService ratingsService;

  @Autowired
  public RatingsController(RatingsService ratingsService) {
    this.ratingsService = ratingsService;
  }

  @GetMapping("private/user-rating")
  public Integer getUsersRating(@Param("isbn") String isbn, @Param("email") String email) {
    return ratingsService.getUsersRating(isbn, email);
  }

  @PostMapping("private/user-rating")
  public void saveUserRating(@Param("isbn") String isbn, @Param("email") String email,
      @Param("rating") Integer rating) {
    ratingsService.saveUsersRating(isbn, email, rating);
  }

  @PutMapping("private/update-user-rating")
  public void updateUserRating(@Param("isbn") String isbn, @Param("email") String email,
      @Param("rating") Integer rating) {
    ratingsService.updateUsersRating(isbn, email, rating);
  }
}

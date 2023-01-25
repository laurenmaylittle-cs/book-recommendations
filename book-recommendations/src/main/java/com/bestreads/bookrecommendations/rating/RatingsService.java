package com.bestreads.bookrecommendations.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class RatingsService {

  private final RatingsRepository ratingsRepository;

  @Autowired
  RatingsService(RatingsRepository ratingsRepository) {
    this.ratingsRepository = ratingsRepository;
  }

  Integer getUsersRating(String isbn, String email) {
    var userRating = ratingsRepository.findByIsbnAndEmail(isbn, email);

    if (userRating.isEmpty()) {
      return null;
    }

    return userRating.get().getRating();
  }

  void saveUsersRating(String isbn, String email, int userRating) {
    var userRatingFromDB = ratingsRepository.findByIsbnAndEmail(isbn, email);
    var rating = new Rating();
    rating.setIsbn(isbn);
    rating.setEmail(email);
    rating.setRating(userRating);

    if (userRatingFromDB.isEmpty()) {
      ratingsRepository.save(rating);
    } else {
      var existingRating = userRatingFromDB.get();
      existingRating.setRating(userRating);
      ratingsRepository.save(existingRating);
    }

  }

}

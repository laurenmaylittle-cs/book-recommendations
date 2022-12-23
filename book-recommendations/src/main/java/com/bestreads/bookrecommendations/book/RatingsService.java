package com.bestreads.bookrecommendations.book;

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
    var userRating = ratingsRepository.findAllByIsbnAndEmail(isbn, email);

    if (userRating.size() > 1) {
      //throw custom exception
      throw new IllegalStateException("There should only be one rating for each user and isbn");
    } else if (userRating.isEmpty()) {
      return null;
    }

    return userRating.get(0).getRating();
  }

  void saveUsersRating(String isbn, String email, int userRating) {
    var userRatingFromDB = ratingsRepository.findAllByIsbnAndEmail(isbn, email);
    var rating = new Rating();
    rating.setIsbn(isbn);
    rating.setEmail(email);
    rating.setRating(userRating);

    //check for valid rating, email, isbn??

    if (userRatingFromDB.isEmpty()) {
      ratingsRepository.save(rating);
    }

  }
}

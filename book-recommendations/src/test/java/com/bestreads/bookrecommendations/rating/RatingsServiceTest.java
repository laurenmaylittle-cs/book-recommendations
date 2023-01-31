package com.bestreads.bookrecommendations.rating;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RatingsServiceTest {

  @Mock
  private RatingsRepository ratingsRepository;

  @InjectMocks
  private RatingsService ratingsService;

  Rating rating = new Rating();
  private static final String ISBN = "9780297859406";
  private static final String EMAIL = "lml@kent.ac.uk";

  private static final int RATING = 4;

  @BeforeEach
  void setUp() {
    rating.setRating(RATING);
    rating.setIsbn(ISBN);
    rating.setEmail(EMAIL);
    rating.setId(1);
  }

  @Test
  void getUsersRating() {
    when(ratingsRepository.findByIsbnAndEmail(ISBN, EMAIL))
        .thenReturn(Optional.of(rating));

    var userRating = ratingsService.getUsersRating(ISBN, EMAIL);
    assertEquals(userRating, RATING);
  }

  @Test
  void getUsersRating_NoRatingExists() {
    when(ratingsRepository.findByIsbnAndEmail(ISBN, EMAIL))
        .thenReturn(Optional.empty());

    var userRating = ratingsService.getUsersRating(ISBN, EMAIL);
    assertNull(userRating);
  }


  @Test
  void saveUsersRating() {
    ArgumentCaptor<Rating> ratingArgumentCaptor = ArgumentCaptor.forClass(Rating.class);
    when(ratingsRepository.findByIsbnAndEmail(ISBN, EMAIL))
        .thenReturn(Optional.empty());
    ratingsService.saveUsersRating(ISBN, EMAIL, RATING);

    verify(ratingsRepository).save(ratingArgumentCaptor.capture());
    var userRating = ratingArgumentCaptor.getValue();
    assertEquals(userRating.getRating(), RATING);
    assertEquals(userRating.getEmail(), EMAIL);
    assertEquals(userRating.getIsbn(), ISBN);
  }

  @Test
  void updateUsersRating() {
    ArgumentCaptor<Rating> ratingArgumentCaptor = ArgumentCaptor.forClass(Rating.class);

    when(ratingsRepository.findByIsbnAndEmail(ISBN, EMAIL))
        .thenReturn(Optional.of(rating));

    ratingsService.saveUsersRating(ISBN, EMAIL, RATING);
    verify(ratingsRepository).save(ratingArgumentCaptor.capture());

    var userRating = ratingArgumentCaptor.getValue();
    assertEquals(userRating.getRating(), RATING);
    assertEquals(userRating.getEmail(), EMAIL);
    assertEquals(userRating.getIsbn(), ISBN);
  }
}
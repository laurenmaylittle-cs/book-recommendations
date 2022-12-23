package com.bestreads.bookrecommendations.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
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
  String ISBN = "9780297859406";
  String email = "lml22@kent.ac.uk";

  @BeforeEach
  void setUp() {
    rating.setRating(4);
    rating.setIsbn(ISBN);
    rating.setEmail(email);
    rating.setId(1);
  }

  @Test
  void getUsersRating() {
    when(ratingsRepository.findAllByIsbnAndEmail(ISBN, email))
        .thenReturn(List.of(rating));

    var userRating = ratingsService.getUsersRating(ISBN, email);
    assertEquals(userRating, 4);
  }

  @Test
  void saveUsersRating() {
    ArgumentCaptor<Rating> ratingArgumentCaptor = ArgumentCaptor.forClass(Rating.class);
    when(ratingsRepository.findAllByIsbnAndEmail(ISBN, email))
        .thenReturn(Collections.emptyList());
    ratingsService.saveUsersRating(ISBN, email, 4);

    verify(ratingsRepository).save(ratingArgumentCaptor.capture());
    var userRating = ratingArgumentCaptor.getValue();
    assertEquals(userRating.getRating(), 4);
    assertEquals(userRating.getEmail(), email);
    assertEquals(userRating.getIsbn(), ISBN);
  }

  @Test
  void updateUsersRating() {

  }
}
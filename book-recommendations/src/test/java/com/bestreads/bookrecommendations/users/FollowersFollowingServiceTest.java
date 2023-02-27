package com.bestreads.bookrecommendations.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FollowersFollowingServiceTest {

  @InjectMocks
  private FollowersFollowingService followersFollowingService;

  @Mock
  private FollowersFollowingRepository followersFollowingRepository;

  private List<FollowersFollowing> followersList;
  private static final String FOLLOWING_EMAIL = "lml@kent.ac.uk";
  private static final String FOLLOWER_EMAIL = "rm@kent.ac.uk";

  @BeforeEach
  void setUp() {
    var followers = new FollowersFollowing();
    followers.setFollowerEmail(FOLLOWER_EMAIL);
    followers.setFollowingEmail(FOLLOWING_EMAIL);
    followersList = List.of(followers);
  }

  @Test
  void getFollowers() {
    when(followersFollowingRepository.findAllByFollowingEmail(FOLLOWING_EMAIL))
        .thenReturn(followersList);
    var followers = followersFollowingService.getFollowers(FOLLOWING_EMAIL);
    assertThat(followers)
        .extracting(FollowersFollowing::getFollowerEmail, FollowersFollowing::getFollowingEmail)
        .containsExactly(
            tuple(FOLLOWER_EMAIL, FOLLOWING_EMAIL)
        );
  }
}
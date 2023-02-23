package com.bestreads.bookrecommendations.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bestreads.bookrecommendations.auth0.Auth0Service;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

  @Mock
  private FollowersFollowingRepository followersFollowingRepository;

  @Mock
  private Auth0Service auth0Service;

  @InjectMocks
  private UsersService usersService;

  private List<FollowersFollowing> followersList;
  private List<FollowersFollowing> followingList;
  private static final String FOLLOWER_EMAIL = "lml@kent.ac.uk";
  private static final String FOLLOWING_EMAIL = "rm@kent.ac.uk";

  @BeforeEach
  void setUp() {
    var followers = new FollowersFollowing();
    followers.setFollowerEmail(FOLLOWER_EMAIL);
    followers.setFollowingEmail(FOLLOWING_EMAIL);
    followersList = new ArrayList<>();
    followersList.add(followers);

    var following = new FollowersFollowing();
    following.setFollowingEmail(FOLLOWER_EMAIL);
    following.setFollowerEmail(FOLLOWING_EMAIL);
    followingList = new ArrayList<>();
    followingList.add(following);
  }

  @Test
  void unfollowUser() {
    when(followersFollowingRepository.findAllByFollowerEmail("lml@kent.ac.uk"))
        .thenReturn(followersList);
    usersService.unfollowUser(FOLLOWER_EMAIL, FOLLOWING_EMAIL);

    verify(followersFollowingRepository).delete(followersList.get(0));
  }

  @Test
  void followUser() {
    ArgumentCaptor<FollowersFollowing> followingArgumentCaptor = ArgumentCaptor.forClass(
        FollowersFollowing.class);
    usersService.followUser(FOLLOWER_EMAIL, FOLLOWING_EMAIL);

    verify(followersFollowingRepository).save(followingArgumentCaptor.capture());

    assertThat(followingArgumentCaptor.getValue())
        .extracting(FollowersFollowing::getFollowerEmail, FollowersFollowing::getFollowingEmail)
        .containsExactly(FOLLOWER_EMAIL, FOLLOWING_EMAIL);
  }

  @Test
  void getAllFollowersAndFollowing() {
    when(followersFollowingRepository.findAllByFollowingEmail(FOLLOWER_EMAIL))
        .thenReturn(followingList);
    when(followersFollowingRepository.findAllByFollowerEmail(FOLLOWER_EMAIL))
        .thenReturn(followersList);

    var user = new User(FOLLOWING_EMAIL, true, "rm", "url", followingList);

    when(auth0Service.searchByMultipleEmails(List.of(FOLLOWING_EMAIL)))
        .thenReturn(List.of(user));

    var followersFollowingDetails = usersService.getAllFollowersAndFollowing(FOLLOWER_EMAIL);
    assertThat(followersFollowingDetails)
        .extracting(FollowersFollowingDetails::allFollowers,
            FollowersFollowingDetails::allFollowing, FollowersFollowingDetails::totalFollowers,
            FollowersFollowingDetails::totalFollowing)
        .containsExactly(List.of(user), List.of(user), 1, 1);
  }
}
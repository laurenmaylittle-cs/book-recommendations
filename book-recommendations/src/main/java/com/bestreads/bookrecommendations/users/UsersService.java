package com.bestreads.bookrecommendations.users;

import com.bestreads.bookrecommendations.auth0.Auth0Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService {

  private final FollowersFollowingRepository followersFollowingRepository;
  private final Auth0Service auth0Service;

  @Autowired
  UsersService(FollowersFollowingRepository followersFollowingRepository,
      Auth0Service auth0Service) {
    this.followersFollowingRepository = followersFollowingRepository;
    this.auth0Service = auth0Service;
  }

  @Transactional
  void unfollowUser(String currentUser, String userToUnfollow) {
    var allPeopleThatUserIsFollowing = followersFollowingRepository.findAllByFollowerEmail(
        currentUser);

    allPeopleThatUserIsFollowing.forEach(following -> {
      if (following.getFollowingEmail().equals(userToUnfollow)) {
        followersFollowingRepository.delete(following);
      }
    });
  }

  @Transactional
  void followUser(String currentUserEmail, String emailToFollow) {
    var followersFollowing = new FollowersFollowing();

    followersFollowing.setFollowerEmail(currentUserEmail);
    followersFollowing.setFollowingEmail(emailToFollow);

    followersFollowingRepository.save(followersFollowing);
  }

  FollowersFollowingDetails getAllFollowersAndFollowing(String currentUserEmail) {
    var allFollowers = followersFollowingRepository.findAllByFollowingEmail(currentUserEmail);
    var allPeopleThatUserIsFollowing = followersFollowingRepository.findAllByFollowerEmail(
        currentUserEmail);

    var followersEmails = allFollowers.stream()
        .map(FollowersFollowing::getFollowerEmail)
        .toList();

    var followingEmails = allPeopleThatUserIsFollowing.stream()
        .map(FollowersFollowing::getFollowingEmail)
        .toList();

    var followers = auth0Service.searchByMultipleEmails(followersEmails);
    var following = auth0Service.searchByMultipleEmails(followingEmails);

    return new FollowersFollowingDetails(allFollowers.size(), followers,
        allPeopleThatUserIsFollowing.size(), following);
  }
}

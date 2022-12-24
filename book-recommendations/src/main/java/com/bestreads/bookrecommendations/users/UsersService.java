package com.bestreads.bookrecommendations.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

  private final FollowersFollowingRepository followersFollowingRepository;

  @Autowired
  public UsersService(FollowersFollowingRepository followersFollowingRepository) {
    this.followersFollowingRepository = followersFollowingRepository;
  }

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
    var numberOfFollowers = allFollowers.size();
    var numberOfFollowing = allPeopleThatUserIsFollowing.size();

    return new FollowersFollowingDetails(numberOfFollowers, allFollowers, numberOfFollowing,
        allPeopleThatUserIsFollowing);
  }
}

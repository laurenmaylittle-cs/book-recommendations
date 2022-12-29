package com.bestreads.bookrecommendations.users;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

  private final FollowersFollowingRepository followersFollowingRepository;

  @Autowired
  public UsersService(FollowersFollowingRepository followersFollowingRepository) {
    this.followersFollowingRepository = followersFollowingRepository;
  }

  public List<FollowersFollowing> getAllFollowers(String currentUserEmail) {
    return followersFollowingRepository.findAllByFollowingEmail(currentUserEmail);
  }

  public void unfollowUser(String currentUser, String userToUnfollow) {
    var allPeopleThatUserIsFollowing = followersFollowingRepository.findAllByFollowerEmail(
        currentUser);

    allPeopleThatUserIsFollowing.forEach(following -> {
      if (following.getFollowingEmail().equals(userToUnfollow)) {
        followersFollowingRepository.delete(following);
      }
    });
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

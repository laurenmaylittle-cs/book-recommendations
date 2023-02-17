package com.bestreads.bookrecommendations.users;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowersFollowingService {

  private final FollowersFollowingRepository followersFollowingRepository;

  @Autowired
  public FollowersFollowingService(FollowersFollowingRepository followersFollowingRepository) {
    this.followersFollowingRepository = followersFollowingRepository;
  }

  public List<FollowersFollowing> getFollowers(String email) {
    return followersFollowingRepository.findAllByFollowingEmail(email);
  }
}

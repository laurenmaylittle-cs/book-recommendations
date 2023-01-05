package com.bestreads.bookrecommendations.users;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowersFollowingRepository extends CrudRepository<FollowersFollowing, Integer> {

  // This returns all people that the user follows
  List<FollowersFollowing> findAllByFollowerEmail(String follower);

  // This returns all people that are followers of the user
  List<FollowersFollowing> findAllByFollowingEmail(String followingUser);
}

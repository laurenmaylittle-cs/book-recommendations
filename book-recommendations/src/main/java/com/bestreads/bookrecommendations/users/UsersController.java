package com.bestreads.bookrecommendations.users;

import com.bestreads.bookrecommendations.auth0.Auth0Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/users")
public class UsersController {

  private final Auth0Service auth0Service;
  private final UsersService usersService;

  @Autowired
  public UsersController(Auth0Service auth0Service, UsersService usersService) {
    this.auth0Service = auth0Service;
    this.usersService = usersService;
  }

  @GetMapping
  public List<User> searchUsers(@Param("email") String email) {
    return auth0Service.searchUsersByEmail(email);
  }

  @GetMapping("followers-following-details")
  public FollowersFollowingDetails getFollowersAndFollowingDetails(
      @Param("currentUser") String currentUser) {
    return usersService.getAllFollowersAndFollowing(currentUser);
  }

  @PostMapping("follow")
  public void followUser(@Param("currentUser") String currentUser,
      @Param("userToFollow") String userToFollow) {
    usersService.followUser(currentUser, userToFollow);
  }

  @PostMapping("unfollow")
  public void unfollowUser(@Param("currentUser") String currentUser,
      @Param("userToUnfollow") String userToUnfollow) {
    usersService.unfollowUser(currentUser, userToUnfollow);
  }
}

package com.bestreads.bookrecommendations.users;

import java.util.List;

public record User(String email,
                   boolean emailVerfied,
                   String name,
                   String picture,
                   List<FollowersFollowing> followers) {

  public User(String email, boolean emailVerfied, String name, String picture,
      List<FollowersFollowing> followers) {
    this.email = email;
    this.emailVerfied = emailVerfied;
    this.name = name;
    this.picture = picture;
    this.followers = followers;
  }
}

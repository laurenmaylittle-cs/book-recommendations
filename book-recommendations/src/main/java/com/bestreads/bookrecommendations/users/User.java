package com.bestreads.bookrecommendations.users;

import java.util.List;

public record User(String email,
                   boolean emailVerified,
                   String name,
                   String picture,
                   List<FollowersFollowing> followers) {

  public User(String email, boolean emailVerified, String name, String picture,
      List<FollowersFollowing> followers) {
    this.email = email;
    this.emailVerified = emailVerified;
    this.name = name;
    this.picture = picture;
    this.followers = followers;
  }
}

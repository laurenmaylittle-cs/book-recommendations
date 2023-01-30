package com.bestreads.bookrecommendations.users;

public record User(String email,
                   boolean emailVerified,
                   String name,
                   String picture) {

  public User(String email, boolean emailVerified, String name, String picture) {
    this.email = email;
    this.emailVerified = emailVerified;
    this.name = name;
    this.picture = picture;
  }
}
package com.bestreads.bookrecommendations.users;

public record User(String email,
                   boolean emailVerfied,
                   String name,
                   String picture) {

  public User(String email, boolean emailVerfied, String name, String picture) {
    this.email = email;
    this.emailVerfied = emailVerfied;
    this.name = name;
    this.picture = picture;
  }
}

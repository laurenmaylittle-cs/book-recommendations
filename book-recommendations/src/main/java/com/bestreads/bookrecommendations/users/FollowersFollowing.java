package com.bestreads.bookrecommendations.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "followers_following", schema = "best_reads")
class FollowersFollowing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String followingEmail;
  private String followerEmail;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFollowingEmail() {
    return followingEmail;
  }

  public void setFollowingEmail(String followingEmail) {
    this.followingEmail = followingEmail;
  }

  public String getFollowerEmail() {
    return followerEmail;
  }

  public void setFollowerEmail(String followerEmail) {
    this.followerEmail = followerEmail;
  }
}

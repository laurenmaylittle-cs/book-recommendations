package com.bestreads.bookrecommendations.users;

import java.util.List;

record FollowersFollowingDetails(Integer totalFollowers,
                                 List<User> allFollowers,
                                 Integer totalFollowing,
                                 List<User> allFollowing) {

}

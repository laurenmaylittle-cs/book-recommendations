package com.bestreads.bookrecommendations.users;

import java.util.List;

record FollowersFollowingDetails(Integer totalFollowers,
                                 List<FollowersFollowing> allFollowers,
                                 Integer totalFollowing,
                                 List<FollowersFollowing> allFollowing) {

}

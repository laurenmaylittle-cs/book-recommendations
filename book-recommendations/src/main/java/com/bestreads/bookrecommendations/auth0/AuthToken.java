package com.bestreads.bookrecommendations.auth0;

record AuthToken(String access_token,
                 String scope,
                 Integer expires_in,
                 String token_type) {

}

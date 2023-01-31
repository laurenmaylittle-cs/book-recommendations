package com.bestreads.bookrecommendations.auth0;

public record Identity(String provider,
                       String access_token,
                       Integer expires_in,
                       String user_id,
                       String connection,
                       Boolean is_social) {

}

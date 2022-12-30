package com.bestreads.bookrecommendations.helper;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class AuthHelper {

    public static String getUserId(JwtAuthenticationToken authenticationToken) {
        var userId = (String) authenticationToken.getToken().getClaims().get("sub");
        //Todo: do some validation
        return userId;
    }
}

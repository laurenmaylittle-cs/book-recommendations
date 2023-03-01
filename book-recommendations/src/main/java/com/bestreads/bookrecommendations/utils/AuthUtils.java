package com.bestreads.bookrecommendations.utils;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthUtils {

  static final Logger logger = Logger.getLogger(AuthUtils.class.getName());

  private AuthUtils() {
    throw new UtilsClassInitialisationException(this.getClass());
  }

  /**
   * SUB claim is the subject (user) of the token and is a standard claim defined in the JWT spec It
   * seems to be a good practice to check the existence of the "sub" claim before accessing it hence
   * the guards
   */
  public static Optional<String> getUserId(JwtAuthenticationToken authenticationToken) {
    if (authenticationToken.getTokenAttributes().containsKey("sub")
        && authenticationToken.getTokenAttributes().get("sub") != null) {
      return Optional.of(authenticationToken.getTokenAttributes().get("sub").toString());
    } else {
      logger.log(Level.SEVERE, "User id not found in token");
      return Optional.empty();
    }
  }

  public static String getUserIdOrBadRequest(JwtAuthenticationToken authenticationToken) {
    return AuthUtils.getUserId(authenticationToken).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
    });
  }
}

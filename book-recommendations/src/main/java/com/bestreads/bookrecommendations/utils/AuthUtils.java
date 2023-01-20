package com.bestreads.bookrecommendations.utils;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

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
}

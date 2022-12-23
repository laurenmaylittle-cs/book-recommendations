package com.bestreads.bookrecommendations.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * In the auth world an audience is the intended recipient of a token. For example, if you have a
 * token that is intended for the BestReads API, then the audience is the BestReads API. This class
 * is used to validate that the audience of a token is the BestReads API.
 */
class AudienceValidator implements OAuth2TokenValidator<Jwt> {

  private final String audience;

  AudienceValidator(String audience) {
    this.audience = audience;
  }

  @Override
  public OAuth2TokenValidatorResult validate(Jwt token) {

    if (token.getAudience().contains(audience)) {
      return OAuth2TokenValidatorResult.success();
    }

    OAuth2Error error = new OAuth2Error("invalid token", "Invalid required audience", null);
    return OAuth2TokenValidatorResult.failure(error);
  }
}

package com.bestreads.bookrecommendations.auth0;

import com.bestreads.bookrecommendations.users.FollowersFollowingService;
import com.bestreads.bookrecommendations.users.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Auth0Service {

  private String apiKey;
  private final String auth0ApiUri;
  private final String authClientId;
  private final String authClientSecret;
  private final UsersService usersService;

  private final FollowersFollowingService followersFollowingService;

  @Autowired
  public Auth0Service(@Value("${auth0.api-uri}") String auth0ApiUri,
      FollowersFollowingService followersFollowingService, @Value("${auth0.api-uri}") String auth0ApiUri,
      @Value("${auth0.client-id}") String authClientId,
      @Value("${auth0.client-secret}") String authClientSecret, UsersService usersService) {
    this.auth0ApiUri = auth0ApiUri;
    this.authClientId = authClientId;
    this.authClientSecret = authClientSecret;
    this.usersService = usersService;
    this.followersFollowingService = followersFollowingService;
  }

  public List<User> searchUsersByName(String name) {
    String uri = "%s/users?q=name:*%s*".formatted(auth0ApiUri, name);

    apiKey = getAuthToken();

    var httpRequest = getGetHttpRequest(uri);

    try {
      var httpResponse = HttpClient.newHttpClient()
          .send(httpRequest, HttpResponse.BodyHandlers.ofString());
      return extractFromHttpResponse(httpResponse, true);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public List<User> searchByMultipleEmails(List<String> emails) {
    if (emails.isEmpty()) {
      return Collections.emptyList();
    }
    StringBuilder uri = new StringBuilder(
        "%s/users?q=email:%s".formatted(auth0ApiUri, emails.get(0)));

    if (emails.size() > 1) {
      for (String email : emails) {
        uri.append("%20OR%20email:").append(email);
      }
    }

    uri = new StringBuilder(
        "%s&fields=email,name,email_verified,picture&search_engine=v2&included_total=true&include_fields=true".formatted(
            uri.toString()));

    apiKey = getAuthToken();

    var httpRequest = getGetHttpRequest(uri.toString());

    try {
      var httpResponse = HttpClient.newHttpClient()
          .send(httpRequest, HttpResponse.BodyHandlers.ofString());
      return extractFromHttpResponse(httpResponse, false);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private List<User> extractFromHttpResponse(HttpResponse<String> httpResponse,
      boolean includeFollowers) {
    if (!checkHttpStatusResponse200Ok(httpResponse)) {
      return Collections.emptyList(); //TODO BES-55 retry calling the API before returning empty list
    }

    var objectMapper = getObjectMapper();
    List<Auth0Users> users;

    try {
      users = objectMapper.readValue(httpResponse.body(), new TypeReference<>() {
      });

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return users.stream()
        .map(user -> new User(
            user.email(),
            user.email_verified(),
            user.name(),
            user.picture(),
            includeFollowers ? followersFollowingService.getFollowers(user.email())
                : null
        )).toList();
  }

  private ObjectMapper getObjectMapper() {
    var objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper;
  }

  private boolean checkHttpStatusResponse200Ok(HttpResponse<String> httpResponse) {
    return httpResponse.statusCode() == 200;
  }

  private HttpRequest getGetHttpRequest(String uri) {
    try {
      return HttpRequest.newBuilder()
          .uri(new URI(uri))
          .GET()
          .header("authorization", "Bearer %s".formatted(apiKey))
          .build();
    } catch (URISyntaxException e) {
      throw new IllegalStateException("Error creating URI: %s".formatted(uri));
    }
  }

  //TODO add secret and id to config
  private String getAuthToken() {
    try {
      var httpResponse = Unirest.post("https://bestreads.eu.auth0.com/oauth/token")
          .header("content-type", "application/json")
          .body("""
                  {
                    "client_id":"%s",
                    "client_secret":"%s",
                    "audience":"https://bestreads.eu.auth0.com/api/v2/",
                    "grant_type":"client_credentials"
                  }
              """.formatted(authClientId, authClientSecret))
          .asString()
          .getBody();
      var objectMapper = getObjectMapper();

      return objectMapper.readValue(httpResponse, AuthToken.class).access_token();

    } catch (UnirestException | JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}

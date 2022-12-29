package com.bestreads.bookrecommendations.auth0;

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

  public Auth0Service(@Value("${auth0.api-uri}") String auth0ApiUri) {
    this.auth0ApiUri = auth0ApiUri;
  }

  public List<User> searchUsersByEmail(String email) {
    String uri = "%s/users?q=email:*%s*".formatted(auth0ApiUri, email);

    apiKey = getAuthToken();

    var httpRequest = getGetHttpRequest(uri);

    try {
      var httpResponse = HttpClient.newHttpClient()
          .send(httpRequest, HttpResponse.BodyHandlers.ofString());
      return extractFromHttpResponse(httpResponse);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private List<User> extractFromHttpResponse(HttpResponse<String> httpResponse) {
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
            user.picture()
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
                    "client_id":"P46MtoBbpfbtbm94Z8Yt8NlsMKoM3mnf",
                    "client_secret":"QaFjvdKU8iTyIlVyuV72Oektfq_aQdVlN5ILWR_S3T6nguTtj1DdYaA6rXEiYEOU",
                    "audience":"https://bestreads.eu.auth0.com/api/v2/",
                    "grant_type":"client_credentials"
                  }
              """)
          .asString()
          .getBody();
      var objectMapper = getObjectMapper();

      return objectMapper.readValue(httpResponse, AuthToken.class).access_token();

    } catch (UnirestException | JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}

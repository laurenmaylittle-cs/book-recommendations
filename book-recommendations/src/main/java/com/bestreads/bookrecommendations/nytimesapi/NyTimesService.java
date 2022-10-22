package com.bestreads.bookrecommendations.nytimesapi;

import com.sun.istack.logging.Logger;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NyTimesService {

  private static final Logger logger = Logger.getLogger(NyTimesService.class);

  @Value("${nytimes.api.uri}")
  private String nyTimesApiUri;

  @Value("${nytimes.api.key}")
  private String apiKey;

  //Todo: parameterize this for list types
  //https://api.nytimes.com/svc/books/v3/lists/overview.json
  public HttpResponse<String> getCurrentBestSellers() {
    var uri = "%s/lists/overview.json?api-key=%s".formatted(
        nyTimesApiUri,
        apiKey
    );
    return sendHttpRequest(getGetHttpRequest(uri));
  }

  private HttpRequest getGetHttpRequest(String uri) {
    try {
      return HttpRequest.newBuilder()
          .uri(new URI(uri))
          .GET()
          .build();
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, "Error while creating HTTP request", e);
      throw new IllegalStateException("Error creating URI: %s".formatted(uri));
    }
  }

  private HttpResponse<String> sendHttpRequest(HttpRequest httpRequest) {
    try {
      return HttpClient.newHttpClient()
          .send(httpRequest, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      logger.log(Level.SEVERE, "Error while sending HTTP request", e);
      throw new IllegalStateException(
          "Error while sending HTTP request %s".formatted(httpRequest.uri()));
    }
  }
}

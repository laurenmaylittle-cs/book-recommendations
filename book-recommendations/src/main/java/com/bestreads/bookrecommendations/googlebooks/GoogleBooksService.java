package com.bestreads.bookrecommendations.googlebooks;

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

/**
 * Service to connect with Google Books API For further documentation see <a
 * href="https://developers.google.com/books/docs/overview">...</a> Useful search params: <a
 * href="https://developers.google.com/books/docs/v1/using#st_params">...</a>
 */
@Service
public class GoogleBooksService {

  private static final Logger LOGGER = Logger.getLogger(GoogleBooksService.class);

  @Value("${googlebooks.api.uri}")
  private String googleBooksApiUri;

  @Value("${googlebooks.api.key}")
  private String apiKey;

  public HttpResponse<String> searchVolumeByTitle(String searchTerm, int startIndex,
      int maxResults) {
    var uri = "%s/volumes?q=%s&startIndex=%s&maxResults=%s&key=%s".formatted(
        googleBooksApiUri,
        searchTerm,
        startIndex,
        maxResults,
        apiKey
    );
    return sendHttpRequest(getGetHttpRequest(uri));
  }

  public HttpResponse<String> searchVolumeByAuthor(String searchTerm, int startIndex,
      int maxResults) {
    var uri = "%s/volumes?q=inauthor:%s&startIndex=%s&maxResults=%s&key=%s".formatted(
        googleBooksApiUri,
        searchTerm,
        startIndex,
        maxResults,
        apiKey
    );
    return sendHttpRequest(getGetHttpRequest(uri));
  }

  public HttpResponse<String> searchVolumeByIsbn(String isbn, int startIndex,
      int maxResults) {
    var uri = "%s/volumes?q=isbn:%s&startIndex=%s&maxResults=%s&key=%s".formatted(
        googleBooksApiUri,
        isbn,
        startIndex,
        maxResults,
        apiKey
    );
    return sendHttpRequest(getGetHttpRequest(uri));
  }

  public HttpResponse<String> searchVolumeByTitleAndAuthors(String title, String authors) {
    var uri = "%s/volumes?q=%s+inauthor:%s&maxResults=%s&key=%s".formatted(
        googleBooksApiUri,
        title,
        authors,
        1,
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
      throw new IllegalStateException("Error creating URI: %s".formatted(uri));
    }
  }

  private HttpResponse<String> sendHttpRequest(HttpRequest httpRequest) {
    HttpResponse<String> response = null;
    try {
      response = HttpClient.newHttpClient()
          .send(httpRequest, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      LOGGER.log(Level.ALL,
          "Cannot make request to Google API with HttpRequest: %s".formatted(httpRequest));
    }
    return response;
  }
}

package com.bestreads.bookrecommendations.googlebooks;

import com.sun.istack.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;

/**
 * Service to connect with Google Books API
 * For further documentation see <a href="https://developers.google.com/books/docs/overview">...</a>
 * Useful search params: <a href="https://developers.google.com/books/docs/v1/using#st_params">...</a>
 */
@Service
public class GoogleBooksService {

    private static final Logger LOGGER = Logger.getLogger(GoogleBooksService.class);

    @Value("${googlebooks.api.uri}")
    private String googleBooksApiUri;

    @Value("${google_books.api.key}")
    private String apiKey;

    public HttpResponse<String> searchVolumeByTitle(String searchTerm, int startIndex, int maxResults) {
        var uri = "%s/volumes?q=%s&startIndex=%s&maxResults=%s&key=%s".formatted(
                googleBooksApiUri,
                searchTerm,
                startIndex,
                maxResults,
                apiKey
        );
        return sendHttpRequest(getGetHttpRequest(uri));
    }

    //TODO BES-54
    public HttpResponse<String> searchVolumeByAuthor(String searchTerm, int startIndex, int maxResults) {
        var uri = "%s/volumes?q=%s&startIndex=%s&maxResults=%s&key=%s".formatted(
                googleBooksApiUri,
                searchTerm,
                startIndex,
                maxResults,
                apiKey
        );
        return sendHttpRequest(getGetHttpRequest(uri));
    }

    public HttpResponse<String> getVolumeById(String id, int maxResults) {
        var uri = "%s/volumes/%s?maxResults=%d&key=%s".formatted(
                googleBooksApiUri,
                id,
                maxResults,
                apiKey
        );
        return sendHttpRequest(getGetHttpRequest(uri));
    }

    public HttpResponse<String> getVolumeByIsbn(String isbn, int maxResults) {
        var uri = "%s/volumes/?q=ISBN:%s&maxResults=%s&key=%s".formatted(
                googleBooksApiUri,
                isbn,
                maxResults,
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
            LOGGER.log(Level.ALL, "Cannot make request to Google API with HttpRequest: %s".formatted(httpRequest));
        }
        return response;
    }
}

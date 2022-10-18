package com.bestreads.bookrecommendations.book;

import com.bestreads.bookrecommendations.googlebooks.GoogleBooksService;
import com.bestreads.bookrecommendations.googlebooks.Item;
import com.bestreads.bookrecommendations.googlebooks.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookSearchService {

    private final GoogleBooksService googleBooksService;

    @Autowired
    public BookSearchService(GoogleBooksService googleBooksService) {
        this.googleBooksService = googleBooksService;
    }

    public List<Item> searchByTitle(String searchTerm, int maxResults) {
        return searchByTitle(searchTerm, 0, maxResults);
    }

    public List<Item> searchByTitle(String searchTerm, int startIndex, int maxResults) {
        HttpResponse<String> httpResponse = googleBooksService.searchVolumeByTitle(searchTerm, startIndex, maxResults);

        return getItemsFromHttpResponse(httpResponse);
    }

    public List<Item> searchByAuthor(String searchTerm, int maxResults) {
        return searchByAuthor(searchTerm, 0, maxResults);
    }

    public List<Item> searchByAuthor(String searchTerm, int startIndex, int maxResults) {
        HttpResponse<String> httpResponse = googleBooksService.searchVolumeByAuthor(searchTerm, startIndex, maxResults);

        return getItemsFromHttpResponse(httpResponse);
    }

    private List<Item> getItemsFromHttpResponse(HttpResponse<String> httpResponse) {
        checkHttpStatusResponse(httpResponse);
        var objectMapper = getObjectMapper();

        List<Item> volumes = new ArrayList<>();
        try {
            volumes = objectMapper.readValue(httpResponse.body(), Root.class).items();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return volumes;
    }

    private void checkHttpStatusResponse(HttpResponse httpResponse) {
        if (httpResponse.statusCode() != 200) {
            throw new IllegalStateException("Books API http response status is: %s".formatted(httpResponse.statusCode()));
        }
    }

    private ObjectMapper getObjectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}

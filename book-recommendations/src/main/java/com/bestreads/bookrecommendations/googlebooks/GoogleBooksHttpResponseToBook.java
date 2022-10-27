package com.bestreads.bookrecommendations.googlebooks;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.HttpResponseToBook;
import com.bestreads.bookrecommendations.book.ImageLinks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleBooksHttpResponseToBook implements HttpResponseToBook {

    @Override
    public List<Book> extractFromHttpResponse(HttpResponse<String> httpResponse) {
        if(!checkHttpStatusResponse200Ok(httpResponse)) {
            return Collections.emptyList(); //TODO BES-55 retry calling the API before returning empty list
        }
        var objectMapper = getObjectMapper();

        List<Item> items = new ArrayList<>();
        try {
            items = objectMapper.readValue(httpResponse.body(), Root.class).items();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return items.stream()
                .map(item -> new Book(
                        item.volumeInfo().title(),
                        item.volumeInfo().authors(),
                        item.volumeInfo().publisher(),
                        item.volumeInfo().publishedDate(),
                        item.volumeInfo().description(),
                        item.volumeInfo().pageCount(),
                        item.volumeInfo().categories(),
                        new ImageLinks(
                                item.volumeInfo().imageLinks().smallThumbnail(),
                                item.volumeInfo().imageLinks().thumbnail()
                        ),
                        item.volumeInfo().language(),
                        item.volumeInfo().averageRating(),
                        item.volumeInfo().ratingsCount(),
                        item.selfLink()
                ))
                .toList();
    }

    @Override
    public Item extractBookFromHttpResponse(HttpResponse<String> httpResponse) {
        if(!checkHttpStatusResponse200Ok(httpResponse)) {
            return null; //TODO BES-55 retry calling the API before returning empty list
        }
        var objectMapper = getObjectMapper();

        try {
            return objectMapper.readValue(httpResponse.body(), Item.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkHttpStatusResponse200Ok(HttpResponse<String> httpResponse) {
        return httpResponse.statusCode() == 200;
    }

    private ObjectMapper getObjectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}

package com.bestreads.bookrecommendations.nytimesapi;

import com.bestreads.bookrecommendations.book.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NyTimesHttpResponseToBook {

  public List<Category> extractFromHttpResponse(HttpResponse<String> httpResponse) {
    if (!checkHttpStatusResponse200Ok(httpResponse)) {
      //retry
      return Collections.emptyList();
    }

    var objectMapper = getObjectMapper();

    try {
      return objectMapper.readValue(httpResponse.body(), Root.class).results().categories();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

  }

  private Boolean checkHttpStatusResponse200Ok(HttpResponse<String> httpResponse) {
    return httpResponse.statusCode() == 200;
  }

  /**
   * This method is used to configure the object mapper to deserialize the json response from the NY
   * Times API, so it gets mapped to the global book object we have.
   */
  private ObjectMapper getObjectMapper() {
    var objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    SimpleModule bookDeserializer = new SimpleModule("CustomBookDeserializer",
        new Version(1, 0, 0, null,
            null, null));

    bookDeserializer.addDeserializer(Book.class, new CustomBookDeserializer(Book.class));
    objectMapper.registerModule(bookDeserializer);

    return objectMapper;

  }
}

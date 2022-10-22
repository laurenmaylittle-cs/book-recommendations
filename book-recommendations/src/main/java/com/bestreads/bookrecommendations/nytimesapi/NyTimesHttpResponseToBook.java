package com.bestreads.bookrecommendations.nytimesapi;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.HttpResponseToBook;
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
public class NyTimesHttpResponseToBook implements HttpResponseToBook {

  @Override
  public List<Book> extractFromHttpResponse(HttpResponse<String> httpResponse) {
    if (!checkHttpStatusResponse200Ok(httpResponse)) {
      //retry
      return Collections.emptyList();
    }

    var objectMapper = getObjectMapper();

    try {
      var lists = objectMapper.readValue(httpResponse.body(), Root.class).results().lists();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return null;
  }


  private Boolean checkHttpStatusResponse200Ok(HttpResponse<String> httpResponse) {
    return httpResponse.statusCode() == 200;
  }

  private ObjectMapper getObjectMapper() {
    var objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    SimpleModule module = new SimpleModule("CustomBookDeserializer", new Version(1, 0, 0, null,
        null, null));
    module.addDeserializer(Book.class, new CustomBookDeserializer(Book.class));
    objectMapper.registerModule(module);

    return objectMapper;
  }
}

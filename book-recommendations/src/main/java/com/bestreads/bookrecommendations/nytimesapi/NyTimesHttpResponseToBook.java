package com.bestreads.bookrecommendations.nytimesapi;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.HttpResponseToBook;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.net.http.HttpResponse;
import java.util.ArrayList;
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
    var bookLists = new ArrayList<com.bestreads.bookrecommendations.nytimesapi.List>();

    try {
      bookLists = objectMapper.readValue(httpResponse.body(), Root.class).results().lists();
      return getBooksFromLists(bookLists, List.of(BestSellerCategories.HARDCOVER_FICTION,
          BestSellerCategories.HARDCOVER_NONFICTION));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

  }

  //Todo update the custom deserilizer to map the list to something else
  private List<Book> getBooksFromLists(
      List<com.bestreads.bookrecommendations.nytimesapi.List> bookList,
      List<BestSellerCategories> bestSellerCategories) {

    return bookList.stream()
        .filter(list -> bestSellerCategories.stream()
            .anyMatch(category -> list.list_name().equals(category.getCategory())))
        .flatMap(list -> list.books().stream())
        .toList();
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

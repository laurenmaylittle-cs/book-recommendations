package com.bestreads.bookrecommendations.googlebooks;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.HttpResponseToBook;
import com.bestreads.bookrecommendations.book.ImageLinks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleBooksHttpResponseToBook implements HttpResponseToBook {

  private final String bookThumbnailPlaceholderLink;

  public GoogleBooksHttpResponseToBook(
      @Value("${book.placeholder.url}") String bookThumbnailPlaceholderLink) {
    this.bookThumbnailPlaceholderLink = bookThumbnailPlaceholderLink;
  }

  @Override
  public List<Book> extractFromHttpResponse(HttpResponse<String> httpResponse) {
    if (!checkHttpStatusResponse200Ok(httpResponse)) {
      return Collections.emptyList(); //TODO BES-55 retry calling the API before returning empty list
    }
    var objectMapper = getObjectMapper();

    List<Item> items;
    try {
      items = objectMapper.readValue(httpResponse.body(), Root.class).items();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return items.stream()
        .map(item -> new Book(
            item.id(),
            item.volumeInfo().title(),
            item.volumeInfo().authors(),
            item.volumeInfo().publisher(),
            item.volumeInfo().publishedDate(),
            item.volumeInfo().description(),
            item.volumeInfo().pageCount(),
            item.volumeInfo().categories(),
            getImageLinks(item),
            item.volumeInfo().language(),
            item.volumeInfo().averageRating(),
            item.volumeInfo().ratingsCount()
        ))
        .toList();
  }

  private boolean checkHttpStatusResponse200Ok(HttpResponse<String> httpResponse) {
    return httpResponse.statusCode() == 200;
  }

  private ObjectMapper getObjectMapper() {
    var objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper;
  }

  private ImageLinks getImageLinks(Item item) {

    var itemImageLinks = item.volumeInfo().imageLinks();

    if (itemImageLinks == null) {
      return new ImageLinks(bookThumbnailPlaceholderLink, bookThumbnailPlaceholderLink);
    }

    String smallThumbnail;
    String thumbnail;
    var thumbnailExist = false;

    if (StringUtils.isEmpty(itemImageLinks.thumbnail())) {
      thumbnail = bookThumbnailPlaceholderLink;
    } else {
      thumbnail = itemImageLinks.thumbnail();
      thumbnailExist = true;
    }

    if (!StringUtils.isEmpty(itemImageLinks.smallThumbnail())) {
      smallThumbnail = itemImageLinks.smallThumbnail();
    } else if (thumbnailExist) {
      smallThumbnail = thumbnail;
    } else {
      smallThumbnail = bookThumbnailPlaceholderLink;
    }

    return new ImageLinks(smallThumbnail, thumbnail);
  }
}

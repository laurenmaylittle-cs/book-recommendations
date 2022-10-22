package com.bestreads.bookrecommendations.nytimesapi;

import com.bestreads.bookrecommendations.book.Book;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class CustomBookDeserializer extends StdDeserializer<Book> {


  protected CustomBookDeserializer(Class<?> vc) {
    super(vc);
  }

  protected CustomBookDeserializer(JavaType valueType) {
    super(valueType);
  }

  protected CustomBookDeserializer(StdDeserializer<?> src) {
    super(src);
  }


  @Override
  public Book deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JacksonException {
    Book book = new Book();
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    try {
      JsonNode author = node.get("author");
      JsonNode book_image = node.get("book_image");
      JsonNode description = node.get("description");
      JsonNode publisher = node.get("publisher");
      JsonNode title = node.get("title");

      var authorsList = java.util.List.of(author.asText()); //change this
      book.setAuthors(java.util.List.of(author.asText()));
      book.setDescription(description.asText());
      book.setPublisher(publisher.asText());
      book.setTitle(title.asText());

    } catch (Exception e) {
      e.printStackTrace();
    }

    return book;
  }
}

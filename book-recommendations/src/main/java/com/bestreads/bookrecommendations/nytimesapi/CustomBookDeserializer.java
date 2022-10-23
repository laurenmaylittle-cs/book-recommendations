package com.bestreads.bookrecommendations.nytimesapi;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.ImageLinks;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.List;

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
  public Book deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    try {
      Book book = new Book(
          node.get("title").asText(),
          List.of(node.get("author").asText()),
          node.get("publisher").asText(),
          node.get("description").asText(),
          new ImageLinks(null, node.get("book_image").asText())
      );

      return book;

    } catch (Exception e) {
      throw new RuntimeException("Error parsing book", e);
    }

  }
}

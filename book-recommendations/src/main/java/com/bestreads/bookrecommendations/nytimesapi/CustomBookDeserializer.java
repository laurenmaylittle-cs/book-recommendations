package com.bestreads.bookrecommendations.nytimesapi;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.ImageLinks;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.List;

public class CustomBookDeserializer extends StdDeserializer<Book> {


  protected CustomBookDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Book deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    //TODO-BES-60: check for the node values before assessing them and provide sensible defaults
    return new Book(
        node.get("title").asText(),
        List.of(node.get("author").asText()),
        node.get("publisher").asText(),
        node.get("description").asText(),
        new ImageLinks(null, node.get("book_image").asText())
    );

  }
}

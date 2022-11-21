package com.bestreads.bookrecommendations.nytimesapi;

import com.bestreads.bookrecommendations.book.Book;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a list in the New York Times Best Sellers list.
 */
public record Category(@JsonProperty("list_id") int listId,
                       @JsonProperty("list_name") String listName, List<Book> books) {

}

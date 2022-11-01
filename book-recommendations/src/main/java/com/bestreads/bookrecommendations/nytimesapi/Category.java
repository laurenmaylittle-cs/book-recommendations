package com.bestreads.bookrecommendations.nytimesapi;

import com.bestreads.bookrecommendations.book.Book;
import java.util.List;

/**
 * Represents a list in the New York Times Best Sellers list.
 */
public record Category(int list_id, String list_name, List<Book> books) {

}

package com.bestreads.bookrecommendations.book;

import java.util.List;

public record Book(String id,
                   String title,
                   List<String> authors,
                   String publisher,
                   String publishedDate,
                   String description,
                   int pageCount,
                   List<String> categories,
                   ImageLinks imageLinks,
                   String language,
                   int averageRating,
                   int ratingsCount) {

  public Book(String title,
      List<String> authors,
      String publisher,
      String description,
      ImageLinks imageLinks) {

    this(null, title, authors, publisher, "Unknown", description, 0, List.of(), imageLinks,
        "Unknown",
        0,
        0);
  }
}

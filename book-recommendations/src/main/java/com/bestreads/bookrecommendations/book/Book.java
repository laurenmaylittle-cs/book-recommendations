package com.bestreads.bookrecommendations.book;

import java.util.ArrayList;
import java.util.List;

public record Book(String title,
                   List<String> authors,
                   String publisher,
                   String publishedDate,
                   String description,
                   int pageCount,
                   ArrayList<String> categories,
                   ImageLinks imageLinks,
                   String language,
                   int averageRating,
                   int ratingsCount) {
}

package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.book.Book;
import java.util.List;

record CollectionBookRootJson(List<CollectionBookJson> collections, Book book) {


}

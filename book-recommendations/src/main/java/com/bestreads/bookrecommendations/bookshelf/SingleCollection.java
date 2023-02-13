package com.bestreads.bookrecommendations.bookshelf;

import java.util.List;

record SingleCollection(String collectionName, List<BookDAO> booksInCollection) {

}

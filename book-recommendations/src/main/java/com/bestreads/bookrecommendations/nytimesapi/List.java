package com.bestreads.bookrecommendations.nytimesapi;

import com.bestreads.bookrecommendations.book.Book;
import java.util.ArrayList;

//Books -- customer mapper
// cannot map the Book by default
record List(int list_id, String list_name, ArrayList<Book> books) {

}

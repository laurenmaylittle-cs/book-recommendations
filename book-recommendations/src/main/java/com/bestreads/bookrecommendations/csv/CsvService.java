package com.bestreads.bookrecommendations.csv;

import org.springframework.stereotype.Service;

@Service
class CsvService {

    void printData(String isbn, String title, String author, String genre, String publisher, String userID) {
        System.out.println(isbn + " " + title + " " + author + " " + genre + " " + publisher + " " + userID);
    }
}

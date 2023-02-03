package com.bestreads.bookrecommendations.csv;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class CsvService {

    void printData(String isbn, String title, String author, String genre, String userID) {
        System.out.println(isbn + " " + title + " " + author + " " + genre + " " + userID);
    }
}

package com.bestreads.bookrecommendations.csv;

import org.springframework.stereotype.Service;
import java.io.*;

@Service
class CsvService {

    void addToCsv(String dataToAdd, String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(dataToAdd);
        bw.close();
    }
    
    boolean checkIfBookExists(String isbn) throws IOException {
        FileReader fr = new FileReader("books.csv");
        BufferedReader br = new BufferedReader(fr);
        boolean exists = false;
        String line = br.readLine();
        while (line != null) {
            if (line.contains(isbn)) {
                exists = true;
                break;
            } else {
                line = br.readLine();
            }
        }
        return exists;
    }
}

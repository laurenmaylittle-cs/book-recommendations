package com.bestreads.bookrecommendations.csv;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;

@Service
class CsvService {

    void addToCsv(String dataToAdd, String fileName) {
        try (
                FileWriter fw = new FileWriter(fileName, true);
                BufferedWriter bw = new BufferedWriter(fw)
        ) {
            bw.write(dataToAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean checkIfBookExists(String isbn) {
        boolean exists = false;
        try (
                FileReader fr = new FileReader("books.csv");
                BufferedReader br = new BufferedReader(fr)
                ) {
            String line = br.readLine();
            while (line != null) {
                if (line.contains(isbn)) {
                    exists = true;
                    break;
                } else {
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }
}

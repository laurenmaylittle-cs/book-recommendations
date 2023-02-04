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
}

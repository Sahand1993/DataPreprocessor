package com.braincourt.datasetdividers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Divider {

    protected String validationFilePath;
    protected String trainingFilePath;
    protected static Random random = new Random();

    protected Logger LOG = LoggerFactory.getLogger(this.getClass());
    protected String fileToDividePath;
    protected int validationPercentage;

    public abstract void divide();

    int getNoOfLines(String dataFilePath, int percentage) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFilePath));
            Stream<String> lines = bufferedReader.lines();
            long numberOfLines = lines.count();
            bufferedReader.close();
            return (int) (numberOfLines * ((float) percentage / 100));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    List<String> getShuffledLines(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            List<String> shuffledList
                    = bufferedReader.lines()
            .collect(Collectors.toList());
            Collections.shuffle(shuffledList);
            bufferedReader.close();
            return shuffledList;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    protected void writeTo(String path, List<String> lines) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            lines.forEach(line -> {
                try {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

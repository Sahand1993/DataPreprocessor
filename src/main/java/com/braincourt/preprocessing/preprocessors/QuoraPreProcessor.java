package com.braincourt.preprocessing.preprocessors;

import com.braincourt.preprocessing.Tokenizer;
import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.dataobjects.QuoraQuestionPairDataObject;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class QuoraPreProcessor extends PreProcessor {

    public QuoraPreProcessor(Tokenizer tokenizer) {
        super(tokenizer);

    }

    @Override
    public Stream<DataObject> toDataObjects(Stream<Path> dataFilePaths) {
        return dataFilePaths.flatMap(this::toDataObjects);
    }

    public Stream<DataObject> toDataObjects(Path dataFilePath) {
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFilePath.toAbsolutePath().toString()));
            return bufferedReader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] fields;
                        try {
                            fields = new CSVParser().parseLine(line);
                            return new QuoraQuestionPairDataObject(
                                    Integer.parseInt(fields[0]),
                                    Integer.parseInt(fields[1]),
                                    Integer.parseInt(fields[2]),
                                    tokenizer.tokenize(fields[3]),
                                    tokenizer.tokenize(fields[4]),
                                    Integer.parseInt(fields[5]) == 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.exit(1);
                            return null;
                        }
            });

        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();

        }
    }
}

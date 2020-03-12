package com.braincourt.preprocessing.preprocessors;

import com.braincourt.preprocessing.Tokenizer;
import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.dataobjects.QuoraQuestionPairDataObject;
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
            CSVReader reader = new CSVReader(bufferedReader);
            return StreamSupport.stream(reader.spliterator(), true)
                    .skip(1)
                    .map(line -> new QuoraQuestionPairDataObject(
                                    Integer.parseInt(line[0]),
                                    Integer.parseInt(line[1]),
                                    Integer.parseInt(line[2]),
                                    tokenizer.tokenize(line[3]),
                                    tokenizer.tokenize(line[4]),
                                    Boolean.parseBoolean(line[5])));

        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();

        }
    }
}

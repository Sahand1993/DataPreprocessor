package com.braincourt.preprocessing.preprocessors;

import com.braincourt.preprocessing.Tokenizer;
import com.braincourt.preprocessing.dataobjects.DataObject;

import java.nio.file.Path;
import java.util.stream.Stream;

public abstract class PreProcessor {

    Tokenizer tokenizer;

    public PreProcessor(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public abstract Stream<DataObject> toDataObjects(Stream<Path> dataFilePaths);
}

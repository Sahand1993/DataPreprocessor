package com.braincourt.preprocessing.filevisitors;

import com.braincourt.preprocessing.Tokenizer;
import com.braincourt.preprocessing.dataobjects.DataObject;

import java.nio.file.Path;
import java.util.stream.Stream;

public abstract class FileVisitor {

    Tokenizer tokenizer;

    public FileVisitor(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public abstract Stream<DataObject> toDataObjects(Stream<Path> dataFilePaths);
}

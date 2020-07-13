package com.braincourt.preprocessing.preprocessors;

import com.braincourt.preprocessing.DataWriter;
import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.filevisitors.FileVisitor;
import com.braincourt.preprocessing.traversers.Traverser;

import java.nio.file.Path;
import java.util.stream.Stream;

abstract class FileSystemPreprocessor extends Preprocessor {

    Traverser fileTraverser;
    FileVisitor fileVisitor;

    public FileSystemPreprocessor(DataWriter dataWriter) {
        super(dataWriter);
    }

    @Override
    public void preprocess() {
            Stream<Path> paths = fileTraverser.getProcessablePaths();
            Stream<DataObject> dataObjects = fileVisitor.toDataObjects(paths);
            dataWriter.writeObjects(dataObjects, preprocessedDir);
    }
}

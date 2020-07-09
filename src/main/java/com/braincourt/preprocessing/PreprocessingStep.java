package com.braincourt.preprocessing;

import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.filevisitors.FileVisitor;
import com.braincourt.preprocessing.traversers.Traverser;

import java.nio.file.Path;
import java.util.stream.Stream;

abstract class PreprocessingStep {

    Traverser fileTraverser;
    FileVisitor fileVisitor;
    String preprocessedDir;
    DataWriter dataWriter;

    public PreprocessingStep(DataWriter dataWriter) {
        this.dataWriter = dataWriter;
    }

    public void preprocess() {
            Stream<Path> paths = fileTraverser.getProcessablePaths();
            Stream<DataObject> dataObjects = fileVisitor.toDataObjects(paths);
            dataWriter.writeObjects(dataObjects, preprocessedDir);
    }
}

package com.braincourt.preprocessing.traversers;

import java.nio.file.Path;
import java.util.stream.Stream;

public abstract class Traverser {

    Path datasetRoot;

    public Traverser(Path datasetRoot) {
        this.datasetRoot = datasetRoot;
    }

    public abstract Stream<Path> getProcessablePaths();
}

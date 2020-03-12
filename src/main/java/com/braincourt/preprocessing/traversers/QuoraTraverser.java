package com.braincourt.preprocessing.traversers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class QuoraTraverser extends Traverser {

    public QuoraTraverser(Path datasetRoot) {
        super(datasetRoot);
    }

    @Override
    public Stream<Path> getProcessablePaths() {
        return Stream.of(Paths.get(datasetRoot + "/train.csv"));

    }
}

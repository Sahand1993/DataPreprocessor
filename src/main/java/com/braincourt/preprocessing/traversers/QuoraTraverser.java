package com.braincourt.preprocessing.traversers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class QuoraTraverser extends Traverser {

    public QuoraTraverser(@Value("${quora.raw.home}") String datasetRoot) {
        super(datasetRoot);
    }

    @Override
    public Stream<Path> getProcessablePaths() {
        return Stream.of(Paths.get(datasetRoot, "/train.csv"));

    }
}

package com.braincourt.preprocessing.traversers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class SquadTraverser extends Traverser {
    public SquadTraverser(@Value("${squad.raw.home}") String datasetRoot) {
        super(datasetRoot);
    }

    @Override
    public Stream<Path> getProcessablePaths() {
        return Stream.of(Paths.get(datasetRoot, "/train-v2.0.json"));
    }
}

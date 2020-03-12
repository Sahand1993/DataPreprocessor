package com.braincourt.preprocessing.traversers;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class NaturalQuestionsTraverser extends Traverser {

    public NaturalQuestionsTraverser(Path datasetRoot) {
        super(datasetRoot);
    }

    @Override
    public Stream<Path> getProcessablePaths() {
        File nqJsonFile = new File(datasetRoot + "/v1.0/train/");
        if (nqJsonFile.isDirectory()) {
            return Arrays.stream(
                    Objects.requireNonNull(nqJsonFile.listFiles())
            )
                    .filter(File::isFile)
                    .filter(file -> file.getName().endsWith(".jsonl"))
                    .map(File::toPath);
        }
        return Stream.empty();
    }
}

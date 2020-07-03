package com.braincourt.preprocessing.traversers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class NaturalQuestionsTraverser extends Traverser {

    public NaturalQuestionsTraverser(@Value("${naturalQuestions.raw.home}") String datasetRoot) {
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

package com.braincourt.preprocessing.traversers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

@Component
public class ReutersTraverser extends Traverser {

    private static final Set<String> NON_DATA_DIRNAMES
            = new HashSet<>(Arrays.asList("codes", "dtds"));

    public ReutersTraverser(@Value("${reuters.raw.home}") String datasetRoot) {
        super(datasetRoot);
    }

    @Override
    public Stream<Path> getProcessablePaths() {
        String reutersDatasetPath = datasetRoot + "/rcv1";
        File reutersDatasetDir = new File(reutersDatasetPath);
        return Arrays.stream(Objects.requireNonNull(reutersDatasetDir.listFiles()))
                .filter(File::isDirectory)
                .filter(file -> !NON_DATA_DIRNAMES.contains(file.getName()))
                .flatMap(file -> Arrays.stream(Objects.requireNonNull(file.listFiles())))
                .filter(file -> file.getName().endsWith(".xml"))
                .map(File::toPath);
    }
}

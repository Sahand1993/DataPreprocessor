package com.braincourt.preprocessing;

import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.filevisitors.NaturalQuestionsFileVisitor;
import com.braincourt.preprocessing.filevisitors.FileVisitor;
import com.braincourt.preprocessing.filevisitors.QuoraFileVisitor;
import com.braincourt.preprocessing.filevisitors.ReutersFileVisitor;
import com.braincourt.preprocessing.traversers.NaturalQuestionsTraverser;
import com.braincourt.preprocessing.traversers.QuoraTraverser;
import com.braincourt.preprocessing.traversers.ReutersTraverser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class Processing {

    private final Tokenizer tokenizer;

    @Value("${reuters.raw.home}")
    String rawReutersDir;

    @Value("${quora.raw.home}")
    String rawQuoraDir;

    @Value("${naturalQuestions.raw.home}")
    String rawNqDir;

    @Value("${naturalQuestions.home}")
    String preprocessedNqDir;

    @Value("${reuters.home}")
    String preprocessedReutersDir;

    @Value("${quora.home}")
    String preprocessedQuoraDir;

    @Value("${filename.json}")
    String jsonFileName;

    DataWriter dataWriter;


    public Processing(DataWriter dataWriter,
                      Tokenizer tokenizer) {
        this.dataWriter = dataWriter;
        this.tokenizer = tokenizer;
    }

    public void preprocessDatasets() {
        preprocessReuters(tokenizer);

        preprocessNaturalQuestions(tokenizer);

        preprocessQuora(tokenizer);
    }

    private void preprocessReuters(Tokenizer tokenizer) {
        ReutersTraverser reutersTraverser = new ReutersTraverser(Paths.get(rawReutersDir));
        Stream<Path> reutersPaths = reutersTraverser.getProcessablePaths();
        FileVisitor reutersFileVisitor = new ReutersFileVisitor(tokenizer);
        Stream<DataObject> dataObjects = reutersFileVisitor.toDataObjects(reutersPaths);
        DataWriter dataWriter = new DataWriter(jsonFileName);
        dataWriter.writeObjects(dataObjects, preprocessedReutersDir);
    }

    private void preprocessNaturalQuestions(Tokenizer tokenizer) {
        NaturalQuestionsTraverser nqTraverser = new NaturalQuestionsTraverser(Paths.get(rawNqDir));
        Stream<Path> nqPaths = nqTraverser.getProcessablePaths();
        FileVisitor nqFileVisitor = new NaturalQuestionsFileVisitor(tokenizer);
        Stream<DataObject> dataObjects = nqFileVisitor.toDataObjects(nqPaths);
        DataWriter dataWriter = new DataWriter(jsonFileName);
        dataWriter.writeObjects(dataObjects, preprocessedNqDir);
    }

    private void preprocessQuora(Tokenizer tokenizer) {
        QuoraTraverser quoraTraverser = new QuoraTraverser(Paths.get(rawQuoraDir));
        Stream<Path> quoraPaths = quoraTraverser.getProcessablePaths();
        FileVisitor quoraFileVisitor = new QuoraFileVisitor(tokenizer);
        Stream<DataObject> dataObjects = quoraFileVisitor.toDataObjects(quoraPaths);
        DataWriter dataWriter = new DataWriter(jsonFileName);
        dataWriter.writeObjects(dataObjects, preprocessedQuoraDir);
    }
}

package com.braincourt.preprocessing;

import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.preprocessors.NaturalQuestionsPreProcessor;
import com.braincourt.preprocessing.preprocessors.QuoraPreProcessor;
import com.braincourt.preprocessing.traversers.NaturalQuestionsTraverser;
import com.braincourt.preprocessing.traversers.QuoraTraverser;
import com.braincourt.preprocessing.traversers.ReutersTraverser;
import com.braincourt.preprocessing.preprocessors.PreProcessor;
import com.braincourt.preprocessing.preprocessors.ReutersPreProcessor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    static final String datasetDir = System.getenv("THESIS_DATA_DIR");
    static DataWriter writer = new DataWriter(datasetDir + "../preprocessed_datasets" );

    public static void main(String[] args) {
        // TODO: Implement command line options for only processing certain datasets

        // data form
        // {article_id: 123, tokens: ["hi", "again"], topic_tags: ["x", "y"]}
        Tokenizer tokenizer = new Tokenizer(datasetDir + "/stopwords.json");

        //preprocessReuters(tokenizer);

        //preprocessNaturalQuestions(tokenizer);

        preprocessQuora(tokenizer);
    }

    private static void preprocessReuters(Tokenizer tokenizer) {
        // TODO: Clean up with paths
        String reutersDataDir = datasetDir + "/reuters";
        PreProcessor reutersPreProcessor = new ReutersPreProcessor(tokenizer);
        ReutersTraverser reutersTraverser = new ReutersTraverser(Paths.get(reutersDataDir));
        Stream<Path> reutersPaths = reutersTraverser.getProcessablePaths();
        Stream<DataObject> dataObjects = reutersPreProcessor.toDataObjects(reutersPaths);
        writer.writeObjects(dataObjects, "/rcv2/");
    }

    private static void preprocessNaturalQuestions(Tokenizer tokenizer) {
        String nqDataDir = datasetDir + "/nq/";
        NaturalQuestionsTraverser nqTraverser = new NaturalQuestionsTraverser(Paths.get(nqDataDir));
        Stream<Path> nqPaths = nqTraverser.getProcessablePaths();
        PreProcessor nqPreProcessor = new NaturalQuestionsPreProcessor(tokenizer);
        Stream<DataObject> dataObjects = nqPreProcessor.toDataObjects(nqPaths);
        writer.writeObjects(dataObjects, "/nq/"); // TODO: Make it so that we send in the whole path here, if possible
    }

    private static void preprocessQuora(Tokenizer tokenizer) {
        String quoraDataDir = datasetDir + "/quora/";
        QuoraTraverser quoraTraverser = new QuoraTraverser(Paths.get(quoraDataDir));
        Stream<Path> quoraPaths = quoraTraverser.getProcessablePaths();
        PreProcessor quoraPreProcessor = new QuoraPreProcessor(tokenizer);
        Stream<DataObject> dataObjects = quoraPreProcessor.toDataObjects(quoraPaths);
        writer.writeObjects(dataObjects, "/quora/"); // TODO
    }


}
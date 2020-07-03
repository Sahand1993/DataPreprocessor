package com.braincourt.preprocessing;

import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.dataobjects.ReutersDataObject;
import com.braincourt.preprocessing.filevisitors.NaturalQuestionsFileVisitor;
import com.braincourt.preprocessing.filevisitors.FileVisitor;
import com.braincourt.preprocessing.filevisitors.QuoraFileVisitor;
import com.braincourt.preprocessing.filevisitors.ReutersFileVisitor;
import com.braincourt.preprocessing.filevisitors.SquadFileVisitor;
import com.braincourt.preprocessing.filevisitors.WikiQAFileVisitor;
import com.braincourt.preprocessing.traversers.NaturalQuestionsTraverser;
import com.braincourt.preprocessing.traversers.QuoraTraverser;
import com.braincourt.preprocessing.traversers.ReutersTraverser;
import com.braincourt.preprocessing.traversers.SquadTraverser;
import com.braincourt.preprocessing.traversers.WikiQATraverser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class Processing {

    private String preprocessedNqDir;
    private String preprocessedReutersDir;
    private final String preprocessedSquadDir;
    private final String preprocessedWikiQADir;

    private String preprocessedQuoraDir;

    private final DataWriter dataWriter;

    private final SquadTraverser squadTraverser;
    private final WikiQATraverser wikiQATraverser;
    private final QuoraTraverser quoraTraverser;
    private final NaturalQuestionsTraverser nqTraverser;
    private final ReutersTraverser reutersTraverser;

    private final NaturalQuestionsFileVisitor naturalQuestionsFileVisitor;
    private final SquadFileVisitor squadFileVisitor;
    private final WikiQAFileVisitor wikiQAFileVisitor;
    private final QuoraFileVisitor quoraFileVisitor;
    private final ReutersFileVisitor reutersFileVisitor;


    public Processing(
            @Value("${wikiQA.raw.home}") String rawWikiQADir,
            @Value("${processed.data.dir}") String preprocessedDir,
            @Value("${nq.folder}") String naturalQuestionsDirName,
            @Value("${quora.folder}") String quoraDirName,
            @Value("${reuters.folder}") String reutersDirName,
            @Value("${squad.folder}") String squadDirName,
            @Value("${wikiQA.folder}") String wikiQADirName,
            DataWriter dataWriter,
            NaturalQuestionsFileVisitor naturalQuestionsFileVisitor,
            SquadTraverser squadTraverser,
            ReutersTraverser reutersTraverser,
            SquadFileVisitor squadFileVisitor,
            QuoraFileVisitor quoraFileVisitor,
            ReutersFileVisitor reutersFileVisitor,
            WikiQATraverser wikiQATraverser,
            QuoraTraverser quoraTraverser,
            NaturalQuestionsTraverser nqTraverser,
            WikiQAFileVisitor wikiQAFileVisitor) {
        this.dataWriter = dataWriter;
        this.preprocessedNqDir = preprocessedDir + naturalQuestionsDirName;
        this.preprocessedQuoraDir = preprocessedDir + quoraDirName;
        this.preprocessedReutersDir = preprocessedDir + reutersDirName;
        this.preprocessedSquadDir = preprocessedDir + squadDirName;
        this.preprocessedWikiQADir = preprocessedDir + wikiQADirName;

        this.squadTraverser = squadTraverser;
        this.wikiQATraverser = wikiQATraverser;
        this.quoraTraverser = quoraTraverser;
        this.nqTraverser = nqTraverser;
        this.reutersTraverser = reutersTraverser;

        this.naturalQuestionsFileVisitor = naturalQuestionsFileVisitor;
        this.squadFileVisitor = squadFileVisitor;
        this.wikiQAFileVisitor = wikiQAFileVisitor;
        this.quoraFileVisitor = quoraFileVisitor;
        this.reutersFileVisitor = reutersFileVisitor;
    }

    public void preprocessDatasets() {
        preprocessWikiQA();

        preprocessSquad();

        preprocessNaturalQuestions();

        preprocessReuters();

        preprocessQuora();
    }

    private void preprocessReuters() {
        Stream<Path> reutersPaths = reutersTraverser.getProcessablePaths();
        Stream<DataObject> dataObjects = reutersFileVisitor.toDataObjects(reutersPaths);
        dataWriter.writeObjects(
                dataObjects
                        .limit(170000),
                preprocessedReutersDir);
    }

    private void preprocessSquad() {
        Stream<Path> squadPaths = squadTraverser.getProcessablePaths();
        Stream<DataObject> dataObjects = squadFileVisitor.toDataObjects(squadPaths);
        dataWriter.writeObjects(dataObjects, preprocessedSquadDir);
    }

    private void preprocessNaturalQuestions() {
        Stream<Path> nqPaths = nqTraverser.getProcessablePaths();
        Stream<DataObject> dataObjects = naturalQuestionsFileVisitor.toDataObjects(nqPaths);
        dataWriter.writeObjects(dataObjects, preprocessedNqDir);
    }

    private void preprocessQuora() {
        Stream<Path> quoraPaths = quoraTraverser.getProcessablePaths();
        Stream<DataObject> dataObjects = quoraFileVisitor.toDataObjects(quoraPaths);
        dataWriter.writeObjects(dataObjects, preprocessedQuoraDir);
    }

    private void preprocessWikiQA() {
        Stream<Path> paths = wikiQATraverser.getProcessablePaths();
        Stream<DataObject> dataObjects = wikiQAFileVisitor.toDataObjects(paths);
        dataWriter.writeObjects(dataObjects, preprocessedWikiQADir);
    }
}

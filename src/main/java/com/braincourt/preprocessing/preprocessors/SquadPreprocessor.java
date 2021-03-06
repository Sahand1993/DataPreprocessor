package com.braincourt.preprocessing.preprocessors;

import com.braincourt.preprocessing.DataWriter;
import com.braincourt.preprocessing.filevisitors.SquadFileVisitor;
import com.braincourt.preprocessing.traversers.SquadTraverser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SquadPreprocessor extends FileSystemPreprocessor {

    public SquadPreprocessor(
            DataWriter dataWriter,
            SquadTraverser fileTraverser,
            SquadFileVisitor fileVisitor,
            @Value("${processed.data.dir}") String preprocessedDir,
            @Value("${squad.folder}") String squadDirName
    ) {
        super(dataWriter);
        this.fileTraverser = fileTraverser;
        this.fileVisitor = fileVisitor;
        this.preprocessedDir = preprocessedDir + squadDirName;
    }
}

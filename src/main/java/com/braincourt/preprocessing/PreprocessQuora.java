package com.braincourt.preprocessing;

import com.braincourt.preprocessing.filevisitors.QuoraFileVisitor;
import com.braincourt.preprocessing.traversers.QuoraTraverser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PreprocessQuora extends PreprocessingStep {

    public PreprocessQuora(
            DataWriter dataWriter,
            QuoraTraverser fileTraverser,
            QuoraFileVisitor fileVisitor,
            @Value("${processed.data.dir}") String preprocessedDir,
            @Value("${quora.folder}") String quoraDirName
    ) {
        super(dataWriter);
        this.fileTraverser = fileTraverser;
        this.fileVisitor = fileVisitor;
        this.preprocessedDir = preprocessedDir + quoraDirName;
    }
}

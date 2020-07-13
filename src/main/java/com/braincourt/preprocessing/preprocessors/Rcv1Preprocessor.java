package com.braincourt.preprocessing.preprocessors;

import com.braincourt.preprocessing.DataWriter;
import com.braincourt.preprocessing.filevisitors.ReutersFileVisitor;
import com.braincourt.preprocessing.traversers.ReutersTraverser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Rcv1Preprocessor extends FileSystemPreprocessor {
    public Rcv1Preprocessor(
        DataWriter dataWriter,
        ReutersTraverser fileTraverser,
        ReutersFileVisitor fileVisitor,
        @Value("${processed.data.dir}") String preprocessedDir,
        @Value("${reuters.folder}") String rcv1DirName
    ) {
            super(dataWriter);
            this.fileTraverser = fileTraverser;
            this.fileVisitor = fileVisitor;
            this.preprocessedDir = preprocessedDir + rcv1DirName;
        }
}

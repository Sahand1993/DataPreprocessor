package com.braincourt.preprocessing.preprocessors;

import com.braincourt.preprocessing.DataWriter;
import com.braincourt.preprocessing.filevisitors.WikiQAFileVisitor;
import com.braincourt.preprocessing.traversers.WikiQATraverser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WikiQAPreprocessor extends FileSystemPreprocessor {
    public WikiQAPreprocessor(
            DataWriter dataWriter,
            WikiQATraverser fileTraverser,
            WikiQAFileVisitor fileVisitor,
            @Value("${processed.data.dir}") String preprocessedDir,
            @Value("${wikiQA.folder}") String wikiQADirName
            ) {
        super(dataWriter);
        this.fileTraverser = fileTraverser;
        this.fileVisitor = fileVisitor;
        this.preprocessedDir = preprocessedDir + wikiQADirName;
    }
}

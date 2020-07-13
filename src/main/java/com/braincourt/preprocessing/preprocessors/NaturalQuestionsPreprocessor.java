package com.braincourt.preprocessing.preprocessors;

import com.braincourt.preprocessing.DataWriter;
import com.braincourt.preprocessing.filevisitors.NaturalQuestionsFileVisitor;
import com.braincourt.preprocessing.traversers.NaturalQuestionsTraverser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NaturalQuestionsPreprocessor extends FileSystemPreprocessor {

    public NaturalQuestionsPreprocessor(
            DataWriter dataWriter,
            NaturalQuestionsTraverser fileTraverser,
            NaturalQuestionsFileVisitor fileVisitor,
            @Value("${processed.data.dir}") String preprocessedDir,
            @Value("${nq.folder}") String naturalQuestionsDirName
    ) {
        super(dataWriter);
        this.fileTraverser = fileTraverser;
        this.fileVisitor = fileVisitor;
        this.preprocessedDir = preprocessedDir + naturalQuestionsDirName;
    }
}

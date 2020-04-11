package com.braincourt.onehotvectors.writers.file.ngram;

import com.braincourt.mysql.entities.DuplicateQuestions;
import com.braincourt.onehotvectors.entitystreamers.ngramindices.DuplicateQuestionNgramIndicesStreamer;
import com.braincourt.onehotvectors.writers.file.FileWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DuplicateQuestionsNgramIndicesFileWriter extends FileWriter<DuplicateQuestions> {

    public DuplicateQuestionsNgramIndicesFileWriter(DuplicateQuestionNgramIndicesStreamer duplicateQuestionNgramIndicesStreamer,
                                                    @Value("${quora.home}") String datasetHome,
                                                    @Value("${onehot.filename.csv}") String filename,
                                                    @Value("${ngram.size}") int ngramSize) {
        super(duplicateQuestionNgramIndicesStreamer, datasetHome + String.format(filename, ngramSize), DuplicateQuestions.class);

    }
}

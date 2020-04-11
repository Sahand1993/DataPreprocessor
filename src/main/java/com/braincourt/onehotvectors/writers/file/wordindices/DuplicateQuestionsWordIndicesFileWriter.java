package com.braincourt.onehotvectors.writers.file.wordindices;

import com.braincourt.mysql.entities.DuplicateQuestions;
import com.braincourt.onehotvectors.entitystreamers.wordindices.DuplicateQuestionWordIndicesStreamer;
import com.braincourt.onehotvectors.writers.file.FileWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DuplicateQuestionsWordIndicesFileWriter extends FileWriter<DuplicateQuestions> {
    public DuplicateQuestionsWordIndicesFileWriter(DuplicateQuestionWordIndicesStreamer streamer,
                                                   @Value("${quora.home}") String datasetHome,
                                                   @Value("${wordIndices.fileName}") String wordIndicesFileName) {
        super(streamer, datasetHome + wordIndicesFileName, DuplicateQuestions.class);
    }
}

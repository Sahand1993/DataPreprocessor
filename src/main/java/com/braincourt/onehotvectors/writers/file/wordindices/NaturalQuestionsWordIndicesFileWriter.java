package com.braincourt.onehotvectors.writers.file.wordindices;

import com.braincourt.mysql.entities.NaturalQuestions;
import com.braincourt.onehotvectors.entitystreamers.wordindices.NaturalQuestionWordIndicesStreamer;
import com.braincourt.onehotvectors.writers.file.FileWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NaturalQuestionsWordIndicesFileWriter extends FileWriter<NaturalQuestions> {
    public NaturalQuestionsWordIndicesFileWriter(NaturalQuestionWordIndicesStreamer streamer,
                                                 @Value("${naturalQuestions.home}") String datasetHome,
                                                 @Value("${wordIndices.fileName}") String wordIndicesFileName) {
        super(streamer, datasetHome + wordIndicesFileName, NaturalQuestions.class);
    }
}

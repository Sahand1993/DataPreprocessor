package com.braincourt.onehotvectors.writers.file;

import com.braincourt.mysql.entities.NaturalQuestions;
import com.braincourt.onehotvectors.entitystreamers.NaturalQuestionAllStreamer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NaturalQuestionsAllIndicesFileWriter extends FileWriter<NaturalQuestions> {

    public NaturalQuestionsAllIndicesFileWriter(NaturalQuestionAllStreamer naturalQuestionAllStreamer,
                                                @Value("${nq.folder}") String naturalQuestionsFolderName,
                                                @Value("${processed.data.dir}") String preprocessedHome,
                                                @Value("${data.csv}") String csvFile) {
        super(
                naturalQuestionAllStreamer,
                preprocessedHome + naturalQuestionsFolderName + csvFile,
                NaturalQuestions.class);
    }
}

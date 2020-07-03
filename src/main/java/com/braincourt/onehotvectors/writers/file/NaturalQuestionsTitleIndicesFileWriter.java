package com.braincourt.onehotvectors.writers.file;

import com.braincourt.mysql.entities.NaturalQuestions;
import com.braincourt.onehotvectors.entitystreamers.NaturalQuestionAllStreamer;
import com.braincourt.onehotvectors.entitystreamers.NaturalQuestionTitleStreamer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NaturalQuestionsTitleIndicesFileWriter extends FileWriter<NaturalQuestions> {

    public NaturalQuestionsTitleIndicesFileWriter(NaturalQuestionTitleStreamer naturalQuestionTitleStreamer,
                                                  @Value("${nq.folder}") String naturalQuestionsFolderName,
                                                  @Value("${processed.data.dir}") String preprocessedHome,
                                                  @Value("${data.csv}") String csvFile) {
        super(
                naturalQuestionTitleStreamer,
                preprocessedHome + naturalQuestionsFolderName + csvFile,
                NaturalQuestions.class);
    }
}

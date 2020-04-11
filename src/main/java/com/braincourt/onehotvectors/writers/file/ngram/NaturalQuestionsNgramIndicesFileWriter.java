package com.braincourt.onehotvectors.writers.file.ngram;

import com.braincourt.mysql.entities.NaturalQuestions;
import com.braincourt.onehotvectors.entitystreamers.ngramindices.NaturalQuestionsNgramIndicesStreamer;
import com.braincourt.onehotvectors.writers.file.FileWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NaturalQuestionsNgramIndicesFileWriter extends FileWriter<NaturalQuestions> {

    public NaturalQuestionsNgramIndicesFileWriter(NaturalQuestionsNgramIndicesStreamer streamer,
                                                  @Value("${naturalQuestions.home}") String datasetHome,
                                                  @Value("${onehot.filename.csv}") String filename,
                                                  @Value("${ngram.size}") int ngramSize) {
        super(streamer, datasetHome + String.format(filename, ngramSize), NaturalQuestions.class);
    }
}

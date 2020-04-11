package com.braincourt.onehotvectors.writers.database.ngram;

import com.braincourt.mysql.entities.NaturalQuestions;
import com.braincourt.mysql.repositories.NaturalQuestionsRepository;
import com.braincourt.onehotvectors.entitystreamers.ngramindices.NaturalQuestionsNgramIndicesStreamer;
import com.braincourt.onehotvectors.writers.database.SingleTableDatabaseWriter;
import org.springframework.stereotype.Component;

@Component
public class NaturalQuestionsDatabaseWriter extends SingleTableDatabaseWriter<NaturalQuestions> {

    public NaturalQuestionsDatabaseWriter(
            NaturalQuestionsNgramIndicesStreamer naturalQuestionsNgramIndicesStreamer,
            NaturalQuestionsRepository naturalQuestionsRepository) {
        this.streamer = naturalQuestionsNgramIndicesStreamer;
        this.repository = naturalQuestionsRepository;
    }


}

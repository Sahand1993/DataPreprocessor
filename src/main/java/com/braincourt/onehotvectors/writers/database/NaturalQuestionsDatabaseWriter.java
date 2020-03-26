package com.braincourt.onehotvectors.writers.database;

import com.braincourt.mysql.entities.NaturalQuestions;
import com.braincourt.mysql.repositories.NaturalQuestionsRepository;
import com.braincourt.onehotvectors.writers.entitystream.NaturalQuestionStreamer;
import org.springframework.stereotype.Component;

@Component
public class NaturalQuestionsDatabaseWriter extends DatabaseWriter<NaturalQuestions> {

    public NaturalQuestionsDatabaseWriter(
            NaturalQuestionStreamer naturalQuestionStreamer,
            NaturalQuestionsRepository naturalQuestionsRepository) {
        this.streamer = naturalQuestionStreamer;
        this.repository = naturalQuestionsRepository;
    }


}

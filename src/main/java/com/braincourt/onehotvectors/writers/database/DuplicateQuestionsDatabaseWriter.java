package com.braincourt.onehotvectors.writers.database;

import com.braincourt.mysql.entities.DuplicateQuestions;
import com.braincourt.mysql.repositories.DuplicateQuestionRepository;
import com.braincourt.onehotvectors.writers.entitystream.DuplicateQuestionStreamer;
import org.springframework.stereotype.Component;

@Component
public class DuplicateQuestionsDatabaseWriter extends DatabaseWriter<DuplicateQuestions> {
    public DuplicateQuestionsDatabaseWriter(
            DuplicateQuestionStreamer duplicateQuestionStreamer,
            DuplicateQuestionRepository duplicateQuestionRepository) {
        this.streamer = duplicateQuestionStreamer;
        this.repository = duplicateQuestionRepository;
    }
}

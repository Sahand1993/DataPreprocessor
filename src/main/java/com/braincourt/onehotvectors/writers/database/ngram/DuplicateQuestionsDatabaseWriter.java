package com.braincourt.onehotvectors.writers.database.ngram;

import com.braincourt.mysql.entities.DuplicateQuestions;
import com.braincourt.mysql.repositories.DuplicateQuestionRepository;
import com.braincourt.onehotvectors.entitystreamers.ngramindices.DuplicateQuestionNgramIndicesStreamer;
import com.braincourt.onehotvectors.writers.database.SingleTableDatabaseWriter;
import org.springframework.stereotype.Component;

@Component
public class DuplicateQuestionsDatabaseWriter extends SingleTableDatabaseWriter<DuplicateQuestions> {
    public DuplicateQuestionsDatabaseWriter(
            DuplicateQuestionNgramIndicesStreamer duplicateQuestionNgramIndicesStreamer,
            DuplicateQuestionRepository duplicateQuestionRepository) {
        this.streamer = duplicateQuestionNgramIndicesStreamer;
        this.repository = duplicateQuestionRepository;
    }
}

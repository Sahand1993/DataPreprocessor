package com.braincourt.onehotvectors.writers.database;

import com.braincourt.mysql.entities.DuplicateQuestions;
import com.braincourt.mysql.repositories.DuplicateQuestionRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DuplicateQuestionsDatabaseWriter extends SingleTableDatabaseWriter<DuplicateQuestions> {
   // public DuplicateQuestionsDatabaseWriter(
   //         DuplicateQuestionTrainStreamer duplicateQuestionTrainStreamer,
   //         DuplicateQuestionValidationStreamer duplicateQuestionValidationStreamer,
   //         DuplicateQuestionRepository duplicateQuestionRepository) {
   //     this.streamers = Arrays.asList(duplicateQuestionTrainStreamer, duplicateQuestionValidationStreamer);
   //     this.repository = duplicateQuestionRepository;
    //}
}

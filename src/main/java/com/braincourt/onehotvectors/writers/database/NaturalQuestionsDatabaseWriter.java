package com.braincourt.onehotvectors.writers.database;

import com.braincourt.mysql.entities.NaturalQuestions;
import com.braincourt.mysql.repositories.NaturalQuestionsRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class NaturalQuestionsDatabaseWriter extends SingleTableDatabaseWriter<NaturalQuestions> {

   // public NaturalQuestionsDatabaseWriter(
   //         NaturalQuestionTrainStreamer naturalQuestionsTrainStreamer, // TODO: use several streamers for each validationset
   //         NaturalQuestionValidationStreamer naturalQuestionValidationStreamer,
   //         NaturalQuestionTestStreamer naturalQuestionTestStreamer,
   //         NaturalQuestionsRepository naturalQuestionsRepository) {
   //     this.streamers = Arrays.asList(naturalQuestionsTrainStreamer, naturalQuestionValidationStreamer, naturalQuestionTestStreamer);
    //    this.repository = naturalQuestionsRepository;
   // }


}

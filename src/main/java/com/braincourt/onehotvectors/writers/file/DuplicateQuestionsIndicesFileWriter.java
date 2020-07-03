package com.braincourt.onehotvectors.writers.file;

import com.braincourt.mysql.entities.DuplicateQuestions;
import com.braincourt.onehotvectors.entitystreamers.DuplicateQuestionStreamer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DuplicateQuestionsIndicesFileWriter extends FileWriter<DuplicateQuestions> {

    public DuplicateQuestionsIndicesFileWriter(DuplicateQuestionStreamer duplicateQuestionStreamer,
                                               @Value("${processed.data.dir}") String preprocessedHome,
                                               @Value("${quora.folder}") String quoraFolderName,
                                               @Value("${data.csv}") String csvFileName) {
        super(
                duplicateQuestionStreamer,
                preprocessedHome + quoraFolderName + csvFileName,
                DuplicateQuestions.class
        );

    }
}

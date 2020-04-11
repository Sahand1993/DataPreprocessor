package com.braincourt.onehotvectors.writers.file;

import com.braincourt.mysql.entities.DuplicateQuestions;
import com.braincourt.onehotvectors.entitystreamers.DuplicateQuestionStreamer;
import com.braincourt.onehotvectors.entitystreamers.EntityStreamer;
import com.braincourt.onehotvectors.writers.Writer;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class DuplicateQuestionsFileWriter extends Writer {

    EntityStreamer<DuplicateQuestions> streamer;
    BufferedWriter bufferedWriter;
    String outFilePath;

    public DuplicateQuestionsFileWriter(DuplicateQuestionStreamer duplicateQuestionStreamer,
                                        @Value("${quora.home}") String datasetHome,
                                        @Value("${onehot.filename.json}") String filename,
                                        @Value("${ngram.size}") int ngramSize) {
        streamer = duplicateQuestionStreamer;
        outFilePath = datasetHome + String.format(filename, ngramSize);
    }

    @Override
    public void write() {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outFilePath));
            streamer.getEntities().forEach(this::writeToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(DuplicateQuestions duplicateQuestions) {
        Gson gson = new Gson();
        String json = gson.toJson(duplicateQuestions);
        try {
            bufferedWriter.write(json);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

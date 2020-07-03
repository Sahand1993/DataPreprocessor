package com.braincourt.preprocessing.filevisitors;

import com.braincourt.preprocessing.Tokenizer;
import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.dataobjects.WikiQADataObject;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class WikiQAFileVisitor extends FileVisitor {

    Gson gson = new Gson();

    Map<String, Set<String>> seenQuestionsAndDocs;

    public WikiQAFileVisitor(Tokenizer tokenizer) {
        super(tokenizer);
        this.seenQuestionsAndDocs = new HashMap<>();
    }

    @Override
    public Stream<DataObject> toDataObjects(Stream<Path> dataFilePaths) {
        return dataFilePaths.flatMap(this::toDataObjects);
    }

    private Stream<DataObject> toDataObjects(Path path) {
        try {
            return new BufferedReader(new FileReader(String.valueOf(path))).lines().skip(1)
                    .map(this::toDataObject)
                    .filter(Objects::nonNull);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    private DataObject toDataObject(String line) {
        String[] values = line.split("\t");
        String questionId = values[0];
        String question = values[1];
        List<String> questionTokens = tokenizer.processTokens(question);
        String docId = values[2];
        String title = values[3];
        List<String> titleTokens = tokenizer.processTokens(title);
        if (questionTokens.isEmpty() || titleTokens.isEmpty()) {
            return null;
        }
        boolean answered = values[6].equals("1");
       // if (answered) {
            if (seenQuestionsAndDocs.containsKey(questionId)) {
                if (seenQuestionsAndDocs.get(questionId).contains(docId)) {
                    return null;
                } else {
                    seenQuestionsAndDocs.get(questionId).add(docId);
                }
            } else {
                Set<String> newEntry = new HashSet<>();
                newEntry.add(docId);
                seenQuestionsAndDocs.put(questionId, newEntry);
            }
            return new WikiQADataObject().setQuestionId(questionId).setQuestionTokens(questionTokens).setDocId(docId).setTitleTokens(titleTokens).setAnswered(answered);
      //  } else {
      //      return null;
      //  }
    }
}

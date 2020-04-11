package com.braincourt.onehotvectors.entitystreamers;

import com.braincourt.mysql.entities.DuplicateQuestions;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public abstract class DuplicateQuestionStreamer extends EntityStreamer<DuplicateQuestions> {

    AtomicLong currentId = new AtomicLong(1);

    public DuplicateQuestionStreamer(String jsonDataPath,
                                     String csvDelimiter,
                                     String indicesDelimiter) {
        super(jsonDataPath, csvDelimiter, indicesDelimiter);
    }

    /**
     * Creates a dataRow object containing the one-hot representation of dataRow.
     * @param duplicateQuestionJson
     * @return
     */
    @Override
    public List<DuplicateQuestions> createEntities(JsonObject duplicateQuestionJson) {
        boolean isDuplicate = duplicateQuestionJson.get("isDuplicate").getAsBoolean();
        if (!isDuplicate) {
            return new ArrayList<>();
        }

        DuplicateQuestions duplicateQuestions = new DuplicateQuestions();
        duplicateQuestions.setQuestion1NGrams(getQuestion1Indices(duplicateQuestionJson));
        duplicateQuestions.setQuestion2NGrams(getQuestion2Indices(duplicateQuestionJson));
        duplicateQuestions.setIsDuplicate(true);
        duplicateQuestions.setQuestion1Id(duplicateQuestionJson.get("question1Id").getAsInt());
        duplicateQuestions.setQuestion2Id(duplicateQuestionJson.get("question2Id").getAsInt());
        duplicateQuestions.setId(currentId.getAndIncrement());

        return Collections.singletonList(duplicateQuestions);
    }

    protected abstract String getQuestion1Indices(JsonObject duplicateQuestionJson);

    protected abstract String getQuestion2Indices(JsonObject duplicateQuestionJson);
}

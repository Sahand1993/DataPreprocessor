package com.braincourt.onehotvectors.entitystreamers;

import com.braincourt.mysql.entities.DuplicateQuestions;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DuplicateQuestionStreamer extends EntityStreamer<DuplicateQuestions> {

    AtomicLong currentId = new AtomicLong(1);

    public DuplicateQuestionStreamer(@Value("${processed.data.dir}") String preprocessedHome,
                                     @Value("${filename.json}") String dataFileName,
                                     @Value("${csv.delimiter}") String csvDelimiter,
                                     @Value("${indices.delimiter}") String indicesDelimiter) {
        super(preprocessedHome + "quora/" + dataFileName, csvDelimiter, indicesDelimiter); // TODO: remove train and validation class, send list of all paths here instead.
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

        duplicateQuestions.setQuestion1NGrams(getQuestion1NGramIndices(duplicateQuestionJson));
        duplicateQuestions.setQuestion2NGrams(getQuestion2NGramIndices(duplicateQuestionJson));

        duplicateQuestions.setQuestion1WordIndices(getQuestion1WordIndices(duplicateQuestionJson));
        duplicateQuestions.setQuestion2WordIndices(getQuestion2WordIndices(duplicateQuestionJson));

        duplicateQuestions.setIsDuplicate(true);

        duplicateQuestions.setQuestion1Id(duplicateQuestionJson.get("question1Id").getAsInt());
        duplicateQuestions.setQuestion2Id(duplicateQuestionJson.get("question2Id").getAsInt());

        duplicateQuestions.setId(duplicateQuestionJson.get("id").getAsLong());

        return Collections.singletonList(duplicateQuestions);
    }

    protected String getQuestion1NGramIndices(JsonObject json) {
        return getJoinedNGramIndicesFrom(json, "question1_tokens");
    }

    protected String getQuestion2NGramIndices(JsonObject json) {
        return getJoinedNGramIndicesFrom(json, "question2_tokens");
    }

    private String getJoinedNGramIndicesFrom(JsonObject json, String jsonKey) {
        return getNgramIndices(json, jsonKey);
    }

    protected String getQuestion1WordIndices(JsonObject json) {
        return getJoinedWordIndicesFrom(json, "question1_tokens");
    }

    protected String getQuestion2WordIndices(JsonObject json) {
        return getJoinedWordIndicesFrom(json, "question2_tokens");
    }

    private String getJoinedWordIndicesFrom(JsonObject json, String jsonKey) {
        return getWordIndicesWithFreqs(json, jsonKey);
    }
}

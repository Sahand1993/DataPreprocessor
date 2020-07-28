package com.braincourt.onehotvectors.entitystreamers;

import com.braincourt.mysql.entities.WikiQAEntity;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class WikiQAEntityStreamer extends EntityStreamer {

    AtomicLong currentId = new AtomicLong();

    public WikiQAEntityStreamer(@Value("${processed.data.dir}") String preprocessedHome,
                               @Value("${wikiQA.folder}") String wikiQAFolderName,
                               @Value("${filename.json}") String fileName,
                               @Value("${indices.delimiter}") String indicesDelimiter,
                               @Value("${csv.delimiter}") String csvDelimiter) {
        super(Paths.get(preprocessedHome, wikiQAFolderName, fileName).toString(), csvDelimiter, indicesDelimiter);
    }

    @Override
    public List createEntities(JsonObject dataRow) {
        return Collections.singletonList(
                new WikiQAEntity()
                        .setId(Long.toString(currentId.getAndIncrement()))
                        .setQuestionId(dataRow.get("questionId").getAsString())
                        .setDocId(dataRow.get("docId").getAsString())
                        .setQuestionNGramIndices(getNgramIndices(dataRow, "questionTokens"))
                        .setQuestionWordIndices(getWordIndicesWithFreqs(dataRow, "questionTokens"))
                        .setTitleNGramIndices(getNgramIndices(dataRow, "titleTokens"))
                        .setTitleWordIndices(getWordIndicesWithFreqs(dataRow, "titleTokens")));
    }
}

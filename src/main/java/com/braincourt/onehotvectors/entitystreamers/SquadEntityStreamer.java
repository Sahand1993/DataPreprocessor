package com.braincourt.onehotvectors.entitystreamers;

import com.braincourt.mysql.entities.SquadEntity;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SquadEntityStreamer extends EntityStreamer<SquadEntity> {

    AtomicInteger currentId = new AtomicInteger();

    public SquadEntityStreamer(@Value("${processed.data.dir}") String preprocessedHome,
                               @Value("${squad.folder}") String squadFolderName,
                               @Value("${filename.json}") String fileName,
                               @Value("${indices.delimiter}") String indicesDelimiter,
                               @Value("${csv.delimiter}") String csvDelimiter) {
        super(Paths.get(preprocessedHome, squadFolderName, fileName).toString(), csvDelimiter, indicesDelimiter);
    }
    @Override
    public List<SquadEntity> createEntities(JsonObject dataRow) {
        SquadEntity squadEntity = new SquadEntity();
        return Collections.singletonList(
                squadEntity
                .setId(currentId.getAndIncrement())
                .setQuestionId(dataRow.get("questionId").getAsString())
                .setTitleId(dataRow.get("titleId").getAsString())
                .setQuestionNGramIndices(getNgramIndices(dataRow, "questionTokens"))
                .setQuestionWordIndices(getWordIndicesWithFreqs(dataRow, "questionTokens"))
                .setTitleNGramIndices(getNgramIndices(dataRow, "titleTokens"))
                .setTitleWordIndices(getWordIndicesWithFreqs(dataRow, "titleTokens")));
    }
}

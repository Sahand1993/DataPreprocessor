package com.braincourt.onehotvectors.entitystreamers;

import com.braincourt.mysql.entities.ConfluenceDocument;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Component
public class ConfluenceDocumentStreamer extends EntityStreamer<ConfluenceDocument> {

    public ConfluenceDocumentStreamer(@Value("${processed.data.dir}") String preprocessedHome,
                                      @Value("${filename.json}") String dataFileName,
                                      @Value("${csv.delimiter}") String csvDelimiter,
                                      @Value("${indices.delimiter}") String indicesDelimiter,
                                      @Value("${confluence.folder}") String confluenceFolder) {
        super(Paths.get(preprocessedHome, confluenceFolder, dataFileName).toAbsolutePath().toString(), csvDelimiter, indicesDelimiter);
    }

    @Override
    public List<ConfluenceDocument> createEntities(JsonObject dataRow) {
        ConfluenceDocument confluenceDocument = new ConfluenceDocument();
        confluenceDocument.setTitleNGrams(getNgramIndices(dataRow, "titleTokens"));
        if (dataRow.get("id").getAsString().equals("-")) {
            return Collections.emptyList();
        } else {
            confluenceDocument.setId(dataRow.get("id").getAsString());
            return Collections.singletonList(confluenceDocument);
        }
    }
}

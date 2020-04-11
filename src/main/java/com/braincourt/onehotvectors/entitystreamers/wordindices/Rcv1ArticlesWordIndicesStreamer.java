package com.braincourt.onehotvectors.entitystreamers.wordindices;

import com.braincourt.onehotvectors.entitystreamers.Rcv1ArticleStreamer;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Rcv1ArticlesWordIndicesStreamer extends Rcv1ArticleStreamer {

    public Rcv1ArticlesWordIndicesStreamer(
            @Value("${reuters.home}") String datasetHome,
            @Value("${filename.json}") String jsonFileName,
            @Value("${csv.delimiter}") String csvDelimiter,
            @Value("${indices.delimiter}") String indicesDelimiter) {
        super(datasetHome + jsonFileName, csvDelimiter, indicesDelimiter);
    }

    @Override
    public String getIndicesFrom(JsonObject articleJson) {
        return getWordIndices(articleJson, "tokens");
    }


}

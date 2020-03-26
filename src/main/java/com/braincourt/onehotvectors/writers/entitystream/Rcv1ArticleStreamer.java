package com.braincourt.onehotvectors.writers.entitystream;


import com.braincourt.mysql.entities.RcvArticles;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class Rcv1ArticleStreamer extends EntityStreamer<RcvArticles> {

    @Value("${reuters.csvHeader}")
    String csvHeader;

    @Value("ngram.delimiter")
    String delimiter;

    public Rcv1ArticleStreamer(
            @Value("${reuters.home}") String datasetHome,
            @Value("${filename.json}") String jsonFileName) {
        this.jsonDataPath = datasetHome + jsonFileName;
    }

    @Override
    public Optional<RcvArticles> createEntity(JsonObject articleJson) {
        RcvArticles rcvArticles = new RcvArticles();
        rcvArticles.setArticleNGrams(getNGramIdsFrom(articleJson));
        rcvArticles.setTags(getTopicTags(articleJson));
        return Optional.of(rcvArticles);
    }

    private String getNGramIdsFrom(JsonObject json) {
        List<String> articleTokens = StreamSupport.stream(json.getAsJsonArray("tokens").spliterator(), false)
                .map(JsonElement::getAsString)
                .collect(Collectors.toList());

        SortedSet<String> nGramIds = getNGramIds(articleTokens);

        return String.join(delimiter, nGramIds);
    }

    private String getTopicTags(JsonObject json) {
        return StreamSupport.stream(json.get("topic_tags").getAsJsonArray().spliterator(), false)
                .map(JsonElement::getAsString)
                .collect(Collectors.joining(delimiter));

    }
}

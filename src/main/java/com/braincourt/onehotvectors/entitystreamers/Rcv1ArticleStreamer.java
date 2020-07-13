package com.braincourt.onehotvectors.entitystreamers;

import com.braincourt.mysql.entities.RcvArticles;
import com.braincourt.mysql.entities.RcvArticlesWithTopicTags;
import com.braincourt.mysql.entities.RcvArticlesWithoutTopicTags;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Scope("singleton")
public class Rcv1ArticleStreamer extends EntityStreamer<RcvArticles> {

    AtomicLong articlesWithoutTopicTagsCurrentId = new AtomicLong(1);
    AtomicLong articlesWithTopicTagsCurrentId = new AtomicLong(1);
    String csvDelimiter;

    public Rcv1ArticleStreamer(@Value("${processed.data.dir}") String preprocessedHome,
                               @Value("${filename.json}") String dataFileName,
                               @Value("${csv.delimiter}") String csvDelimiter,
                               @Value("${indices.delimiter}") String indicesDelimiter) {
        super(
                preprocessedHome + "rcv1/" + dataFileName,
                csvDelimiter,
                indicesDelimiter);
        this.csvDelimiter = csvDelimiter;
    }

    @Override
    public List<RcvArticles> createEntities(JsonObject articleJson) {
        int articleId = articleJson.get("articleId").getAsInt();
        String articleNGramIndices = getNgramIndices(articleJson, "tokens");
        String articleWordIndices = getWordIndicesWithFreqs(articleJson, "tokens");
        List<String> tags = getTopicTags(articleJson);

        if (tags.isEmpty()) {
            RcvArticlesWithoutTopicTags article
                    = new RcvArticlesWithoutTopicTags(articleId, articleNGramIndices, articleWordIndices);
            article.setId(articlesWithoutTopicTagsCurrentId.getAndIncrement());
            return Collections.singletonList(article);
        } else {
            RcvArticlesWithTopicTags article
                    = new RcvArticlesWithTopicTags(articleId, articleNGramIndices, articleWordIndices);
            article.setAllTags(tags);
            article.setId(articlesWithTopicTagsCurrentId.getAndIncrement());
            return Collections.singletonList(article);
        }
    }

    private List<String> getTopicTags(JsonObject json) {
        return StreamSupport.stream(json.get("topicTags").getAsJsonArray().spliterator(), false)
                .map(JsonElement::getAsString)
                .filter(tag -> !tag.endsWith("CAT"))
                .collect(Collectors.toList());
    }
}

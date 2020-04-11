package com.braincourt.onehotvectors.entitystreamers;

import com.braincourt.mysql.entities.DatabaseEntity;
import com.braincourt.onehotvectors.OneHotNgramVectors;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/**
 * Responsible for writing onehot representations to the right file on disk
 */
public abstract class EntityStreamer<T extends DatabaseEntity> {

    Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected String indicesDelimiter;
    protected String csvDelimiter;

    protected String jsonDataPath;

    public EntityStreamer(String jsonDataPath,
                          String csvDelimiter,
                          String indicesDelimiter) {
        this.jsonDataPath = jsonDataPath;
        this.indicesDelimiter = indicesDelimiter;
        this.csvDelimiter = csvDelimiter;
    }

    private BufferedReader bufferedReader;

    public Stream<T> getEntities() {
        try {
            bufferedReader = new BufferedReader(new FileReader(jsonDataPath));
            return bufferedReader.lines().map(JsonParser::parseString)
                    .map(JsonElement::getAsJsonObject)
                    .map(this::createEntities)
                    .filter(entities -> !entities.isEmpty())
                    .flatMap(Collection::stream);


        } catch (IOException e) {

            e.printStackTrace();
            return Stream.empty();

        }
    }

    public abstract List<T> createEntities(JsonObject dataRow);

    protected SortedSet<String> getNGramIndices(List<String> tokens) {
        SortedSet<String> nGramIds = new TreeSet<>();
        Map<String, Map<String, Integer>> wordToNGramsWithIds = OneHotNgramVectors.getWordsToNGramsWithIds();
        for (int i = 0; i < tokens.size(); i++) {

            String token = tokens.get(i);
            Map<String, Integer> nGramsWithIds = wordToNGramsWithIds.get(token);
            if (nGramsWithIds != null) {
                Collection<Integer> tokenNGramIds = nGramsWithIds.values();
                tokenNGramIds.stream()
                        .map(Object::toString)
                        .forEach(nGramIds::add);
            }
        }

        return nGramIds;
    }

    protected SortedSet<String> getWordIndices(List<String> tokens) {
        SortedSet<String> wordIds = new TreeSet<>();
        Map<String, String> vocabulary = OneHotNgramVectors.getVocabulary();
        tokens.stream()
                .map(token -> vocabulary.getOrDefault(token, null))
                .filter(Objects::nonNull)
                .forEach(wordIds::add);
        return wordIds;
    }

    protected List<JsonElement> getAsList(JsonArray json) {
        return StreamSupport.stream(json.spliterator(), false).collect(Collectors.toList());
    }

    protected List<String> getAslist(JsonObject dataRow, String key) {
        return StreamSupport.stream(dataRow.getAsJsonArray(key).spliterator(), false)
                .map(JsonElement::getAsString)
                .collect(Collectors.toList());
    }

    protected String getNgramIndices(JsonObject dataRow, String key) {
        List<String> documentTokens = getAslist(dataRow, key);

        SortedSet<String> wordIds = getNGramIndices(documentTokens);

        return String.join(indicesDelimiter, wordIds);
    }

    protected String getWordIndices(JsonObject dataRow, String key) {
        List<String> documentTokens = getAslist(dataRow, key);

        SortedSet<String> wordIds = getWordIndices(documentTokens);

        return String.join(indicesDelimiter, wordIds);
    }
}

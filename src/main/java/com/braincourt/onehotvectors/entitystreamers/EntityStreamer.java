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
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public abstract class EntityStreamer<T extends DatabaseEntity> {

    Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected String indicesDelimiter;
    protected String csvDelimiter;

    protected String jsonDataPath;

    private BufferedReader bufferedReader;

    public EntityStreamer(String jsonDataPath,
                          String csvDelimiter,
                          String indicesDelimiter) {
        this.jsonDataPath = jsonDataPath;
        this.indicesDelimiter = indicesDelimiter;
        this.csvDelimiter = csvDelimiter;
    }

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

    protected SortedMap<String, Integer> getNGramIndicesWithFreqs(List<String> tokens) {
        SortedMap<String, Integer> nGramsWithFreqs = new TreeMap<>();
        Map<String, Map<Integer, Integer>> wordToNGramToIdToFreq = OneHotNgramVectors.getWordsToNGramIdsToFreqs();
        for (String token : tokens) {

            Map<Integer, Integer> tokenNGramToFreq = Objects.requireNonNull(wordToNGramToIdToFreq.get(token));
//            Collection<Integer> tokenNGramIds = tokenNGramToFreq.values();
//            tokenNGramIds.stream()
//                    .map(Object::toString)
//                    .forEach(nGramId -> nGramsWithFreqs.merge(nGramId, 1, Integer::sum));
            tokenNGramToFreq.entrySet().forEach(entry -> {

                    String nGramId = entry.getKey().toString();
                    Integer freq = entry.getValue();

                    if (nGramsWithFreqs.containsKey(nGramId)) {

                        nGramsWithFreqs.put(nGramId, nGramsWithFreqs.get(nGramId) + freq);

                    } else {

                        nGramsWithFreqs.put(nGramId, freq);

                    }
            });
        }

        return nGramsWithFreqs;
    }

    protected SortedMap<String, Integer> getWordIndicesWithFreqs(List<String> tokens) {
        SortedMap<String, Integer> wordIds = new TreeMap<>();
        Map<String, String> vocabulary = OneHotNgramVectors.getVocabulary();
        tokens.stream()
                .map(vocabulary::get)
                .forEach(wordId -> wordIds.merge(wordId, 1, Integer::sum));
        return wordIds;
    }

    protected static List<JsonElement> getAsList(JsonArray json) {
        return StreamSupport.stream(json.spliterator(), false).collect(Collectors.toList());
    }

    protected List<String> getAslist(JsonObject dataRow, String key) {
        return StreamSupport.stream(dataRow.getAsJsonArray(key).spliterator(), false)
                .map(JsonElement::getAsString)
                .collect(Collectors.toList());
    }

    protected String getNgramIndices(JsonObject dataRow, String key) {
        List<String> tokens = getAslist(dataRow, key);

        SortedMap<String, Integer> nGramIdsToFreqs = getNGramIndicesWithFreqs(tokens);

        return joinIdsWithFreqs(nGramIdsToFreqs);
    }


    protected String joinIdsWithFreqs(SortedMap<String, Integer> idsWithFreqs) {
        return String.join(
                indicesDelimiter,
                idsWithFreqs.entrySet().stream()
                        .map(stringIntegerEntry
                                -> String.format("%d %s", stringIntegerEntry.getValue(), stringIntegerEntry.getKey()))
                        .collect(Collectors.toList()));
    }

    protected String getWordIndicesWithFreqs(JsonObject dataRow, String key) {
        List<String> documentTokens = getAslist(dataRow, key);

        SortedMap<String, Integer> wordIds = getWordIndicesWithFreqs(documentTokens);

        return joinIdsWithFreqs(wordIds);
    }
}

package com.braincourt.onehotvectors.writers.entitystream;

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
import java.util.Optional;
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

    public String jsonDataPath;
    BufferedReader bufferedReader;

    public Stream<T> getEntities() {
        try {

            bufferedReader = new BufferedReader(new FileReader(jsonDataPath));
            return bufferedReader.lines().map(JsonParser::parseString)
                    .map(JsonElement::getAsJsonObject)
                    .map(this::createEntity)
                    .filter(Optional::isPresent)
                    .map(Optional::get);

        } catch (IOException e) {

            e.printStackTrace();
            return Stream.empty();

        }
    }

    public abstract Optional<T> createEntity(JsonObject dataRow);

    SortedSet<String> getNGramIds(List<String> tokens) {
        SortedSet<String> nGramIds = new TreeSet<>();

        for (int i = 0; i < tokens.size(); i++) {

            String token = tokens.get(i);
            Collection<Integer> tokenNGramIds = OneHotNgramVectors.getWordToNGramToId().get(token).values();
            tokenNGramIds.stream()
                    .map(Object::toString)
                    .forEach(nGramIds::add);

        }

        return nGramIds;
    }

    List<JsonElement> getAsList(JsonArray json) {
        return StreamSupport.stream(json.spliterator(), false).collect(Collectors.toList());
    }
}

package com.braincourt.preprocessing;

import com.braincourt.preprocessing.dataobjects.NaturalQuestionsToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Tokenizer {

    private static final Set<String> END_PUNCTUATION = new HashSet<>(Arrays.asList("?", ".", "!"));
    Set<String> stopwords;
    String stopwordsPath;
    String dataDir;

    Pattern validTokenRegex = Pattern.compile("[\\p{IsLatin}&&[^_]]+('[\\p{IsLatin}&&[^_]]*)?");

    public Tokenizer(@Value("${stopwords.path}") String stopwordsPath) {
        this.dataDir = dataDir;
        this.stopwordsPath = stopwordsPath;
        setStopWords();
    }

    private void setStopWords() {
        stopwords = new HashSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(stopwordsPath));
            bufferedReader.lines().forEach(stopWord -> stopwords.add(stopWord));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This class will stem the words. Takes in a list of words, returns a list of tokens (stemmed, stop words removed)
    public List<String> tokenize(String line) {
        return processTokens(Arrays.stream(line.split(" ")));
    }

    public List<String> processTokens(List<String> tokens) {
        return processTokens(tokens.stream());
    }

    public List<String> processTokens(Stream<String> tokens) {
        return tokens
                .map(this::removeEndPunctuation)
                .map(String::toLowerCase)
                .map(this::stem)
                //.filter(this::isValidToken)
                //.flatMap(this::splitOnApostrophe)
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toList());
    }

    public List<String> processNaturalQuestionsTokens(Stream<NaturalQuestionsToken> tokens) {
        return processTokens(tokens.map(NaturalQuestionsToken::getToken));
    }

    private Stream<String> splitOnApostrophe(String token) {
        if (token.contains("'")) {
            String[] tokens = token.split("'");
            if (tokens.length == 1) {
                return Stream.of(tokens[0]);
            }
            if (tokens[1].equals("s")) {
                return Stream.of(tokens[0]);
            }
            return Stream.of(tokens[0], "'" + tokens[1]);
        } else {
            return Stream.of(token);
        }
    }

    public List<String> processTokens(String token) {
        return processTokens(Arrays.stream(token.split("\\s+| ")));
    }

    private String removeEndPunctuation(String token) {
        if (token.length() > 0 && END_PUNCTUATION.contains(token.substring(token.length() - 1))) {
            return token.substring(0, token.length() - 1);
        }
        return token;
    }

    private Boolean isValidToken(String token) {
        return !isStopWord(token) && hasValidRegex(token);
    }

    private boolean hasValidRegex(String token) {
        return validTokenRegex.matcher(token).matches();
    }

    private String stem(String token) {
        if (token.endsWith("'")) {
            return token.substring(0, token.length() - 1);
        }
        return token;
    }

    private boolean isStopWord(String word) {
        return stopwords.contains(word.toLowerCase());
    }
}
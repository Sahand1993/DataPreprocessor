package com.braincourt.preprocessing;

import com.braincourt.preprocessing.dataobjects.NaturalQuestionsToken;
import com.braincourt.preprocessing.dataobjects.NaturalQuestionsTokenWithHtml;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
    String stopwordsFileName;
    String dataDir;

    Pattern validTokenRegex = Pattern.compile("[\\p{IsLatin}&&[^_]]+"); // all unicode and numbers, no underscores

    public Tokenizer(@Value("${dataDir}") String dataDir,
                     @Value("${stopwords.fileName}") String stopwordsFileName) {
        this.dataDir = dataDir;
        this.stopwordsFileName = stopwordsFileName;
        setStopWords();
        // TODO: Add stopwords from file
    }

    private void setStopWords() {
        stopwords = new HashSet<>();
        try {
            String stopwordsPath = dataDir + stopwordsFileName;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(stopwordsPath));
            bufferedReader.lines().forEach(stopWord -> stopwords.add(stopWord));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This class will stem the words. Takes in a list of words, returns a list of tokens (stemmed, stop words removed)
    public List<String> tokenize(String line) {
        return filterTokens(Arrays.stream(line.split(" ")));
    }

    public List<String> filterTokens(Stream<String> tokens) {
        return tokens
                .map(this::removeEndPunctuation)
                .filter(this::isValidToken)
                .map(this::normalizeToken)
                .collect(Collectors.toList());
    }

    private String removeEndPunctuation(String token) {
        if (token.length() > 0 && END_PUNCTUATION.contains(token.substring(token.length() - 1))) {
            return token.substring(0, token.length() - 1);
        }
        return token;
    }

    public List<NaturalQuestionsToken> filterNaturalQuestionTokens(Stream<NaturalQuestionsToken> tokens) {
        return tokens
                .filter(token -> isValidToken(token.getToken()))
                .map(token -> token.setToken(normalizeToken(token.getToken())))
                .collect(Collectors.toList());
    }

    private Boolean isValidToken(String token) {
        return !isStopWord(token) && hasValidRegex(token);
    }

    private String normalizeToken(String token) {
        return stem(token.toLowerCase().trim());
    }

    private boolean hasValidRegex(String token) {
        return validTokenRegex.matcher(token).matches();
    }

    private String stem(String token) {
        return token;
    }

    private boolean isStopWord(String word) {
        return stopwords.contains(word);
    }
}
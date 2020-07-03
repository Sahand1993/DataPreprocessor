package com.braincourt.preprocessing.filevisitors;

import com.braincourt.preprocessing.Tokenizer;
import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.dataobjects.LongAnswerCandidate;
import com.braincourt.preprocessing.dataobjects.NaturalQuestionsDataObject;
import com.braincourt.preprocessing.dataobjects.NaturalQuestionsToken;
import com.braincourt.preprocessing.jsonpojos.NaturalQuestionsJsonPojo;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class NaturalQuestionsFileVisitor extends FileVisitor {

    int currentId = 0;

    Logger LOG = LoggerFactory.getLogger(NaturalQuestionsFileVisitor.class);

    Gson gson;

    public NaturalQuestionsFileVisitor(Tokenizer tokenizer) {
        super(tokenizer);
        gson = new Gson();
    }

    @Override
    public Stream<DataObject> toDataObjects(Stream<Path> dataFilePaths) {
        return dataFilePaths.flatMap(this::toDataObjects);
    }

    private Stream<DataObject> toDataObjects(Path path) {
        try {
            FileReader reader = new FileReader(path.toAbsolutePath().toString());
            BufferedReader bufferedReader = new BufferedReader(reader);
            return bufferedReader.lines()
                    .map(this::toDataObject)
                    .filter(Objects::nonNull);

        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();

        }

    }

    private DataObject toDataObject(String line) {
        NaturalQuestionsJsonPojo naturalQuestionsJsonPojo = gson.fromJson(line, NaturalQuestionsJsonPojo.class);
        NaturalQuestionsDataObject nqDataObject = new NaturalQuestionsDataObject();

        nqDataObject.setId(currentId++);

        nqDataObject.setExampleId(naturalQuestionsJsonPojo.getExample_id());
        List<String> titleTokens = Arrays.asList(naturalQuestionsJsonPojo.getDocumentTitle().split(" "));
        nqDataObject.setTitleTokens(tokenizer.processTokens(titleTokens));

        List<String> answerTokens = getLongAnswerTokens(naturalQuestionsJsonPojo);
        nqDataObject.setDocumentTokens(answerTokens);

        List<String> questionTokens = naturalQuestionsJsonPojo.getQuestionTokens();
        List<String> filteredQuestionTokens = tokenizer.processTokens(questionTokens.stream());
        nqDataObject.setQuestionTokens(filteredQuestionTokens);

        if (filteredQuestionTokens.size() == 0 || nqDataObject.getTitleTokens().isEmpty()) {
            return null;
        }

        return nqDataObject;
    }

    private List<String> getLongAnswerTokens(NaturalQuestionsJsonPojo naturalQuestionsJsonPojo) {
        List<LongAnswerCandidate> longAnswerCandidates = naturalQuestionsJsonPojo.getLongAnswerCandidates();

        for (LongAnswerCandidate longAnswerCandidate : longAnswerCandidates) {
            List<String> longAnswerTokens = naturalQuestionsJsonPojo.getDocumentTokens().subList(
                    longAnswerCandidate.getStartToken(),
                    longAnswerCandidate.getEndToken()).stream()
                    .filter(naturalQuestionsTokenWithHtml -> !naturalQuestionsTokenWithHtml.isHtmlToken())
                    .map(NaturalQuestionsToken::getToken)
                    .collect(Collectors.toList());

            longAnswerTokens = tokenizer.processTokens(longAnswerTokens);

            if (!longAnswerTokens.isEmpty()) {
                return longAnswerTokens;
            }
        }
        LOG.info(String.format("Empty answer doc returned for %d", naturalQuestionsJsonPojo.getExample_id()));
        return Collections.emptyList();
    }
}

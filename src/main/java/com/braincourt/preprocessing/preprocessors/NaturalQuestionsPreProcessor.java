package com.braincourt.preprocessing.preprocessors;

import com.braincourt.preprocessing.Tokenizer;
import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.dataobjects.LongAnswerCandidate;
import com.braincourt.preprocessing.dataobjects.NaturalQuestionsDataObject;
import com.braincourt.preprocessing.dataobjects.NaturalQuestionsToken;
import com.braincourt.preprocessing.jsonpojos.NaturalQuestionsJsonPojo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class NaturalQuestionsPreProcessor extends PreProcessor {

    public NaturalQuestionsPreProcessor(Tokenizer tokenizer) {
        super(tokenizer);
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
                    .map(this::toDataObject);

        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();

        }

    }

    private DataObject toDataObject(String line) {
        NaturalQuestionsJsonPojo naturalQuestionsJsonPojo = new Gson().fromJson(line, NaturalQuestionsJsonPojo.class);
        NaturalQuestionsDataObject nqDataObject = new NaturalQuestionsDataObject();

        nqDataObject.setDocumentTitle(naturalQuestionsJsonPojo.getDocumentTitle());

        List<NaturalQuestionsToken> tokens = naturalQuestionsJsonPojo.getDocumentTokens();
        nqDataObject.setDocumentTokens(
                tokenizer.filterNaturalQuestionTokens(tokens.stream()
                        .filter(token -> !token.isHtmlToken()))
        );

        List<LongAnswerCandidate> longAnswerCandidates = naturalQuestionsJsonPojo.getLongAnswerCandidates();
        nqDataObject.setLongAnswerCandidates(longAnswerCandidates);

        List<String> questionTokens = naturalQuestionsJsonPojo.getQuestionTokens();
        nqDataObject.setQuestionTokens(tokenizer.filterTokens(questionTokens.stream()));

        return nqDataObject;
    }
}

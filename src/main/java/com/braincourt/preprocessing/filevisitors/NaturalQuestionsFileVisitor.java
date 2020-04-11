package com.braincourt.preprocessing.filevisitors;

import com.braincourt.preprocessing.Tokenizer;
import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.dataobjects.NaturalQuestionsDataObject;
import com.braincourt.preprocessing.dataobjects.NaturalQuestionsToken;
import com.braincourt.preprocessing.dataobjects.NaturalQuestionsTokenWithHtml;
import com.braincourt.preprocessing.jsonpojos.NaturalQuestionsJsonPojo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class NaturalQuestionsFileVisitor extends FileVisitor {

    int currentId = 0;

    public NaturalQuestionsFileVisitor(Tokenizer tokenizer) {
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
                    .map(this::toDataObject)
                    .filter(Objects::nonNull);

        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();

        }

    }

    private DataObject toDataObject(String line) {
        NaturalQuestionsJsonPojo naturalQuestionsJsonPojo = new Gson().fromJson(line, NaturalQuestionsJsonPojo.class);
        NaturalQuestionsDataObject nqDataObject = new NaturalQuestionsDataObject();

        nqDataObject.setId(currentId++);

        nqDataObject.setDocumentTitle(naturalQuestionsJsonPojo.getDocumentTitle());

        List<NaturalQuestionsTokenWithHtml> tokens = naturalQuestionsJsonPojo.getDocumentTokens();
        nqDataObject.setDocumentTokens(
                tokenizer.filterNaturalQuestionTokens(tokens.stream()
                        .filter(token -> !token.isHtmlToken())
                        .map(token -> new NaturalQuestionsToken(token.getStartByte(), token.getEndByte(), token.getToken())))// TODO: consider adding another map-clause where NaturalQuestionsTokenWithHtml is transformed to new object NaturalQuestionsTokenLight that doesn't have the html_token boolean
        );

        List<String> questionTokens = naturalQuestionsJsonPojo.getQuestionTokens();
        List<String> filteredQuestionTokens = tokenizer.filterTokens(questionTokens.stream());
        nqDataObject.setQuestionTokens(filteredQuestionTokens);

        if (filteredQuestionTokens.size() == 0) {
            return null;
        }

        return nqDataObject;
    }
}

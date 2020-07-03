package com.braincourt.preprocessing.filevisitors;

import com.braincourt.preprocessing.Tokenizer;
import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.dataobjects.SquadDataObject;
import com.braincourt.preprocessing.jsonpojos.SquadDataElement;
import com.braincourt.preprocessing.jsonpojos.SquadJson;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Component
public class SquadFileVisitor extends FileVisitor {

    Gson gson = new Gson();

    Map<String, Long> titleIds;

    AtomicLong titleId;

    public SquadFileVisitor(Tokenizer tokenizer) {
        super(tokenizer);
        titleIds = new HashMap<>();
        titleId = new AtomicLong();
    }

    @Override
    public Stream<DataObject> toDataObjects(Stream<Path> dataFilePaths) {
        return dataFilePaths.flatMap(this::toDataObjects);
    }

    private Stream<DataObject> toDataObjects(Path path) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(path.toAbsolutePath().toString()));
            return fileReader.lines().flatMap(this::toDataObjects);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    private Stream<DataObject> toDataObjects(String line) {
        SquadJson squadJson = gson.fromJson(line, SquadJson.class);
        return squadJson.getData().stream().flatMap(this::toDataObjects);
    }

    private Stream<? extends DataObject> toDataObjects(SquadDataElement squadDataElement) {
        String title = squadDataElement.getTitle();
        if (!titleIds.containsKey(title)) {
            titleIds.put(title, titleId.getAndIncrement());
        }
        return squadDataElement.getParagraphs().stream()
                .flatMap(squadParagraph -> squadParagraph.getQas().stream())
                .filter(squadQA -> !squadQA.isIs_impossible())
                .map(squadQA ->
                        new SquadDataObject(
                                squadQA.getId(),
                                titleIds.get(title),
                                tokenizer.processTokens(squadQA.getQuestion()),
                                tokenizer.processTokens(title)))
                .filter(squadDataObject -> !squadDataObject.getTitleTokens().isEmpty())
                .filter(squadDataObject -> !squadDataObject.getQuestionTokens().isEmpty());
    }
}

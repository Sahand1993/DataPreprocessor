package com.braincourt.onehotvectors.writers.file;

import com.braincourt.mysql.entities.SquadEntity;
import com.braincourt.mysql.entities.WikiQAEntity;
import com.braincourt.onehotvectors.entitystreamers.WikiQAEntityStreamer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class WikiQAIndicesFileWriter extends FileWriter<WikiQAEntity> {
    public WikiQAIndicesFileWriter(WikiQAEntityStreamer wikiQAEntityStreamer,
                                   @Value("${wikiQA.folder}") String squadDirName,
                                   @Value("${processed.data.dir}") String preprocessedHome,
                                   @Value("${data.csv}") String csvFile) {
        super(wikiQAEntityStreamer, Paths.get(preprocessedHome,  squadDirName, csvFile).toString(), WikiQAEntity.class);
    }
}

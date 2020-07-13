package com.braincourt.onehotvectors.writers.file;

import com.braincourt.mysql.entities.ConfluenceDocument;
import com.braincourt.onehotvectors.entitystreamers.ConfluenceDocumentStreamer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class ConfluenceFileWriter extends FileWriter<ConfluenceDocument> {
    public ConfluenceFileWriter(
            ConfluenceDocumentStreamer streamer,
            @Value("${processed.data.dir}") String preprocessedHome,
            @Value("${confluence.folder}") String confluenceFolderName,
            @Value("${data.csv}") String csvFileName
            ) {
        super(streamer, Paths.get(preprocessedHome, confluenceFolderName, csvFileName).toAbsolutePath().toString(), ConfluenceDocument.class);
    }
}

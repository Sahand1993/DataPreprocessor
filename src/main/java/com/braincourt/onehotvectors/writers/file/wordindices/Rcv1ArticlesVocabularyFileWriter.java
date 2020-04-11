package com.braincourt.onehotvectors.writers.file.wordindices;

import com.braincourt.mysql.entities.RcvArticles;
import com.braincourt.onehotvectors.entitystreamers.wordindices.Rcv1ArticlesWordIndicesStreamer;
import com.braincourt.onehotvectors.writers.file.FileWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Rcv1ArticlesVocabularyFileWriter extends FileWriter<RcvArticles> {

    public Rcv1ArticlesVocabularyFileWriter(Rcv1ArticlesWordIndicesStreamer rcv1ArticlesWordIndicesStreamer,
                                            @Value("${reuters.home}") String datasetHome,
                                            @Value("${wordIndices.fileName}") String wordIndicesFileName) {
        super(rcv1ArticlesWordIndicesStreamer, datasetHome + wordIndicesFileName, RcvArticles.class);
    }
}

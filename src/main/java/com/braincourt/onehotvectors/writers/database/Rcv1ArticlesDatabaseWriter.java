package com.braincourt.onehotvectors.writers.database;

import com.braincourt.mysql.entities.RcvArticles;
import com.braincourt.mysql.repositories.Rcv1ArticleRepository;
import com.braincourt.onehotvectors.writers.entitystream.Rcv1ArticleStreamer;
import org.springframework.stereotype.Component;

@Component
public class Rcv1ArticlesDatabaseWriter extends DatabaseWriter<RcvArticles> {
    public Rcv1ArticlesDatabaseWriter(
            Rcv1ArticleStreamer rcv1ArticleStreamer,
            Rcv1ArticleRepository rcv1ArticlesRepository) {
        this.streamer = rcv1ArticleStreamer;
        this.repository = rcv1ArticlesRepository;
    }
}

package com.braincourt.onehotvectors.writers.database;

import com.braincourt.mysql.entities.DatabaseEntity;
import com.braincourt.onehotvectors.writers.entitystream.EntityStreamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;

public abstract class DatabaseWriter<T extends DatabaseEntity> {

    Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected String jsonDataPath;

    EntityStreamer<T> streamer;

    CrudRepository<T, Long> repository;

    public void write(Map<String, Map<String, Integer>> wordToNGramToId) {
        LOG.info("Started writing to database");
        streamer.getEntities().forEach(naturalQuestion -> repository.save(naturalQuestion));
    }}

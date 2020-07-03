package com.braincourt.onehotvectors.writers.database;

import com.braincourt.mysql.entities.DatabaseEntity;
import com.braincourt.onehotvectors.entitystreamers.EntityStreamer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SingleTableDatabaseWriter<T extends DatabaseEntity> extends DatabaseWriter<T> {
    public CrudRepository<T, Long> repository;
    public List<EntityStreamer<T>> streamers;
    AtomicInteger i = new AtomicInteger();
    public void write() {
        LOG.info("Started writing to database");
        streamers.stream().flatMap(EntityStreamer::getEntities).forEach(entity -> {
            repository.save(entity);
            if (i.incrementAndGet() % 1000 == 0) {
                LOG.info("Wrote " + i.get() + " entities.");
            }
        });
        LOG.info("Finished writing to database");
    }
}
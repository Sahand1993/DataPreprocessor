package com.braincourt.onehotvectors.writers.file;

import com.braincourt.mysql.entities.DatabaseEntity;
import com.braincourt.onehotvectors.entitystreamers.EntityStreamer;
import com.braincourt.onehotvectors.writers.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class FileWriter<T extends DatabaseEntity> extends Writer {

    private String writePath;
    public EntityStreamer<T> streamer;
    public BufferedWriter bufferedWriter;
    private final Class<T> entityClass;

    @Value("${csv.delimiter}")
    protected String csvDelimiter;

    protected Logger LOG = LoggerFactory.getLogger(this.getClass());
    protected AtomicInteger linesWritten = new AtomicInteger();

    final Field[] fields;

    public FileWriter(EntityStreamer<T> streamer, String writePath, Class<T> entityClass) {
        this.streamer = streamer;
        this.entityClass = entityClass;
        this.fields = getDeclaredFields(entityClass);
        this.writePath = writePath;
    }

    public FileWriter(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.fields = getDeclaredFields(entityClass);
    }

    private Field[] getDeclaredFields(Class<T> entityClass) {
        return entityClass.getDeclaredFields();
    }

    @Override
    public void write() {
        try {
            bufferedWriter = new BufferedWriter(new java.io.FileWriter(writePath));
            bufferedWriter.write(getCsvHeader());
            bufferedWriter.newLine();
            streamer.getEntities().forEach(this::write);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCsvHeader() {
        return Arrays.stream(fields)
                .map(Field::getName)
                .map(name -> {
                    String[] parts = name.split("\\.");
                    return parts[parts.length-1];
                })
                .collect(Collectors.joining(csvDelimiter));
    }

    private void write(T entity) {
        try {
            bufferedWriter.write(getCsvRepresentation(entity));
            bufferedWriter.newLine();
            if (linesWritten.incrementAndGet() % 1000 == 0) {
                LOG.info(String.format("Wrote %d lines.", linesWritten.get()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCsvRepresentation(T entity) {
        return Arrays.stream(fields)
            .map(field -> {
                try {
                    Object fieldValue = field.get(entity);
                    return fieldValue != null ? fieldValue.toString() : "";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    System.exit(1);
                    return null;
                }
            })
            .collect(Collectors.joining(csvDelimiter));
    }


}

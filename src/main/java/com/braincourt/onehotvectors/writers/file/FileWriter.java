package com.braincourt.onehotvectors.writers.file;

import com.braincourt.mysql.entities.DatabaseEntity;
import com.braincourt.onehotvectors.entitystreamers.EntityStreamer;
import com.braincourt.onehotvectors.writers.Writer;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FileWriter<T extends DatabaseEntity> extends Writer {

    public EntityStreamer<T> streamer;
    public BufferedWriter bufferedWriter;
    public String outFilePath;
    private final Class<T> clazz;

    @Value("${csv.delimiter}")
    private String csvDelimiter;

    public FileWriter(EntityStreamer<T> streamer, String outFilePath, Class<T> clazz) {
        this.streamer = streamer;
        this.outFilePath = outFilePath;
        this.clazz = clazz;
    }

    @Override
    public void write() {
        try {
            bufferedWriter = new BufferedWriter(new java.io.FileWriter(outFilePath));
            bufferedWriter.write(getCsvHeader());
            bufferedWriter.newLine();
            streamer.getEntities().forEach(this::write);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCsvRepresentation(T entity) {
        List<String> fields = Arrays.stream(entity.getClass().getDeclaredFields()).map(field -> {
            try {
                return field.get(entity) != null ? field.get(entity).toString() : "";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.exit(1);
                return null;
            }
        }).collect(Collectors.toList());

        return csvDelimiter + String.join(csvDelimiter, fields);
    }

    private String getCsvHeader() {
        Field[] fields = clazz.getDeclaredFields();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

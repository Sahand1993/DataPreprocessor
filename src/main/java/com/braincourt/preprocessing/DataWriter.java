package com.braincourt.preprocessing;

import com.braincourt.preprocessing.dataobjects.DataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

@Component
public class DataWriter {

    private static Logger LOG = LoggerFactory.getLogger(DataWriter.class);

    private int processedFiles = 0;

    private String jsonFile;

    public DataWriter(@Value("${filename.json}") String jsonFileName) {
        this.jsonFile = jsonFileName;
    }

    public void writeObjects(Stream<DataObject> dataObjects, String destinationDir) {
        File outputFolder = new File(destinationDir);

        try {
            deleteContents(outputFolder);

            if (outputFolder.exists() && !outputFolder.delete()) {
                LOG.error("Failed to delete old directory. Exiting...");
                System.exit(1);
            }

            if (outputFolder.mkdir()) {
                String filePath = destinationDir + jsonFile;
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));

                LOG.info(String.format("Beginning processing of %s folder.", outputFolder));

                dataObjects.forEach(dataObject -> { // TODO remove limit after debugging
                    writeTo(bufferedWriter, dataObject);
                    if (++processedFiles % 1000 == 0) {
                        LOG.info(String.format("wrote %d lines.", processedFiles));
                    }
                });
                LOG.info(String.format("Finished writing to %s. Lines written: %d.", filePath, processedFiles));
                bufferedWriter.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTo(BufferedWriter bw, DataObject dataObject) {
        try {

            bw.write(dataObject.toJsonString());
            bw.newLine();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    private void deleteContents(File outputFolder) {
        if (outputFolder.isDirectory()) {
            File[] contents = outputFolder.listFiles();
            if (contents != null && contents.length > 0) {
                for (File file : contents) {
                    file.delete();
                }
            }
        }
    }
}

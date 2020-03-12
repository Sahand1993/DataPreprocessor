package com.braincourt.preprocessing;

import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.dataobjects.ReutersDataObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

public class DataWriter {

    private final String writePath;

    private int processedFiles = 0;

    public DataWriter(String writePath) {
        this.writePath = writePath;
    }

    public void writeObjects(Stream<DataObject> dataObjects, String relativePath) {
        String writeFolderPath = System.getenv("THESIS_PROCESSED_DATA_DIR");
        File outputFolder = new File(writeFolderPath + relativePath);

        try {
            if (outputFolder.isDirectory()) {
                File[] contents = outputFolder.listFiles();
                for (int i = 0; i < contents.length; i++) {
                    File file = contents[i];
                    file.delete();

                }
            }

            if (outputFolder.exists() && !outputFolder.delete()) {
                System.err.println("Failed to delete old directory. Exiting...");
                System.exit(1);

            }

            if (outputFolder.mkdir()) {
                String filePath = outputFolder.getAbsolutePath() + "/data.json";
                FileWriter writer = new FileWriter(filePath);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                System.out.println(String.format("Beginning processing of %s folder.", outputFolder));

                dataObjects.forEach(dataObject -> {
                    try {
                        bufferedWriter.write(dataObject.toJsonString());
                        bufferedWriter.newLine();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    if (++processedFiles % 1000 == 0) {
                        System.out.println(String.format("wrote %d lines.", processedFiles));
                    }


                });
                System.out.println(String.format("Finished writing to %s. Lines written: %d.", filePath, processedFiles));
                bufferedWriter.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

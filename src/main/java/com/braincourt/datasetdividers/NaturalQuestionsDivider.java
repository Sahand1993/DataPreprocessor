package com.braincourt.datasetdividers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class NaturalQuestionsDivider extends Divider {

    private final int testPercentage;
    private final String testFilePath;

    public NaturalQuestionsDivider(@Value("${processed.data.dir}") String preprocessedHome,
                                   @Value("${filename.json}") String jsonFileName,
                                   @Value("${reuters.validationPercentage}") int validationPercentage,
                                   @Value("${naturalQuestions.testPercentage}") int testPercentage,
                                   @Value("${validationFileName.json}") String validationFileName,
                                   @Value("${trainingFileName.json}") String trainingFileName,
                                   @Value("${testFileName.json}") String testFileName) {
        String nqHome = preprocessedHome + "nq/";
        this.fileToDividePath = nqHome + jsonFileName;
        this.trainingFilePath = nqHome + trainingFileName;
        this.validationFilePath = nqHome + validationFileName;
        this.testFilePath = nqHome + testFileName;
        this.validationPercentage = validationPercentage;
        this.testPercentage = testPercentage;
    }

    @Override
    public void divide() {
        LOG.info("Started dividing dataset");

        int linesOfValidationSet = getNoOfLines(fileToDividePath, validationPercentage);
        int linesOfTestSet = getNoOfLines(fileToDividePath, testPercentage);
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToDividePath));
            writeNextNLinesOf(bufferedReader, validationFilePath, linesOfValidationSet);
            writeNextNLinesOf(bufferedReader, testFilePath, linesOfTestSet);
            writeNextNLinesOf(bufferedReader, trainingFilePath, -1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        LOG.info("Finished dividing dataset");
    }

    /**
     * Writes the next n lines of bufferedReader to validationFilePath. Overwrites if file already exists.
     * @param bufferedReader The reader from which n lines will be copied.
     * @param validationFilePath The path to write n lines to.
     * @param n The number of lines to write. If n is negative, write rest of bufferedReader to validationFilePath.
     */
    private void writeNextNLinesOf(BufferedReader bufferedReader, String validationFilePath, int n) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(validationFilePath));
            if (n > -1) {
                for (int i = 0; i < n; i++) {
                    bufferedWriter.write(bufferedReader.readLine() + "\n");
                }
            }
            else {
                String nextLine;
                while ((nextLine = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(nextLine + "\n");
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

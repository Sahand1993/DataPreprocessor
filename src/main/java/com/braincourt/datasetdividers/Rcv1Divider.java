package com.braincourt.datasetdividers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class Rcv1Divider extends Divider {

    public Rcv1Divider(@Value("${processed.data.dir}") String preprocessedHome,
                       @Value("${filename.json}") String jsonFileName,
                       @Value("${reuters.validationPercentage}") int validationPercentage,
                       @Value("${validationFileName.json}") String validationFileName,
                       @Value("${trainingFileName.json}") String trainingFileName) {
        String rcv1Home = preprocessedHome + "rcv1/";
        this.fileToDividePath = rcv1Home + jsonFileName;
        this.validationPercentage = validationPercentage;
        this.validationFilePath = rcv1Home + validationFileName;
        this.trainingFilePath = rcv1Home + trainingFileName;
    }

    @Override
    public void divide() {
        LOG.info("Started diving dataset");

        int linesOfValidationSet = getNoOfLines(fileToDividePath, validationPercentage);
        List<String> shuffledList = getShuffledLines(fileToDividePath);

        List<String> validationSet = shuffledList.subList(0, linesOfValidationSet);
        List<String> trainingSet = shuffledList.subList(linesOfValidationSet, shuffledList.size());

        writeTo(validationFilePath, validationSet);
        writeTo(trainingFilePath, trainingSet);

        LOG.info("Finished dividing dataset");
    }
}

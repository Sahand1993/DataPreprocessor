package com.braincourt.datasetdividers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DuplicateQuestionsDivider extends Divider {

    public DuplicateQuestionsDivider(@Value("${processed.data.dir}") String preprocessedHome,
                                     @Value("${filename.json}") String fileToDivideName,
                                     @Value("${quora.validationPercentage}") int validationPercentage,
                                     @Value("${validationFileName.json}") String validationFileName,
                                     @Value("${trainingFileName.json}") String trainingFileName) {
        String quoraHome = preprocessedHome + "quora/";
        this.fileToDividePath = quoraHome + fileToDivideName;
        this.validationPercentage = validationPercentage;
        this.validationFilePath = quoraHome + validationFileName;
        this.trainingFilePath = quoraHome + trainingFileName;
    }

    @Override
    public void divide() {
        LOG.info("Started dividing dataset");

        int linesOfValidationSet = getNoOfLines(fileToDividePath, validationPercentage);
        List<String> shuffledList = getShuffledLines(fileToDividePath);

        List<String> validationSet = shuffledList.subList(0, linesOfValidationSet);
        List<String> trainingSet = shuffledList.subList(linesOfValidationSet, shuffledList.size());

        writeTo(validationFilePath, validationSet);
        writeTo(trainingFilePath, trainingSet);

//        divideOtherFile("/Users/sahandzarrinkoub/School/year5/thesis/DSSM/preprocessed_datasets/quora/data.json");

        LOG.info("Finished dividing dataset");
    }

//    private void divideOtherFile(String path) {
//        int linesOfValidationSet = getNoOfLines(fileToDividePath, validationPercentage);
//        List<String> shuffledList = getShuffledLines(path);
//
//        List<String> validationSet = shuffledList.subList(0, linesOfValidationSet);
//        List<String> trainingSet = shuffledList.subList(linesOfValidationSet, shuffledList.size());
//
//        writeTo("/Users/sahandzarrinkoub/School/year5/thesis/DSSM/preprocessed_datasets/quora/validation.json", validationSet);
//        writeTo("/Users/sahandzarrinkoub/School/year5/thesis/DSSM/preprocessed_datasets/quora/train.json", trainingSet);
//    }
}

package com.braincourt.preprocessing;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Processing {

    private final List<PreprocessingStep> PREPROCESSING_STEPS;

    public Processing(
            PreprocessWikiQA preprocessWikiQA,
            PreprocessSquad preprocessSquad,
            PreprocessRcv1 preprocessRcv1,
            PreprocessNaturalQuestions preprocessNaturalQuestions,
            PreprocessQuora preprocessQuora
    ) {
        PREPROCESSING_STEPS = Arrays.asList(preprocessWikiQA, preprocessSquad, preprocessNaturalQuestions, preprocessRcv1, preprocessQuora);
    }

    public void preprocessDatasets() {
        for (PreprocessingStep preprocessingStep : PREPROCESSING_STEPS) {
            preprocessingStep.preprocess();
        }
    }
}

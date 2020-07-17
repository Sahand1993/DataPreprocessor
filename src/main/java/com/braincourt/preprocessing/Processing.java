package com.braincourt.preprocessing;

import com.braincourt.preprocessing.preprocessors.ConfluencePreprocessor;
import com.braincourt.preprocessing.preprocessors.NaturalQuestionsPreprocessor;
import com.braincourt.preprocessing.preprocessors.Preprocessor;
import com.braincourt.preprocessing.preprocessors.QuoraPreprocessor;
import com.braincourt.preprocessing.preprocessors.Rcv1Preprocessor;
import com.braincourt.preprocessing.preprocessors.SquadPreprocessor;
import com.braincourt.preprocessing.preprocessors.WikiQAPreprocessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Processing {

    private final List<Preprocessor> PREPROCESSING_STEPS;

    public Processing(
            ConfluencePreprocessor confluencePreprocessor
//            WikiQAPreprocessor wikiQAPreprocessor,
//            SquadPreprocessor squadPreprocessor,
//            Rcv1Preprocessor rcv1Preprocessor,
//            NaturalQuestionsPreprocessor naturalQuestionsPreprocessor,
//            QuoraPreprocessor quoraPreprocessor
    ) {
        PREPROCESSING_STEPS = Arrays.asList(
                confluencePreprocessor
//                wikiQAPreprocessor,
//                squadPreprocessor,
//                naturalQuestionsPreprocessor,
//                rcv1Preprocessor,
//                quoraPreprocessor
        );
    }

    public void preprocessDatasets() {
        for (Preprocessor fileSystemPreprocessor : PREPROCESSING_STEPS) {
            fileSystemPreprocessor.preprocess();
        }
    }
}

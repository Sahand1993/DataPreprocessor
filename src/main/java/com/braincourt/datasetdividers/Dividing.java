package com.braincourt.datasetdividers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Dividing {
    private final List<Divider> DIVIDERS;

    public Dividing(Rcv1Divider rcv1Divider,
                    NaturalQuestionsDivider naturalQuestionsDivider,
                    DuplicateQuestionsDivider duplicateQuestionsDivider) {
        DIVIDERS = new ArrayList<>();
        DIVIDERS.addAll(
                Arrays.asList(
                        rcv1Divider,
                        naturalQuestionsDivider,
                        duplicateQuestionsDivider));
    }

    public void divideAll() {
        DIVIDERS.forEach(Divider::divide);
    }
}

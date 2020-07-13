package com.braincourt.preprocessing.preprocessors;

import com.braincourt.preprocessing.DataWriter;

public abstract class Preprocessor {

    DataWriter dataWriter;
    String preprocessedDir;

    public Preprocessor(DataWriter dataWriter) {
        this.dataWriter = dataWriter;
    }

    public abstract void preprocess();
}

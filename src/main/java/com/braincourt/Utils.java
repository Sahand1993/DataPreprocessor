package com.braincourt;

import static com.braincourt.Constants.N_GRAM_LENGTH;

public class Utils {

    public static String getNqOneHotPath() {
        return System.getProperty("nq.home") + getOnehotCsvFileName();
    }

    public static String getOnehotCsvFileName() {
        return String.format("onehot_%sgram.csv", N_GRAM_LENGTH);
    }

    public static String getNqOneHotSampledCsvPath() {
        return System.getProperty("nq.home") + getOnehotSampledCsvFileName();
    }

    public static String getOnehotSampledCsvFileName() {
        return String.format("onehot_%sgram.sampled.csv", N_GRAM_LENGTH);
    }
}

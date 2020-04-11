package com.braincourt;

import java.util.regex.Pattern;

public class Constants {

    public static int N_GRAM_LENGTH = 3;
    public static int NUMBER_OF_IRRELEVANT_DOCS = 4;

    public static String JSON_FILE = "data.json";

    public static String PREPROCESSED_DATA_PATH = System.getenv("THESIS_PROCESSED_DATA_DIR");

    public static String PREPROCESSED_QUORA = PREPROCESSED_DATA_PATH + "quora/";
    public static String PREPROCESSED_QUORA_DATA = PREPROCESSED_DATA_PATH + "quora/data.json";

    public static String PREPROCESSED_NQ = PREPROCESSED_DATA_PATH + "nq/";
    public static String PREPROCESSED_NQ_DATA = PREPROCESSED_DATA_PATH + "nq/data.json";

    public static String PREPROCESSED_REUTERS = PREPROCESSED_DATA_PATH + "rcv1/";
    public static String PREPROCESSED_REUTERS_DATA = PREPROCESSED_DATA_PATH + "rcv1/data.json";

    public static Pattern HAS_NUMBERS = Pattern.compile(".*\\d.*");

}

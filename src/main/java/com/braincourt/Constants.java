package com.braincourt;

public class Constants {
    public static String PREPROCESSED_DATA_PATH = System.getenv("THESIS_PROCESSED_DATA_DIR");

    public static String PREPROCESSED_QUORA = PREPROCESSED_DATA_PATH + "quora/";
    public static String PREPROCESSED_QUORA_DATA = PREPROCESSED_DATA_PATH + "quora/data.json";

    public static String PREPROCESSED_NQ = PREPROCESSED_DATA_PATH + "nq/";
    public static String PREPROCESSED_NQ_DATA = PREPROCESSED_DATA_PATH + "nq/data.json";

    public static String PREPROCESSED_REUTERS = PREPROCESSED_DATA_PATH + "rcv1/";
    public static String PREPROCESSED_REUTERS_DATA = PREPROCESSED_DATA_PATH + "rcv1/data.json";
}
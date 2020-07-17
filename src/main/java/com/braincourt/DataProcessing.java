package com.braincourt;

import com.braincourt.datasetdividers.Dividing;
import com.braincourt.onehotvectors.OneHotNgramVectors;
import com.braincourt.onehotvectors.writers.database.Rcv1ArticlesDatabaseWriter;
import com.braincourt.onehotvectors.writers.file.Rcv1ArticlesIndicesFileWriter;
import com.braincourt.preprocessing.Processing;
import com.braincourt.vocabularyextraction.VocabularyExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
})
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=Rcv1ArticlesDatabaseWriter.class),
        @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=Rcv1ArticlesIndicesFileWriter.class),
        @ComponentScan.Filter(type=FilterType.REGEX, pattern=".*mysql.*"),
}
)
public class DataProcessing implements CommandLineRunner {

    OneHotNgramVectors oneHotNgramVectors;
    VocabularyExtractor vocabularyExtractor;
    Processing processing;

    public DataProcessing(OneHotNgramVectors oneHotNgramVectors,
                          VocabularyExtractor vocabularyExtractor,
                          Processing processing,
                          Dividing dividing) {
        this.oneHotNgramVectors = oneHotNgramVectors;
        this.vocabularyExtractor = vocabularyExtractor;
        this.processing = processing;
    }

    private static Logger LOG = LoggerFactory
            .getLogger(DataProcessing.class);

    public static void main(String[] args) {
        SpringApplication.run(DataProcessing.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        processing.preprocessDatasets();

        vocabularyExtractor.createVocabulary();

        oneHotNgramVectors.writeOneHotDatasets();
    }
}
package com.braincourt.onehotvectors.writers.file;

import com.braincourt.mysql.entities.SquadEntity;
import com.braincourt.onehotvectors.entitystreamers.SquadEntityStreamer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class SquadIndicesFileWriter extends FileWriter<SquadEntity> {
    public SquadIndicesFileWriter(SquadEntityStreamer squadEntityStreamer,
                                  @Value("${squad.folder}") String squadDirName,
                                  @Value("${processed.data.dir}") String preprocessedHome,
                                  @Value("${data.csv}") String csvFile) {
        super(squadEntityStreamer, Paths.get(preprocessedHome,  squadDirName, csvFile).toString(), SquadEntity.class);
    }
}

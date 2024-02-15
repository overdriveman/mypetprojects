package su.wrath.formatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import su.wrath.bean.MusicTrack;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JsonFormatter implements Formatter {

    private final ObjectMapper jsonMapper = new ObjectMapper().registerModule(new JavaTimeModule()).enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public void format(List<MusicTrack> tracks, CommandLine cmd) {
        if (cmd.hasOption("json")) {
            try {
                jsonMapper.writeValue(new File(cmd.getOptionValue("json")), tracks);
            } catch (IOException e) {
                log.warn("Ошибка в JsonFormatter: ", e);
            }
        }
    }
}

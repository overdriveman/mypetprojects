package su.wrath;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import su.wrath.bean.MusicTrack;
import su.wrath.formatter.Formatter;
import su.wrath.service.FormatSelector;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static su.wrath.config.ShazamCliOptions.buildOptions;

@Slf4j
public class TestHelpers {

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd");

    @SneakyThrows
    public static CommandLine prepareCommandLine(String... args) {
        Options options = buildOptions();
        CommandLineParser parser = new DefaultParser();

        return parser.parse(options, args);
    }

    public static Path getPathToTestFile(String filename) {
        return Paths.get("src", "test", "resources", filename);
    }

    public static void assertTrackAuthorEquals(String expected, MusicTrack track) {
        assertEquals(expected, track.getArtist(), "Автор трека");
    }

    public static void assertTrackTitleEquals(String expected, MusicTrack track) {
        assertEquals(expected, track.getTitle(), "Название трека");
    }

    public static void assertTrackDateEquals(String expected, MusicTrack track) {
        LocalDate expectedDate = LocalDate.parse(expected, FORMATTER);
        assertEquals(expectedDate, track.getDate(), "Дата трека");
    }

    public static void assertContainsSelector(CommandLine cmd, Class<? extends Formatter> clazz) {
        FormatSelector selector = new FormatSelector();
        String message = "Должен содержать " + clazz.toString();
        assertTrue(selector.chooseFormatters(cmd).stream().anyMatch(formatter -> clazz.equals(formatter.getClass())), message);
    }
}

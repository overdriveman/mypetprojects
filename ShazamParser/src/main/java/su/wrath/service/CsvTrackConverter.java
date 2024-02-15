package su.wrath.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Marker;
import su.wrath.bean.MusicTrack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static su.wrath.service.InputValidator.*;
import static su.wrath.config.DateConstants.*;

@Slf4j
public class CsvTrackConverter {

    public List<MusicTrack> parseFile(CommandLine cmd) {
        Optional<String> optionalFilename = getInputFileName(cmd);

        if (optionalFilename.isPresent()) {
            return parseInputFile(optionalFilename.get(), cmd);
        } else {
            return Collections.emptyList();
        }
    }

    protected List<MusicTrack> parseInputFile(String fileName, CommandLine cmd) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            return new CsvToBeanBuilder<MusicTrack>(reader)
                    .withType(MusicTrack.class)
                    .withSkipLines(2)
                    .build()
                    .stream()
                    .sequential()
                    .distinct()
                    .filter(getFilter(cmd))
                    .sorted(getComparator(cmd))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Ошибка при чтении файла:", e);
            return Collections.emptyList();
        }
    }

    Comparator<MusicTrack> getComparator(CommandLine cmd) {

        if (cmd.hasOption("sort-by-artist")) {
            return Comparator.comparing(MusicTrack::getArtist);
        } else if (cmd.hasOption("sort-by-title")) {
            return Comparator.comparing(MusicTrack::getTitle);
        } else if (cmd.hasOption("sort-by-date")) {
            return Comparator.comparing(MusicTrack::getDate);
        } else {
            return Comparator.comparing(MusicTrack::getIndexNumber);
        }
    }

    Predicate<MusicTrack> getFilter(CommandLine cmd) {
        if (cmd.hasOption("filter-date-after")) {
            String userInputDate = cmd.getOptionValue("filter-date-after");
            log.debug("Дата указанная в опции: '{}'", userInputDate);
            try {
                LocalDate inputDate = LocalDate.parse(userInputDate, FILTER_FORMATTER);
                return musicTrack -> musicTrack.getDate().isAfter(inputDate);
            } catch ( DateTimeParseException exception) {
                log.error("Не смогли обработать формат даты: '{}', результат не будет отфильтрован!", userInputDate, exception);
                return musicTrack -> false;
            }
        }
        return musicTrack -> true;
    }
}

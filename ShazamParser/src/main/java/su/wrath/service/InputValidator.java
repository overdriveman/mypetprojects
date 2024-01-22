package su.wrath.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;

import static su.wrath.config.ShazamFileConstants.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Slf4j
public class InputValidator {
    public boolean validateInputArgs(CommandLine cmd) {
        if (checkInputFile(cmd)) {
            return checkOutputFileNotPresent(cmd) &&
                    checkXmlFileNotPresent(cmd) &&
                    checkJsonFileNotPresent(cmd);
        }

        return false;
    }

    private boolean checkOutputFileNotPresent(CommandLine cmd) {
        return isOptionCorrect(cmd, "target", "Не указан выходной файл!");
    }

    private boolean checkXmlFileNotPresent(CommandLine cmd) {
        return isOptionCorrect(cmd, "xml", "Не указан xml файл!");
    }

    private boolean checkJsonFileNotPresent(CommandLine cmd) {
        return isOptionCorrect(cmd, "json", "Не указан json файл!");
    }

    private boolean isOptionCorrect(CommandLine cmd, String optionName, String errorMessage) {
        if (cmd.hasOption(optionName) && isOptionValueIncorrect(cmd.getOptionValue(optionName), errorMessage)) {
            return false;
        }

        return true;
    }

    private boolean isOptionValueIncorrect(String filename, String errorMessage) {
        if (filename == null || filename.isEmpty()) {
            log.error(errorMessage);
            return true;
        }

        if (Files.exists(Paths.get(filename))) {
            log.error("Такой файл уже существует: '{}'", filename);
            return true;
        }

        return false;
    }

    private boolean checkInputFile(CommandLine cmd) {
        if (cmd.getArgList().size() == 0) {
            log.debug("Входной файл не задан!");
            return false;
        }

        return getInputFileName(cmd).isPresent();
    }

    public static boolean checkIfInputFileIsShazam(Path path) {
        if (Files.exists(path) &&
                Files.isReadable(path)) {
            try {
                return isValidShazamFormat(Files.readAllLines(path));
            } catch (IOException e) {
                log.error("Ошибка при валидации файла:", e);
            }
        }

        return false;
    }

    private static boolean isValidShazamFormat(List<String> fileLines) {
        if (fileLines.size() < 3) {
            log.debug("Входной файл слишком короткий: '{}'", fileLines.size());
            return false;
        } else if (!SHAZAM_HEADER.equals(fileLines.get(0))) {
            log.debug("Не известный формат, первая строка: '{}'", fileLines.get(0));
            return false;
        } else if (!SHAZAM_COLUMNS.equals(fileLines.get(1))) {
            log.debug("Не известный формат, вторая строка: '{}'", fileLines.get(1));
            return false;
        } else {
            validateAllLines(fileLines);
        }

        return true;
    }

    private static void validateAllLines(List<String> fileLines) {
        for (int i = 2; i < fileLines.size(); i++) {
            String currentLine = fileLines.get(i);

            if (!currentLine.matches(SHAZAM_TRACK_REGEX)) {
                log.warn("Не известный формат строки '{}'", currentLine);
            }
        }
    }

    public static Optional<String> getInputFileName(CommandLine cmd) {
        List<String> allArgs = cmd.getArgList();

        for (String filename : allArgs) {
            Path inputPath = Paths.get(filename);
            if (checkIfInputFileIsShazam(inputPath)) {
                return Optional.of(filename);
            }
        }

        log.debug("Не нашел входного файла!");
        return Optional.empty();
    }
}

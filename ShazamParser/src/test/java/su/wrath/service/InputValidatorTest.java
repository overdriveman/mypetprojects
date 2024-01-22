package su.wrath.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static su.wrath.TestHelpers.getPathToTestFile;
import static su.wrath.TestHelpers.prepareCommandLine;

@Slf4j
public class InputValidatorTest {
    InputValidator inputValidator = new InputValidator();

    @Test
    public void validateInputArgsNotExistingFile() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("inputNotExist.csv").toString());

        assertFalse(inputValidator.validateInputArgs(cmd), "Не существующий файл валидацию не проходит");
    }

    @Test
    public void validateInputArgsExistingFileNotShazam() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestConsoleOpts").toString());

        assertFalse(inputValidator.validateInputArgs(cmd), "Файл с неверным форматом 1ой валидацию не проходит");
    }

    @Test
    public void validateInputArgsExistingFileNotShazam2ndLine() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestConsoleOpts1").toString());

        assertFalse(inputValidator.validateInputArgs(cmd), "Файл с неверным форматом 2ой строки валидацию не проходит");
    }

    @Test
    public void validateInputArgsExistingFileNotShazam3rdLine() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestConsoleOpts2").toString());

        assertTrue(inputValidator.validateInputArgs(cmd), "После заголовка формат не важен");
    }

    @Test
    public void validateInputArgsExistingFileShazam() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString());

        assertTrue(inputValidator.validateInputArgs(cmd), "ShazTestInput.csv валидный файл");
    }

    @Test
    public void validateArgsWithTargetAlreadyExist() {
        CommandLine cmd = prepareCommandLine("-t", getPathToTestFile("csv_output").toString(), "-d", getPathToTestFile("ShazTestInput.csv").toString());

        assertFalse(inputValidator.validateInputArgs(cmd), "Не пишем в уже существующий plaintext файл");
    }

    @Test
    public void validateArgsWithTarget() {
        CommandLine cmd = prepareCommandLine("-t", getPathToTestFile("csv_outputNotExist").toString(), "-d", getPathToTestFile("ShazTestInput.csv").toString());

        assertTrue(inputValidator.validateInputArgs(cmd), "Можем писать в не существующий plaintext файл");
    }

    @Test
    public void validateArgsWithJsonAlreadyExist() {
        CommandLine cmd = prepareCommandLine("-j", getPathToTestFile("json_output").toString(), "-d", getPathToTestFile("ShazTestInput.csv").toString());

        assertFalse(inputValidator.validateInputArgs(cmd), "Не пишем в уже существующий json файл");
    }

    @Test
    public void validateArgsWithJson() {
        CommandLine cmd = prepareCommandLine("-j", getPathToTestFile("json_outputNotExist").toString(), "-d", getPathToTestFile("ShazTestInput.csv").toString());

        assertTrue(inputValidator.validateInputArgs(cmd), "Можем писать в не существующий json файл");
    }

    @Test
    public void validateArgsWithXmlAlreadyExist() {
        CommandLine cmd = prepareCommandLine("-x", getPathToTestFile("xml_output").toString(), "-d", getPathToTestFile("ShazTestInput.csv").toString());

        assertFalse(inputValidator.validateInputArgs(cmd), "Не пишем в уже существующий xml файл");
    }

    @Test
    public void validateArgsWithXml() {
        CommandLine cmd = prepareCommandLine("-x", getPathToTestFile("xml_outputNotExist").toString(), "-d", getPathToTestFile("ShazTestInput.csv").toString());

        assertTrue(inputValidator.validateInputArgs(cmd), "Можем писать в не существующий xml файл");
    }

    @Test
    public void checkIfInputFileIsShazamWithShazamFile() {
        Path filePath = getPathToTestFile("ShazTestInput.csv");

        assertTrue(InputValidator.checkIfInputFileIsShazam(filePath), "Валидный шазам файл проходит проверку");
    }

    @Test
    public void checkIfInputFileIsShazamWithNotShazamFile() {
        Path filePath = getPathToTestFile("ShazTestConsoleOpts");

        assertFalse(InputValidator.checkIfInputFileIsShazam(filePath), "Не шазам файл не проходит проверку");
    }

    @Test
    public void checkInputValidatorParseInputFromAnyPosition() {
        Path filePath = getPathToTestFile("ShazTestInput.csv");

        CommandLine cmd = prepareCommandLine("-df", "-j", "jout", "-x", "xout", "-t", "tout", filePath.toString());
        assertTrue(inputValidator.validateInputArgs(cmd), "Входной файл может быть указан последним");

        cmd = prepareCommandLine(filePath.toString(), "-df", "-j", "jout", "-x", "xout", "-t", "tout");
        assertTrue(inputValidator.validateInputArgs(cmd), "Входной файл может быть указан первым");

        cmd = prepareCommandLine("-df", "-j", "jout", filePath.toString(), "-x", "xout", "-t", "tout");
        assertTrue(inputValidator.validateInputArgs(cmd), "Входной файл может быть указан в середине ключей");

        cmd = prepareCommandLine("-df", "-j", filePath.toString(), "jout", "-x", "xout", "-t", "tout");
        assertFalse(inputValidator.validateInputArgs(cmd), "Входной файл не может быть указан вместо выходного как ключ");

    }
}

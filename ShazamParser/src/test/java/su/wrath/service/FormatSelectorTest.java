package su.wrath.service;

import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.Test;
import su.wrath.formatter.ConsoleFormatter;
import su.wrath.formatter.JsonFormatter;
import su.wrath.formatter.TextFormatter;
import su.wrath.formatter.XmlFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static su.wrath.TestHelpers.*;

public class FormatSelectorTest {

    FormatSelector selector = new FormatSelector();

    @Test
    public void checkSelectorWithInputFileOnly() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString());

        assertEquals(1, selector.chooseFormatters(cmd).size(), "Должен быть один форматер");
        assertContainsSelector(cmd, ConsoleFormatter.class);
    }

    @Test
    public void checkSelectorWithCorrectInputFormattingOptions() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-j", "output.txt");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть два форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, JsonFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-x", "output.txt");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть два форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, XmlFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-t", "output.txt");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть два форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, TextFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-j", "output.json", "-x", "output.xml");

        assertEquals(3, selector.chooseFormatters(cmd).size(), "Должно быть три форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, JsonFormatter.class);
        assertContainsSelector(cmd, XmlFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-j", "output.json", "-x", "output.xml", "-t", "output.txt");

        assertEquals(4, selector.chooseFormatters(cmd).size(), "Должно быть четыре форматера");

        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, JsonFormatter.class);
        assertContainsSelector(cmd, XmlFormatter.class);
        assertContainsSelector(cmd, TextFormatter.class);
    }

    @Test
    public void validateArgsWithDateWithoutOutputOptions() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-d");

        assertEquals(1, selector.chooseFormatters(cmd).size(), "Должен быть один форматер");
        assertContainsSelector(cmd, ConsoleFormatter.class);
    }

    @Test
    public void validateArgsWithFormatWithoutOutputOptions() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-f");

        assertEquals(1, selector.chooseFormatters(cmd).size(), "Должен быть один форматер");
        assertContainsSelector(cmd, ConsoleFormatter.class);
    }

    @Test
    public void validateArgsWithDateFormatWithoutOutputOptions() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-df");

        assertEquals(1, selector.chooseFormatters(cmd).size(), "Должен быть один форматер");
        assertContainsSelector(cmd, ConsoleFormatter.class);
    }

    @Test
    public void validateArgsWithDateAndOutputOptions() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-d");

        assertEquals(1, selector.chooseFormatters(cmd).size(), "Должен быть 1 форматер");
        assertContainsSelector(cmd, ConsoleFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-d", "-j", "output.json");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть 2 форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, JsonFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-d", "-x", "output.xml");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть 2 форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, XmlFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-d", "-t", "output.txt");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть 2 форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, TextFormatter.class);
    }

    @Test
    public void validateArgsWithFormatAndOutputOptions() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-f");

        assertEquals(1, selector.chooseFormatters(cmd).size(), "Должен быть 1 форматер");
        assertContainsSelector(cmd, ConsoleFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-f", "-j", "output.json");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть 2 форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, JsonFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-f", "-x", "output.xml");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть 2 форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, XmlFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-f", "-t", "output.txt");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть 2 форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, TextFormatter.class);
    }

    @Test
    public void validateArgsWithFormatDateAndOutputOptions() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-df");

        assertEquals(1, selector.chooseFormatters(cmd).size(), "Должен быть 1 форматер");
        assertContainsSelector(cmd, ConsoleFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-df", "-j", "output.json");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть 2 форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, JsonFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-df", "-x", "output.xml");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть 2 форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, XmlFormatter.class);

        cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-df", "-t", "output.txt");

        assertEquals(2, selector.chooseFormatters(cmd).size(), "Должно быть 2 форматера");
        assertContainsSelector(cmd, ConsoleFormatter.class);
        assertContainsSelector(cmd, TextFormatter.class);
    }
}

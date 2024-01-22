package su.wrath.formatter;

import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.Test;
import su.wrath.bean.TextFormatterTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static su.wrath.TestHelpers.getPathToTestFile;
import static su.wrath.TestHelpers.prepareCommandLine;

public class ConsoleOutputTest {

    ConsoleFormatter testFormatter = new ConsoleFormatter();

    @Test
    void templateComposerWithoutDateWithoutFormat(){
        CommandLine cmd = prepareCommandLine(getPathToTestFile("inputFile.csv").toString());
        TextFormatterTemplate template = testFormatter.textOutputTemplateComposer(cmd);

        assertEquals("%s - %s%n",template.formatString);
    }

    @Test
    void templateComposerWithDateWithoutFormat(){
        CommandLine cmd = prepareCommandLine(getPathToTestFile("inputFile.csv").toString(),"-d");
        TextFormatterTemplate template = testFormatter.textOutputTemplateComposer(cmd);

        assertEquals("%s - %s - %s%n",template.formatString);
    }

    @Test
    void templateComposerWithoutDateWithFormat(){
        CommandLine cmd = prepareCommandLine(getPathToTestFile("inputFile.csv").toString(),"-f");
        TextFormatterTemplate template = testFormatter.textOutputTemplateComposer(cmd);

        assertEquals("%-28.28s %s%n",template.formatString);
    }

    @Test
    void templateComposerWithDateWithFormat(){
        CommandLine cmd = prepareCommandLine(getPathToTestFile("inputFile.csv").toString(),"-f","-d");
        TextFormatterTemplate template = testFormatter.textOutputTemplateComposer(cmd);

        assertEquals("%-28.28s %-28.28s %s%n",template.formatString);
    }
}

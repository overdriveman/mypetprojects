package su.wrath;

import org.apache.commons.cli.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static su.wrath.TestHelpers.prepareCommandLine;

class LauncherTest {

    private final String[] jsonKeys = {"-j", "output.json"};
    private final String[] jsonKeysFull = {"--json", "output.json"};
    private final String[] xmlKeys = {"-x", "output.xml"};
    private final String[] xmlKeysFull = {"--xml", "output.xml"};
    private final String[] txtKeys = {"-t", "output.txt"};
    private final String[] txtKeysFull = {"--target", "output.txt"};
    private final String[] allKeys = {"-dfans", "-j", "output.json", "-x", "output.xml", "-t", "output.txt", "input.csv"};

    @Test
    void testHelpOption() {
        CommandLine cmd = prepareCommandLine("-h");
        Assertions.assertTrue(cmd.hasOption("help"), "Help option should present");

        cmd = prepareCommandLine("--help");
        Assertions.assertTrue(cmd.hasOption("help"), "Help option should present");

        cmd = prepareCommandLine("-d");
        Assertions.assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine("-f");
        Assertions.assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine("-a");
        Assertions.assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine("-n");
        Assertions.assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine("-s");
        Assertions.assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine("inputFileName.csv");
        Assertions.assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine(jsonKeys);
        Assertions.assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine(xmlKeys);
        Assertions.assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine(txtKeys);
        Assertions.assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine(allKeys);
        Assertions.assertFalse(cmd.hasOption("help"), "Help option should not present");
    }

    @Test
    void testDateOption() {
        CommandLine cmd = prepareCommandLine("-d");
        Assertions.assertTrue(cmd.hasOption("date"), "Date option should present");

        cmd = prepareCommandLine("--date");
        Assertions.assertTrue(cmd.hasOption("date"), "Date option should present");

        cmd = prepareCommandLine(allKeys);
        Assertions.assertTrue(cmd.hasOption("date"), "Date option should present");

    }

    @Test
    void testFormatOption() {
        CommandLine cmd = prepareCommandLine("-f");
        Assertions.assertTrue(cmd.hasOption("format"), "Format option should present");

        cmd = prepareCommandLine("--format");
        Assertions.assertTrue(cmd.hasOption("format"), "Format option should present");

        cmd = prepareCommandLine(allKeys);
        Assertions.assertTrue(cmd.hasOption("format"), "Format option should present");
    }

    @Test
    void testSortByArtistOption() {
        CommandLine cmd = prepareCommandLine("-a");
        Assertions.assertTrue(cmd.hasOption("sort-by-artist"), "Sort-by-artist option should present");

        cmd = prepareCommandLine("--sort-by-artist");
        Assertions.assertTrue(cmd.hasOption("sort-by-artist"), "Sort-by-artist option should present");

        cmd = prepareCommandLine(allKeys);
        Assertions.assertTrue(cmd.hasOption("sort-by-artist"), "Sort-by-artist option should present");
    }

    @Test
    void testSortByTrackNameOption() {
        CommandLine cmd = prepareCommandLine("-n");
        Assertions.assertTrue(cmd.hasOption("sort-by-title"), "Sort-by-title option should present");

        cmd = prepareCommandLine("--sort-by-title");
        Assertions.assertTrue(cmd.hasOption("sort-by-title"), "Sort-by-title option should present");

        cmd = prepareCommandLine(allKeys);
        Assertions.assertTrue(cmd.hasOption("sort-by-title"), "Sort-by-title option should present");
    }

    @Test
    void testSortByTrackDateOption() {
        CommandLine cmd = prepareCommandLine("-s");
        Assertions.assertTrue(cmd.hasOption("sort-by-date"), "Sort-by-date option should present");

        cmd = prepareCommandLine("--sort-by-date");
        Assertions.assertTrue(cmd.hasOption("sort-by-date"), "Sort-by-date option should present");

        cmd = prepareCommandLine(allKeys);
        Assertions.assertTrue(cmd.hasOption("sort-by-date"), "Sort-by-date option should present");
    }

    @Test
    void testJsonFileOption() {
        CommandLine cmd = prepareCommandLine(jsonKeys);
        Assertions.assertTrue(cmd.hasOption("json"), "Json option should present");

        cmd = prepareCommandLine(jsonKeysFull);
        Assertions.assertTrue(cmd.hasOption("json"), "Json option should present");

        cmd = prepareCommandLine(allKeys);
        Assertions.assertTrue(cmd.hasOption("json"), "Json option should present");
    }

    @Test
    void testXmlFileOption() {
        CommandLine cmd = prepareCommandLine(xmlKeys);
        Assertions.assertTrue(cmd.hasOption("xml"), "Xml option should present");

        cmd = prepareCommandLine(xmlKeysFull);
        Assertions.assertTrue(cmd.hasOption("xml"), "Xml option should present");

        cmd = prepareCommandLine(allKeys);
        Assertions.assertTrue(cmd.hasOption("xml"), "Xml option should present");
    }

    @Test
    void testTxtFileOption() {
        CommandLine cmd = prepareCommandLine(txtKeys);
        Assertions.assertTrue(cmd.hasOption("target"), "Target option should present");

        cmd = prepareCommandLine(txtKeysFull);
        Assertions.assertTrue(cmd.hasOption("target"), "Target option should present");

        cmd = prepareCommandLine(allKeys);
        Assertions.assertTrue(cmd.hasOption("target"), "Target option should present");
    }
}

package su.wrath;

import org.apache.commons.cli.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static su.wrath.TestHelpers.prepareCommandLine;

class LauncherTest {

    private final String[] jsonKeys = {"-j", "output.json"};
    private final String[] jsonKeysFull = {"--json", "output.json"};
    private final String[] xmlKeys = {"-x", "output.xml"};
    private final String[] xmlKeysFull = {"--xml", "output.xml"};
    private final String[] txtKeys = {"-t", "output.txt"};
    private final String[] txtKeysFull = {"--target", "output.txt"};
    private final String[] filterDateKeys = {"-p", "01.02.2024"};
    private final String[] filterDateKeysFull = {"--filter-date-after","01.02.2024"};
    private final String[] allKeys = {"-dfans", "-p", "01.02.2024", "-j", "output.json", "-x", "output.xml", "-t", "output.txt", "input.csv"};

    @Test
    void testHelpOption() {
        CommandLine cmd = prepareCommandLine("-h");
        assertTrue(cmd.hasOption("help"), "Help option should present");

        cmd = prepareCommandLine("--help");
        assertTrue(cmd.hasOption("help"), "Help option should present");

        cmd = prepareCommandLine("-d");
        assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine("-f");
        assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine("-a");
        assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine("-n");
        assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine("-s");
        assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine("inputFileName.csv");
        assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine(jsonKeys);
        assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine(xmlKeys);
        assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine(txtKeys);
        assertFalse(cmd.hasOption("help"), "Help option should not present");

        cmd = prepareCommandLine(allKeys);
        assertFalse(cmd.hasOption("help"), "Help option should not present");
    }

    @Test
    void testDateOption() {
        CommandLine cmd = prepareCommandLine("-d");
        assertTrue(cmd.hasOption("date"), "Date option should present");

        cmd = prepareCommandLine("--date");
        assertTrue(cmd.hasOption("date"), "Date option should present");

        cmd = prepareCommandLine(allKeys);
        assertTrue(cmd.hasOption("date"), "Date option should present");

    }

    @Test
    void testFormatOption() {
        CommandLine cmd = prepareCommandLine("-f");
        assertTrue(cmd.hasOption("format"), "Format option should present");

        cmd = prepareCommandLine("--format");
        assertTrue(cmd.hasOption("format"), "Format option should present");

        cmd = prepareCommandLine(allKeys);
        assertTrue(cmd.hasOption("format"), "Format option should present");
    }

    @Test
    void testSortByArtistOption() {
        CommandLine cmd = prepareCommandLine("-a");
        assertTrue(cmd.hasOption("sort-by-artist"), "Sort-by-artist option should present");

        cmd = prepareCommandLine("--sort-by-artist");
        assertTrue(cmd.hasOption("sort-by-artist"), "Sort-by-artist option should present");

        cmd = prepareCommandLine(allKeys);
        assertTrue(cmd.hasOption("sort-by-artist"), "Sort-by-artist option should present");
    }

    @Test
    void testSortByTrackNameOption() {
        CommandLine cmd = prepareCommandLine("-n");
        assertTrue(cmd.hasOption("sort-by-title"), "Sort-by-title option should present");

        cmd = prepareCommandLine("--sort-by-title");
        assertTrue(cmd.hasOption("sort-by-title"), "Sort-by-title option should present");

        cmd = prepareCommandLine(allKeys);
        assertTrue(cmd.hasOption("sort-by-title"), "Sort-by-title option should present");
    }

    @Test
    void testSortByTrackDateOption() {
        CommandLine cmd = prepareCommandLine("-s");
        assertTrue(cmd.hasOption("sort-by-date"), "Sort-by-date option should present");

        cmd = prepareCommandLine("--sort-by-date");
        assertTrue(cmd.hasOption("sort-by-date"), "Sort-by-date option should present");

        cmd = prepareCommandLine(allKeys);
        assertTrue(cmd.hasOption("sort-by-date"), "Sort-by-date option should present");
    }

    @Test
    void testJsonFileOption() {
        CommandLine cmd = prepareCommandLine(jsonKeys);
        assertTrue(cmd.hasOption("json"), "Json option should present");

        cmd = prepareCommandLine(jsonKeysFull);
        assertTrue(cmd.hasOption("json"), "Json option should present");

        cmd = prepareCommandLine(allKeys);
        assertTrue(cmd.hasOption("json"), "Json option should present");
    }

    @Test
    void testXmlFileOption() {
        CommandLine cmd = prepareCommandLine(xmlKeys);
        assertTrue(cmd.hasOption("xml"), "Xml option should present");

        cmd = prepareCommandLine(xmlKeysFull);
        assertTrue(cmd.hasOption("xml"), "Xml option should present");

        cmd = prepareCommandLine(allKeys);
        assertTrue(cmd.hasOption("xml"), "Xml option should present");
    }

    @Test
    void testTxtFileOption() {
        CommandLine cmd = prepareCommandLine(txtKeys);
        assertTrue(cmd.hasOption("target"), "Target option should present");

        cmd = prepareCommandLine(txtKeysFull);
        assertTrue(cmd.hasOption("target"), "Target option should present");

        cmd = prepareCommandLine(allKeys);
        assertTrue(cmd.hasOption("target"), "Target option should present");
    }

    @Test
    void testFilterDateOption() {
        CommandLine cmd = prepareCommandLine(filterDateKeys);
        assertTrue(cmd.hasOption("filter-date-after"), "Date filtration option should present");

        cmd = prepareCommandLine(filterDateKeysFull);
        assertTrue(cmd.hasOption("filter-date-after"), "Date filtration option should present");

        cmd = prepareCommandLine(allKeys);
        assertTrue(cmd.hasOption("filter-date-after"), "Date filtration option should present");
    }
}

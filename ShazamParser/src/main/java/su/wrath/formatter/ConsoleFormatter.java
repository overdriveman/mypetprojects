package su.wrath.formatter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import su.wrath.bean.MusicTrack;
import su.wrath.bean.TextFormatterTemplate;

import java.io.PrintWriter;
import java.util.List;

@Slf4j
public class ConsoleFormatter extends PlainTextFormatter {

    @Override
    public void format(List<MusicTrack> tracks, CommandLine cmd) {
        TextFormatterTemplate template = textOutputTemplateComposer(cmd);
        PrintWriter consoleWriter = new PrintWriter(System.out, true);
        printFormattedTracks(tracks, consoleWriter, template);
    }
}

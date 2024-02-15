package su.wrath.formatter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import su.wrath.bean.MusicTrack;
import su.wrath.bean.TextFormatterTemplate;

import java.io.PrintWriter;
import java.util.List;

@Slf4j
public abstract class PlainTextFormatter implements Formatter {

    public TextFormatterTemplate textOutputTemplateComposer(CommandLine cmd) {

        if (!(cmd.hasOption("date")) && !(cmd.hasOption("format"))) {
            return new TextFormatterTemplate("%s - %s%n", 2);
        } else if (cmd.hasOption("date") && !(cmd.hasOption("format"))) {
            return new TextFormatterTemplate("%s - %s - %s%n", 3);
        } else if (!(cmd.hasOption("date")) && cmd.hasOption("format")) {
            return new TextFormatterTemplate("%-28.28s %s%n", 2);
        } else if (cmd.hasOption("date") && cmd.hasOption("format")) {
            return new TextFormatterTemplate("%-28.28s %-28.28s %s%n", 3);
        } else {
            log.error("Ошибка составления шаблона по ключам: '{}'", cmd);
            throw new RuntimeException("По указанным ключам невозможно создать шаблон вывода");
        }
    }

    public void printFormattedTracks(List<MusicTrack> tracks, PrintWriter writer, TextFormatterTemplate template) {
        if (template.outputColumns == 3) {
            writer.printf(template.formatString, "Artist", "Track", "date");
            tracks.forEach(t -> writer.printf(String.format(template.formatString, t.getArtist(), t.getTitle(), t.getDate())));
        } else if (template.outputColumns == 2) {
            writer.printf(template.formatString, "Artist", "Track");
            tracks.forEach(t -> writer.printf(String.format(template.formatString, t.getArtist(), t.getTitle())));
        } else {
            log.error("Невозможно вывести в файл шаблон с номером столбцов: '{}'", template.outputColumns);
        }
    }
}

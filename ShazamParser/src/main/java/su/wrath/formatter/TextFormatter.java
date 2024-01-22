package su.wrath.formatter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import su.wrath.bean.MusicTrack;
import su.wrath.bean.TextFormatterTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
public class TextFormatter extends PlainTextFormatter {

    @Override
    public void format(List<MusicTrack> tracks, CommandLine cmd) {
        TextFormatterTemplate template = textOutputTemplateComposer(cmd);
        String outputFile = cmd.getOptionValue("target");

        try (FileWriter fileWriter = new FileWriter(outputFile);
            PrintWriter printWriter = new PrintWriter(fileWriter)) {

            if (template.outputColumns == 3) {
                printWriter.printf(template.formatString, "Artist", "Track", "date");
                tracks.forEach(t -> printWriter.printf(String.format(template.formatString, t.getArtist(), t.getTitle(), t.getDate())));
            } else if (template.outputColumns == 2) {
                printWriter.printf(template.formatString, "Artist", "Track");
                tracks.forEach(t -> printWriter.printf(String.format(template.formatString, t.getArtist(), t.getTitle())));
            } else {
                log.error("Невозможно вывести в файл шаблон с номером столбцов: '{}'", template.outputColumns);
            }

        } catch (IOException e) {
            log.error("Ошибка при записи в файл: ", e);
        }
    }
}

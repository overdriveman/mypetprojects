package su.wrath.formatter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import su.wrath.bean.MusicTrack;
import su.wrath.bean.TextFormatterTemplate;

import java.util.List;

@Slf4j
public class ConsoleFormatter extends PlainTextFormatter {

    @Override
    public void format(List<MusicTrack> tracks, CommandLine cmd) {
        TextFormatterTemplate template = textOutputTemplateComposer(cmd);
        write(template, tracks);
    }

    protected void write(TextFormatterTemplate template, List<MusicTrack> tracks) {
        if (template.outputColumns == 3) {
            System.out.printf(template.formatString, "Artist", "Track", "Date");
            tracks.forEach(musicTrack -> System.out.printf(template.formatString, musicTrack.getArtist(), musicTrack.getTitle(), musicTrack.getDate()));
        } else if (template.outputColumns == 2) {
            System.out.printf(template.formatString, "Artist", "Track");
            tracks.forEach(track -> System.out.printf(template.formatString, track.getArtist(), track.getTitle()));
        } else {
            log.error("Невозможно вывести в консоль шаблон с номером столбцов: '{}'", template.outputColumns);
        }
    }
}

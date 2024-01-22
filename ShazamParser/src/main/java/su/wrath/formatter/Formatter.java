package su.wrath.formatter;

import org.apache.commons.cli.CommandLine;
import su.wrath.bean.MusicTrack;

import java.util.List;

public interface Formatter {

    void format(List<MusicTrack> tracks, CommandLine cmd);

}

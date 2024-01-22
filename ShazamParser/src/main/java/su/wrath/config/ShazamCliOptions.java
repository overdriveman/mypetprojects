package su.wrath.config;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ShazamCliOptions {

    public static Options buildOptions() {
        Options options = new Options();

        options.addOption(Option.builder("x")
                .longOpt("xml")
                .hasArg(true)
                .argName("xml_file")
                .optionalArg(false)
                .desc("write output to xml formatted file")
                .build());

        options.addOption(Option.builder("j")
                .longOpt("json")
                .hasArg(true)
                .argName("json_file")
                .optionalArg(false)
                .desc("write output to json formatted file")
                .build());

        options.addOption(Option.builder("t")
                .longOpt("target")
                .hasArg(true)
                .argName("csv_file")
                .optionalArg(false)
                .desc("write output to file")
                .build());

        options.addOption(new Option("d", "date", false, "add date to console output"));
        options.addOption(new Option("f", "format", false, "format the output"));
        options.addOption(new Option("h", "help", false, "print help message"));

        options.addOption(new Option("a", "sort-by-artist", false, "sort output by title artist"));
        options.addOption(new Option("n", "sort-by-title", false, "sort output by title name"));
        options.addOption(new Option("s", "sort-by-date", false, "sort output by addition date to library"));

        return options;
    }
}

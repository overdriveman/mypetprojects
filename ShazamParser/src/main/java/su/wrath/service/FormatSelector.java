package su.wrath.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import su.wrath.formatter.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FormatSelector {

    public List<Formatter> chooseFormatters(CommandLine cmd) {
        List<Formatter> formatters = new ArrayList<>();

        if (cmd.hasOption("target")) {
            formatters.add(new TextFormatter());
        }

        if (cmd.hasOption("xml")) {
            formatters.add(new XmlFormatter());
        }

        if (cmd.hasOption("json")) {
            formatters.add(new JsonFormatter());
        }

        formatters.add(new ConsoleFormatter());

        return formatters;
    }
}

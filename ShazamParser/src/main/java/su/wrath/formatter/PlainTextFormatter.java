package su.wrath.formatter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import su.wrath.bean.TextFormatterTemplate;

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
}

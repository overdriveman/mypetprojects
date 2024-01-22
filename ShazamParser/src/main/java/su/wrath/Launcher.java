package su.wrath;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import su.wrath.bean.MusicTrack;
import su.wrath.formatter.*;
import su.wrath.service.CsvTrackConverter;
import su.wrath.service.FormatSelector;
import su.wrath.service.InputValidator;

import java.util.List;

import static su.wrath.config.ShazamCliOptions.buildOptions;

@Slf4j
public class Launcher {

    private static final Options options = buildOptions();

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
        } else {
            CommandLineParser parser = new DefaultParser();

            try {
                CommandLine cmd = parser.parse(options, args);

                if (cmd.hasOption("help")) {
                    printHelp();
                } else {
                    startProcessingCmd(cmd);
                }
            } catch (MissingArgumentException argException) {
                log.error("Не указаны обязательные аргументы для опции: '{}'/'{}'",
                        argException.getOption().getOpt(),
                        argException.getOption().getLongOpt());

                System.exit(2);
            } catch (ParseException e) {
                log.error("Ошибка парсинга аргументов: ", e);
                System.exit(1);
            }
        }
    }

    private static void startProcessingCmd(CommandLine cmd) {
        InputValidator validator = new InputValidator();

        if (validator.validateInputArgs(cmd)) {
            CsvTrackConverter csvReader = new CsvTrackConverter();
            List<MusicTrack> tracks = csvReader.parseFile(cmd);

            FormatSelector selector = new FormatSelector();
            for (Formatter formatter : selector.chooseFormatters(cmd)) {
                formatter.format(tracks, cmd);
            }
        } else {
            log.error("Аргументы заданы не верно!");
        }
    }

    private static void printHelp() {
        HelpFormatter helpForm = new HelpFormatter();
        helpForm.printHelp("java -jar ShazamConv.jar [-dfans] [-j <json_file>] " +
                "[-x <xml_file>] [-t <plain_text_file>] input_file.csv", options);
    }
}

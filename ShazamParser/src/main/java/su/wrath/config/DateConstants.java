package su.wrath.config;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;

@UtilityClass
public class DateConstants {

    // DateTimeFormatter pattern
    public final String DATE_DELIMITER = "['/']['-']['.']";
    public final String DAY_FIRST_PATTERN = "[dd" + DATE_DELIMITER + "MM" + DATE_DELIMITER + "uuuu]";
    public final String YEAR_FIRST_PATTERN = "[uuuu" + DATE_DELIMITER + "dd" + DATE_DELIMITER + "MM]";
    public final String DATE_PARSING_PATTERN = DAY_FIRST_PATTERN + YEAR_FIRST_PATTERN;

    /**
     * ResolverStyle.STRICT выставлен потому, что стандартный SMART (по умолчанию) приводит к некорректной работе
     * приложения: если вызывать LocalDate.parse с датой, например 29 февраля (не високосный), 30 и 31 февраля
     * то дата будет выставлена корректно: будет выставлен последний день февраля 28 для обычного 29 для високосного.
     */
    public final DateTimeFormatter FILTER_FORMATTER = DateTimeFormatter
            .ofPattern(DATE_PARSING_PATTERN)
            .withResolverStyle(ResolverStyle.STRICT);

    // String regex
    public final String DAY_FIRST_REGEX = "(0[1-9]|[12]\\d|3[01])[./-](0[1-9]|1[012])[./-]\\d{4}";
    public final String YEAR_FIRST_REGEX = "\\d{4}[./-](0[1-9]|[12]\\d|3[01])[./-](0[1-9]|1[012])";

}

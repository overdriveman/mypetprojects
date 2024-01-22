package su.wrath;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static su.wrath.MsgInterpreter.*;
import static su.wrath.InputParser.processString;


public class MsgInterpreterTest {

    @Test
    public void prefixMeansWithCorrectInput() {
        assertEquals("Московское городское такси ", prefixMeans("MTT"), "Такси");
        assertEquals("Московский городской автобус ", prefixMeans("MTA"), "Автобус");
    }

    @Test
    public void prefixMeansWithIncorrectInput() {
        assertEquals("Транспорт не определен!", prefixMeans("ZBA"), "Плохой префикс");
    }

    @Test
    public void prefixMeansWithEmptyInput() {
        assertEquals("Транспорт не определен!", prefixMeans(""), "Пустой префикс");
    }

    @Test
    public void categoryMeansWithCorrectInput() {
        assertEquals("за безопасность движения ", categoryMeans(1), "Категория 1");
        assertEquals("за поведение кондуктора/водителя ", categoryMeans(2), "Категория 2");
        assertEquals("за соблюдение расписания ", categoryMeans(3), "Категория 3");
        assertEquals("за техническое состояние ", categoryMeans(4), "Категория 4");
        assertEquals("за оплату проезда ", categoryMeans(5), "Категория 5");
    }

    @Test
    public void categoryMeansWithIncorrectInput() {
        assertEquals("Ошибка в категории оценки!", categoryMeans(9), "Категория 9");
        assertEquals("Ошибка в категории оценки!", categoryMeans(0), "Категория 0");
    }

    @Test
    public void reviewMeansWithCorrectInput() {
        assertEquals("получил оценку не приемлемо", reviewMeans(1), "Оценка 1");
        assertEquals("получил оценку плохо", reviewMeans(2), "Оценка 2");
        assertEquals("получил оценку удовлетворительно", reviewMeans(3), "Оценка 3");
        assertEquals("получил оценку хорошо", reviewMeans(4), "Оценка 4");
        assertEquals("получил оценку отлично", reviewMeans(5), "Оценка 5");
    }

    @Test
    public void reviewMeansWithIncorrectInput() {
        assertEquals("Ошибка в оценке!", reviewMeans(125), "Оценка 125");
        assertEquals("Ошибка в оценке!", reviewMeans(0), "Оценка 0");
    }

    @Test
    public void processResultValidResult() {
        ParsingResult result = processString("mta ав141х 2 1");
        String validOutput = "Московский городской автобус c номером ав141х за поведение кондуктора/водителя получил оценку не приемлемо";
        assertEquals(validOutput, processResult(result), "Корректная работа");
    }

    @Test
    public void processResultInvalidResult() {
        ParsingResult result = processString("mta ав141х 22 1");
        String notValidOutput = "Обработка прошла с ошибкой! Код ошибки: 5, значение: Неверно указан тип оценки";
        assertEquals(notValidOutput, processResult(result), "Корректная работа");
    }

    @Test
    public void decodeTestFormatValid() {
        ParsingResult result = processString("mta ав141х 4 4");
        String formattedString = String.format("%sc номером %s %s%s",
                prefixMeans("mta"),
                "ав141х",
                categoryMeans(4),
                reviewMeans(4));

        assertEquals(formattedString, processResult(result), "Вывод должен совпадать");
    }

    @Test
    public void decodeTestFormatInvalid() {
        ParsingResult result = processString("mta ав141х 44 4");
        String output = "Обработка прошла с ошибкой! Код ошибки: "
                + result.getErrorCode()
                + ", значение: "
                + result.getErrorDescription();

        assertEquals(output, processResult(result), "Вывод должен совпадать");
    }
}

package su.wrath;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MsgInterpreter {

    static String decode(Message msg) {

        return String.format("%sc номером %s %s%s",
                prefixMeans(msg.getMsgPrefix()),
                msg.getTransportNumber(),
                categoryMeans(msg.getCategory()),
                reviewMeans(msg.getReview()));
    }

    static String prefixMeans(String input) {
        switch (input.toUpperCase()) {
            case "MTA":
                return "Московский городской автобус ";
            case "MTT":
                return "Московское городское такси ";
            default:
                return "Транспорт не определен!";
        }
    }

    static String categoryMeans(int input) {
        switch (input) {
            case 1:
                return "за безопасность движения ";
            case 2:
                return "за поведение кондуктора/водителя ";
            case 3:
                return "за соблюдение расписания ";
            case 4:
                return "за техническое состояние ";
            case 5:
                return "за оплату проезда ";
            default:
                return "Ошибка в категории оценки!";
        }
    }

    static String reviewMeans(int input) {
        switch (input) {
            case 1:
                return "получил оценку не приемлемо";
            case 2:
                return "получил оценку плохо";
            case 3:
                return "получил оценку удовлетворительно";
            case 4:
                return "получил оценку хорошо";
            case 5:
                return "получил оценку отлично";
            default:
                return "Ошибка в оценке!";
        }
    }

    static String processResult(ParsingResult result) {

        if (result.isValid()) {
            return result.getMessage()
                    .map(MsgInterpreter::decode)
                    .orElse("Ошибка в формировании сообщения!");
        } else {
            return "Обработка прошла с ошибкой! Код ошибки: "
                    + result.getErrorCode()
                    + ", значение: "
                    + result.getErrorDescription();
        }
    }
}

package su.wrath;

public class MsgInterpreter {

    public static void intepret(Message msg) {
        StringBuilder result = new StringBuilder();

        result
                .append(prefixMeans(msg.getMsgPrefix()))
                .append("c номером ").append(msg.getTransportNumber()).append(" ")
                .append(categoryMeans(msg.getCategory()))
                .append(reviewMeans(msg.getReview()));

        System.out.println(result);
    }

    private static String prefixMeans(String input) {
        switch (input.toUpperCase()) {
            case "MTA":
                return "Московский Транспорт Автобус ";
            case "MTT":
                return "Московский Транспорт Такси ";
            default:
                return "Транспорт не определен!";
        }
    }

    private static String categoryMeans(int input) {
        switch (input) {
            case 1:
                return "за безопасность движения ";
            case 2:
                return "за поведение кондуктора/водителя ";
            case 3:
                return "за нарушение расписания ";
            case 4:
                return "за техническое состояние ";
            case 5:
                return "за оплату проезда ";
            default:
                return "Ошибка Опредения Категории Оценки!";
        }
    }

    private static String reviewMeans(int input) {
        switch (input) {
            case 1:
                return "получил очень плохо";
            case 2:
                return "получил плохо";
            case 3:
                return "получил удовлетворительно";
            case 4:
                return "получил хорошо";
            case 5:
                return "получил отлично";
            default:
                return "Ошибка Определения Оценки!";
        }
    }

    public static void main(String[] args) {
        Message msg = new Message("mta", "ав141х", 3, 3);
        intepret(msg);
    }

}

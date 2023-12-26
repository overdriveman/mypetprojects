package su.wrath;

public enum ErrorMessage {
    SUCCESS("Обработка прошла успешно", 0),
    WRONG_INPUT_FORMAT("Некорректный формат входных данных",1),
    WRONG_ARGUMENTS("Некорректное число входных аргументов", 2),
    WRONG_PREFIX("Неверно указан префикс",3),
    WRONG_TRANSPORT_NUMBER("Неверно указан номер ТС",4),
    WRONG_CATEGORY("Неверно указан тип оценки",5),
    WRONG_REVIEW("Неверно указана оценка",6);

    public final String description;

    public final int code;

    ErrorMessage(String description, int code) {
        this.description = description;
        this.code = code;
    }

    public boolean isValid() {
        return (this.code == 0);
    }

}

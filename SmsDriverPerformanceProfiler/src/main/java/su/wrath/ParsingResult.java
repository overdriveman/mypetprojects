package su.wrath;

import java.util.Optional;

/**
 * Цель класса - хранение результата работы InputParser
 * результат корректной работы InputParser - обьъект Message;
 * В случае ошибки Message не будет сформирован, поэтому getMessage() возращает Optional;
 * Есть метод isValid() который показывает наличие ошибки в результате работы InputParser;
 * ошибку можно получить в двух видах:
 *      человекочитаемый вид - getErrorDescription
 *      код для апи - getErrorCode
 *
 * Пример использования:
 * InputParser parser = new InputParser(); // Создаем новый парсер обработки запросов
 *         String tstInput = "mta ав141х 1 1"; //Пример входных данных
 *
 *         ParsingResult result = parser.processString(tstInput);
 *         if (result.isValid()) {
 *             Optional<Message> message = result.getMessage();// можем Message забирать
 *         } else {
 *             int code = result.getErrorCode(); // можем значение ошибки по-человечески забирать
 *             String description = result.getErrorDescription(); // можем забирать код ошибки для API
 *         }
 */
public class ParsingResult {

    private ErrorMessage error;

    private Message message;

    public ParsingResult(ErrorMessage error) {
        this.error = error;
    }

    public ParsingResult(Message message) {
        this.message = message;
        this.error = ErrorMessage.SUCCESS;
    }

    public boolean isValid() {
        return error.isValid();
    }

    public String getErrorDescription(){
        return error.description;
    }

    public int getErrorCode(){
        return error.code;
    }

    public Optional<Message> getMessage() {
        return Optional.ofNullable(message);
    }
}

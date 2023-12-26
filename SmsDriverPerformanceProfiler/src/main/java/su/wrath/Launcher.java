package su.wrath;

/**
 * Логика работы на пальцах:
 * Мы получаем на вход строку\файл [может clicommons прикрутить?] ... пока не нужно прикручивать (этот проект без
 * него... в следущем будет :) )
 * Дальше мы данные:
 *               обрабатываем [InputParser]
 *              валидируем  [MsgValidator]
 *                  формируем объекты: репорт + то что распарсили(message)
 *                  возможно что-то делаем с message [пропихиваем в базу например]
 *
 * после обработки выдаем: в случае строки - успех\ неуспех + ошибка
 *                          в случае файла - успех - просто стрингом поздравляем
 *                                              неуспех - список (ошибка: + номер строки файла + строка) [репорт]
 *                                              успех + неуспех - возвращаем два списка                  [репорт]
 */
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class Launcher {
    public static void main(String[] args) {

        InputParser parser = new InputParser();
        String tstInput = "      mta      ав141х    1   1  ";

        ParsingResult result = parser.processString(tstInput);
        if (result.isValid()) {
            Optional<Message> message = result.getMessage();// можем Message забирать
        } else {
            int code = result.getErrorCode(); // можем значение ошибки по-человечески забирать
            String description = result.getErrorDescription(); // можем забирать код ошибки для API
        }

        String wrngInput = "mta ав141х 2 9 ";
        result = parser.processString(wrngInput);
    }
}

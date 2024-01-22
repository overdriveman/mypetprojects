package su.wrath;

import lombok.extern.slf4j.Slf4j;

import static su.wrath.MsgValidator.*;

@Slf4j
public class InputParser {

    public static ParsingResult processString(String input) {
        ErrorMessage errorCode = validateInput(input);

        if (errorCode.isValid()) {
            String[] tokens = tokenize(input);

            Message newMessage = Message.builder()
                    .msgPrefix(tokens[0])
                    .transportNumber(tokens[1])
                    .category(tokens[2])
                    .review(tokens[3])
                    .build();

            log.info("Успешно обработали вызов парсера: '{}'", input);
            return new ParsingResult(newMessage);
        }
        log.warn("Ошибка обработки: '{}'", input);
        return new ParsingResult(errorCode);
    }

}

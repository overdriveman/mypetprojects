package su.wrath;

import lombok.extern.slf4j.Slf4j;

import static su.wrath.Constants.*;


/**
 * Цель класса - проверка входных данных на валидность
 * Главный метод - validateInput("input").
 * Рузльтат работы - ErrorMessage
 * Класс для внутреннего использования - не предназначен для внешнего API
 */
@Slf4j
public class MsgValidator {
    private final static String[] EMPTY_STRING = {" "};

    protected static ErrorMessage validateInput(String input) {
        ErrorMessage errorCode = checkInputFormat(input); //1;
        String[] tokens = tokenize(input, errorCode);
        if (tokens.length == 4) {
            errorCode = tokenChecker(errorCode, tokens[0], MSG_PREFIX_PATTERN, ErrorMessage.WRONG_PREFIX);
            errorCode = tokenChecker(errorCode, tokens[1], MSG_TRANSPORT_NUM_PATTERN, ErrorMessage.WRONG_TRANSPORT_NUMBER);
            errorCode = tokenChecker(errorCode, tokens[2], MSG_CATEGORY_PATTERN, ErrorMessage.WRONG_CATEGORY);
            errorCode = tokenChecker(errorCode, tokens[3], MSG_REVIEW_PATTERN, ErrorMessage.WRONG_REVIEW);

            return errorCode;
        } else if (errorCode != ErrorMessage.SUCCESS) {
            return errorCode;
        }
        log.warn("{}: '{}'", ErrorMessage.WRONG_ARGUMENTS.description, input); //2
        return ErrorMessage.WRONG_ARGUMENTS;
    }

    protected static ErrorMessage checkInputFormat(String input) {
        if (input == null || (input.length() < MSG_MIN_LENGTH)) {
            log.warn("{}: '{}'", ErrorMessage.WRONG_INPUT_FORMAT.description, input);
            return ErrorMessage.WRONG_INPUT_FORMAT;
        } else {
            return ErrorMessage.SUCCESS;
        }
    }

    protected static String[] tokenize(String input) {
        return input.trim().replaceAll("\\s+", " ").split(" ");
    }

    protected static String[] tokenize(String input, ErrorMessage errorCode) {
        if (errorCode == ErrorMessage.SUCCESS) {
            return tokenize(input);
        } else {
            return EMPTY_STRING;
        }
    }

    protected static ErrorMessage checkPrefix(String input, ErrorMessage errorCode) { //Обратить внимание - стало ли лушче? Я затрудняюсь ответить.
        if (errorCode == ErrorMessage.SUCCESS) {
            if (checkTokenWithPattern(input, MSG_PREFIX_PATTERN)) {
                return ErrorMessage.SUCCESS;
            } else {
                log.warn("{}: '{}'", ErrorMessage.WRONG_PREFIX.description, input);
                return ErrorMessage.WRONG_PREFIX;
            }
        } else {
            return errorCode;
        }
    }

    protected static ErrorMessage checkTransportNumber(String input, ErrorMessage errorCode) {
        if (errorCode == ErrorMessage.SUCCESS) {
            if (checkTokenWithPattern(input, MSG_TRANSPORT_NUM_PATTERN)) {
                return ErrorMessage.SUCCESS;
            } else {
                log.warn("{}: '{}'", ErrorMessage.WRONG_TRANSPORT_NUMBER.description, input);
                return ErrorMessage.WRONG_TRANSPORT_NUMBER;
            }
        } else {
            return errorCode;
        }
    }

    protected static ErrorMessage checkCategory(String category, ErrorMessage errorCode) {
        if (errorCode == ErrorMessage.SUCCESS) {
            if (checkTokenWithPattern(category, MSG_CATEGORY_PATTERN)) {
                return ErrorMessage.SUCCESS;
            } else {
                log.warn("{}: '{}'", ErrorMessage.WRONG_CATEGORY.description, category);
                return ErrorMessage.WRONG_CATEGORY;
            }
        } else {
            return errorCode;
        }
    }

    protected static ErrorMessage checkReview(String review, ErrorMessage errorCode) {
        if (errorCode == ErrorMessage.SUCCESS) {
            if (checkTokenWithPattern(review, MSG_REVIEW_PATTERN)) {
                return ErrorMessage.SUCCESS;
            } else {
                log.warn("{}: '{}'", ErrorMessage.WRONG_REVIEW.description, review);
                return ErrorMessage.WRONG_REVIEW;
            }
        } else {
            return errorCode;
        }
    }

    protected static boolean checkTokenWithPattern(String token, String pattern) {
        if (token == null) {
            return false;
        } else {
            String upper = token.toUpperCase();
            return upper.matches(pattern);
        }
    }

    //внизу, потому-что играемся пока тут с этим делом, когда уберем избыточные методы - отрефакторим
    protected static ErrorMessage tokenChecker(ErrorMessage errorCode, String token, String pattern, ErrorMessage tokenError) {
        if (errorCode == ErrorMessage.SUCCESS) {
            if (checkTokenWithPattern(token, pattern)) {
                return ErrorMessage.SUCCESS;
            } else {
                log.warn("{}: '{}'", tokenError.description, token);
                return tokenError;
            }
        } else {
            return errorCode;
        }
    }

}

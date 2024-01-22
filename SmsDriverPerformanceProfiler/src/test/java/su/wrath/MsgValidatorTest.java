package su.wrath;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MsgValidatorTest implements Constants {

    private final String CORRECT_INPUT = "mta ав141х 2 4";
    private final String CORRECT_PREFIX = "mta";
    private final String CORRECT_TRANSPORT_NUM ="ав141х";
    private final String CORRECT_CATEGORY="2";
    private final String CORRECT_REVIEW="4";

    @Test
    public void checkInputFormatWithNull() {
        ErrorMessage error = MsgValidator.checkInputFormat(null);
        assertEquals(1, error.code,"Код ошибки должен быть 1");
        assertEquals("Некорректный формат входных данных", error.description,"Проверка текстового описания ошибки");
    }

    @Test
    public void checkInputFormatWithShortInput() {
        ErrorMessage error = MsgValidator.checkInputFormat("1 2 3 4");
        assertEquals(1, error.code,"Код ошибки должен быть 1");
        assertEquals("Некорректный формат входных данных", error.description,"Сообщение об ошибке");
    }

    @Test
    public void checkInputFormatWithCorrectInput() {
        ErrorMessage error = MsgValidator.checkInputFormat(CORRECT_INPUT);
        assertEquals(0, error.code,"Код ошибки должен быть 0");
        assertEquals("Обработка прошла успешно", error.description,"Сообщение об ошибке");
    }

    @Test
    public void checkTokenizeWithError() {
        String[] tokens = MsgValidator.tokenize(CORRECT_INPUT, ErrorMessage.WRONG_INPUT_FORMAT);
        assertEquals(1, tokens.length,"Должен быть один токен");
    }

    @Test
    public void checkTokenizeWithCorrectInput() {
        String[] tokens = MsgValidator.tokenize(CORRECT_INPUT, ErrorMessage.SUCCESS);
        assertEquals(CORRECT_PREFIX, tokens[0],"Префикс должен быть");
        assertEquals(CORRECT_TRANSPORT_NUM, tokens[1],"Номер ТС должен быть");
        assertEquals(CORRECT_CATEGORY, tokens[2],"Выбрана категория");
        assertEquals(CORRECT_REVIEW, tokens[3],"Выставлена оценка");
    }

    @Test
    public void checkTokenizeWithCorrectSpacedInput() {
        String input = " mta  ав141х      2  4 ";
        String[] tokens = MsgValidator.tokenize(input, ErrorMessage.SUCCESS);
        assertEquals(CORRECT_PREFIX, tokens[0],"Префикс должен быть");
        assertEquals(CORRECT_TRANSPORT_NUM, tokens[1],"Номер ТС должен быть");
        assertEquals(CORRECT_CATEGORY, tokens[2],"Выбрана категория");
        assertEquals(CORRECT_REVIEW, tokens[3],"Выставлена оценка");
    }

    @Test
    public void checkPrefixWithError() {
        ErrorMessage test = MsgValidator.checkPrefix(CORRECT_PREFIX, ErrorMessage.WRONG_INPUT_FORMAT);
        assertEquals(ErrorMessage.WRONG_INPUT_FORMAT, test,"Должна быть ошибка");
    }

    @Test
    public void checkPrefixWithWrongInput() {
        String input = "mtaatm";
        assertEquals(ErrorMessage.WRONG_PREFIX, MsgValidator.checkPrefix(input, ErrorMessage.SUCCESS),"Должна быть ошибка");
    }

    @Test
    public void checkPrefixWithCorrectInput() {
        assertEquals(ErrorMessage.SUCCESS, MsgValidator.checkPrefix(CORRECT_PREFIX, ErrorMessage.SUCCESS),"Должна быть ошибка");
    }

    @Test
    public void checkTransportNumberWithError() {
        ErrorMessage test = MsgValidator.checkTransportNumber(CORRECT_TRANSPORT_NUM, ErrorMessage.WRONG_INPUT_FORMAT);
        assertEquals(ErrorMessage.WRONG_INPUT_FORMAT, test,"Должна быть ошибка");
    }

    @Test
    public void checkTransportNumberWithWrongInput() {
        String input = "ав141х11";
        assertEquals(ErrorMessage.WRONG_TRANSPORT_NUMBER, MsgValidator.checkTransportNumber(input, ErrorMessage.SUCCESS),"Должна быть ошибка");
    }

    @Test
    public void checkTransportNumberWithCorrectInput() {
        assertEquals(ErrorMessage.SUCCESS, MsgValidator.checkTransportNumber(CORRECT_TRANSPORT_NUM, ErrorMessage.SUCCESS),"Должна быть корректная работа");
    }

    @Test
    public void checkCategoryWithError() {
        assertEquals(ErrorMessage.WRONG_INPUT_FORMAT, MsgValidator.checkCategory(CORRECT_CATEGORY, ErrorMessage.WRONG_INPUT_FORMAT),"Должна быть ошибка");
    }

    @Test
    public void checkCategoryWithCharInput() {
        assertEquals(ErrorMessage.WRONG_CATEGORY, MsgValidator.checkCategory("a", ErrorMessage.SUCCESS),"Должна быть ошибка");
    }

    @Test
    public void checkCategoryWithWrongInput() {
        assertEquals(ErrorMessage.WRONG_CATEGORY, MsgValidator.checkCategory("7", ErrorMessage.SUCCESS),"Должна быть ошибка");
    }

    @Test
    public void checkCategoryWithCorrectInput() {
        assertEquals(ErrorMessage.SUCCESS, MsgValidator.checkCategory(CORRECT_CATEGORY, ErrorMessage.SUCCESS),"Должна быть корректная работа");
    }

    @Test
    public void checkReviewWithError() {
        assertEquals(ErrorMessage.WRONG_PREFIX, MsgValidator.checkReview(CORRECT_REVIEW, ErrorMessage.WRONG_PREFIX),"Должна быть ошибка");
    }

    @Test
    public void checkReviewWithCharInput() {
        assertEquals(ErrorMessage.WRONG_REVIEW, MsgValidator.checkReview("d", ErrorMessage.SUCCESS),"Должна быть ошибка");
    }

    @Test
    public void checkReviewWithIncorrectInput() {
        assertEquals(ErrorMessage.WRONG_REVIEW, MsgValidator.checkReview("9",ErrorMessage.SUCCESS),"Должна быть ошибка");
    }

    @Test
    public void checkReviewWithCorrectInput() {
        assertEquals(ErrorMessage.SUCCESS, MsgValidator.checkReview(CORRECT_REVIEW, ErrorMessage.SUCCESS),"Должна быть корректная работа");
    }

    @Test
    public void checkTokensWithPatternTests() {
        String token = null;
        String pattern = "[1-5]";
        assertFalse(MsgValidator.checkTokenWithPattern(token, pattern),"Должна быть ошибка");
        token = "a";
        assertFalse(MsgValidator.checkTokenWithPattern(token, pattern),"Должна быть ошибка");
        token = "6";
        assertFalse(MsgValidator.checkTokenWithPattern(token, pattern),"Должна быть ошибка");
        token = "3";
        assertTrue(MsgValidator.checkTokenWithPattern(token, pattern),"Должна быть корректная работа");
    }

    //prefix with error
    @Test
    public void tokenCheckerPrefixWithError() {
        assertEquals(ErrorMessage.WRONG_INPUT_FORMAT, MsgValidator.tokenChecker(ErrorMessage.WRONG_INPUT_FORMAT,
                CORRECT_PREFIX,
                MSG_PREFIX_PATTERN,
                ErrorMessage.WRONG_PREFIX),"Должна быть ошибка");
    }

}

package su.wrath;

interface Constants {

    /**
     * Допустимые буквы в российских номерах для общественного транспорта
     */
    String NUMBER_VALID_CHARS = "АВЕКМНОРСТУХ";

    /**
     * Шаблон номера для автобуса
     */
    String MSG_TRANSPORT_NUM_PATTERN = "[" + NUMBER_VALID_CHARS + "]{2}[0-9]{3}[" + NUMBER_VALID_CHARS + "]";

    /**
     * Шаблоны частей смс сообщения
     */
    String MSG_PREFIX_PATTERN = "MTA";

    String MSG_CATEGORY_PATTERN = "[1-5]";

    String MSG_REVIEW_PATTERN = "[1-5]";

    int MSG_MIN_LENGTH = 14;
}

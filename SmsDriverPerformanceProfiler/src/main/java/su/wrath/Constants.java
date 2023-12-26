package su.wrath;

interface Constants {
    String NUMBER_VALID_CHARS = "АВЕКМНОРСТУХ";
    String MSG_TRANSPORT_NUM_PATTERN = "[" + NUMBER_VALID_CHARS + "]{2}[0-9]{3}[АВЕКМНОРСТУХ]"; //regex ru Transport
    String MSG_PREFIX_PATTERN = "MTA"; //can be regex
    String MSG_CATEGORY_PATTERN = "[1-5]";
    String MSG_REVIEW_PATTERN = "[1-5]";
    int MSG_MIN_LENGTH = 14; //(prefix+_+transport+_+category+_+review)
}

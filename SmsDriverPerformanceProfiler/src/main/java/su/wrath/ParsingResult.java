package su.wrath;

import java.util.Optional;

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

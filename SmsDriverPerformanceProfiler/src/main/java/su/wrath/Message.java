package su.wrath;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@AllArgsConstructor
@Getter
@Builder
@Slf4j
public class Message {
    private String msgPrefix;
    private String transportNumber;
    private int category;
    private int review;

    public static class MessageBuilder {
        public MessageBuilder category(String category) {
            try {
                this.category = Integer.parseInt(category);
            } catch (NumberFormatException ex) {
                String errorBuf = "Building message with category: " + "'" + category + "'" + "with exception:";
                log.error(errorBuf, ex);
                this.category = -1;
            }
            return this;
        }

        public MessageBuilder review(String review) {
            try {
                this.review = Integer.parseInt(review);
            } catch (NumberFormatException ex) {
                String errorBuf = "Building message with review: " + "'" + review + "'" + "with exception:";
                log.error(errorBuf, ex);
                this.review = -1;
            }
            return this;
        }
    }
}

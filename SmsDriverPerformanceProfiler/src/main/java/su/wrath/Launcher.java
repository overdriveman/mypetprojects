package su.wrath;

import lombok.extern.slf4j.Slf4j;

import static su.wrath.InputParser.processString;
import static su.wrath.MsgInterpreter.processResult;

@Slf4j
public class Launcher {
    public static void main(String[] args) {
        ParsingResult result = processString(String.join(" ", args));

        System.out.println(processResult(result));
    }
}

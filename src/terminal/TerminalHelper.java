package terminal;

import models.Word;

import java.util.ArrayList;

public class TerminalHelper {
    static final String RESET = "\033[0m";
    static final String RED_BOLD = "\033[1;31m";
    static final String GREEN = "\033[0;32m";
    static final String YELLOW = "\033[0;33m";
    static final String BLUE = "\033[0;34m";
    static final String BLUE_BOLD = "\033[1;34m";

    public static void printMisspelledWord(Word word, ArrayList<String> suggestions) {
        System.out.println();
        System.out.println("Could not find '" + RED_BOLD + word.value + RESET +"' in our dictionary");
        System.out.println(YELLOW + "at line " + word.lineNumber + ", col " + word.columnNumber + ":" + RESET);
        if (word.previousLine != null) {
            System.out.println(GREEN + String.valueOf(word.lineNumber - 1) + RESET + "| " + word.previousLine);
        }
        System.out.println(GREEN + String.valueOf(word.lineNumber) + RESET + "| " + word.currentLine.substring(0, word.columnNumber - 1) + RED_BOLD + word.value + RESET + word.currentLine.substring(word.columnNumber - 1 + word.value.length()));
        if (word.followingLine != null) {
            System.out.println(GREEN + String.valueOf(word.lineNumber + 1) + RESET + "| " + word.followingLine);
        }
        System.out.println(BLUE + "Did you mean " + BLUE_BOLD + String.join(", ", suggestions) + "?" + RESET);
    }
}

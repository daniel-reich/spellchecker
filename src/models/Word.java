package models;

public class Word {
    public String value;
    public String surroundingText;
    public int lineNumber;
    public int columnNumber;
    public boolean isStartSentence;

    public Word(String value, int lineNumber, int columnNumber, String surroundingText, boolean isStartSentence) {
        this.value = value;
        this.surroundingText = surroundingText;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.isStartSentence = isStartSentence;
    }
}

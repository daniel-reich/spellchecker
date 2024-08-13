package org.example.models;

public class Word {
    // In java land I think its more typical that all of these properties would be private, with
    // getters and setters? Not going to worry about it right now
    public String value;
    public String previousLine;
    public String currentLine;
    public String followingLine;
    public int lineNumber;
    public int columnNumber;
    public boolean isStartSentence;

    public Word(String value, int lineNumber, int columnNumber, String previousLine, String currentLine, String followingLine, boolean isStartSentence) {
        this.value = value;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.isStartSentence = isStartSentence;
        this.previousLine = previousLine;
        this.currentLine = currentLine;
        this.followingLine = followingLine;
    }
}

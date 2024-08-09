package wordutils;

import models.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordParser implements Iterator<Word> {
    private int lineNumber;
    private int columnNumber;
    private String previousLine;
    private String currentLine;
    private String followingLine;
    private BufferedReader reader;
    private Queue<Word> words;
    private HashSet<Character> validWordChars;
    private HashSet<Character> sentenceTerminators;

    /**
     * Constructs a new {@code WordParser} to read a txt file and return each word
     * as a {@code Word} object.
     *
     * @param validWordChars set of non-letter chars that will be considered part of a word in addition to letters
     */
    public WordParser(HashSet<Character> validWordChars, HashSet<Character> sentenceTerminators) {
        this.validWordChars = validWordChars;
        this.sentenceTerminators = sentenceTerminators;
    }
    public void loadFile(String filepath) {
        try {
            this.reader = new BufferedReader(new FileReader(filepath));
            this.lineNumber = 1;
            this.previousLine = null;
            this.currentLine = this.reader.readLine();
            this.followingLine = this.reader.readLine();
            this.convertCurrentLineToWords();
        } catch (IOException exception) {
            // TODO ERROR HANDLING
        }
    }

    private void convertCurrentLineToWords() {
        StringBuilder word = new StringBuilder(); // TODO IS THIS HOW TO USE STRINGBUILDER?
        ArrayList<Word> words = new ArrayList<>();
        boolean startsSentence = true;
        for (int i = 0; i < currentLine.length(); i++) {
            char letter = currentLine.charAt(i);
            if (Character.isLetter(letter) || this.validWordChars.contains(letter)) {
                word.append(letter);
            } else if (!word.isEmpty()) {
                Word wordContext = new Word(word.toString(), lineNumber, i + 1 - word.length(), previousLine, startsSentence);
                words.add(wordContext);
                startsSentence = false;
                word = new StringBuilder();
            }
            if (this.sentenceTerminators.contains(letter)) {
                startsSentence = true;
            }
        }
        if (!word.isEmpty()) {
            Word wordContext = new Word(word.toString(), lineNumber, currentLine.length() + 1 - word.length(), previousLine, startsSentence);
            words.add(wordContext);
        }
        this.words = new LinkedList<>(words);
    }

    private void processTheNextLine() {
        try {
            this.previousLine = currentLine;
            this.currentLine = this.followingLine;
            this.followingLine = this.reader.readLine();
            this.lineNumber++;
            this.convertCurrentLineToWords();

        } catch (IOException exception) {

        }
    }


    @Override
    public boolean hasNext() {
        return !words.isEmpty() || followingLine != null;
    }

    @Override
    public Word next() {
        if (!words.isEmpty()) {
            Word nextWord = words.remove();
            if (words.isEmpty() && this.hasNext()) {
                this.processTheNextLine();
            }
            return nextWord;
        } else {
            if (this.hasNext()) {
                this.processTheNextLine();
                return this.next();
            }
            return null;
        }

    }
}

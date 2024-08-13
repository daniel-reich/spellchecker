package org.example.wordutils;

import org.example.models.Word;

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
    private final Set<Character> validWordChars;
    private final Set<Character> sentenceTerminators;

    /**
     * Constructs a new {@code WordParser} to read a txt file and return each word
     * as a {@code Word} object.
     *
     * @param filepath path to the .txt file that will be spellchecked
     * @param validWordChars set of non-letter chars that will be considered part of a word in addition to letters
     * @param sentenceTerminators set of chars that will be considered the end of a sentence
     */
    public WordParser(String filepath, Set<Character> validWordChars, Set<Character> sentenceTerminators) {
        this.validWordChars = validWordChars;
        this.sentenceTerminators = sentenceTerminators;
        this.loadFile(filepath);
    }

    private void loadFile(String filepath) {
        try {
            this.reader = new BufferedReader(new FileReader(filepath));
            this.lineNumber = 1;
            this.previousLine = null;
            this.currentLine = this.reader.readLine();
            this.followingLine = this.reader.readLine();
            this.convertCurrentLineToWords();
        } catch (IOException exception) {
            System.out.println("Error trying to read lines of file " + filepath);
            System.exit(1);
        }
    }

    private void convertCurrentLineToWords() {
        StringBuilder word = new StringBuilder();
        ArrayList<Word> words = new ArrayList<>();
        boolean startsSentence = true;
        // Look at each char in the line.
        for (int i = 0; i < currentLine.length(); i++) {
            char letter = currentLine.charAt(i);
            if (Character.isLetter(letter) || this.validWordChars.contains(letter)) {
                word.append(letter);
            } else if (!word.isEmpty()) {
                Word wordContext = new Word(word.toString(), lineNumber, i + 1 - word.length(), previousLine, currentLine, followingLine, startsSentence);
                words.add(wordContext);
                startsSentence = false;
                word = new StringBuilder();
            }
            if (this.sentenceTerminators.contains(letter)) {
                startsSentence = true;
            }
        }
        if (!word.isEmpty()) {
            Word wordContext = new Word(word.toString(), lineNumber, currentLine.length() + 1 - word.length(), previousLine, currentLine, followingLine, startsSentence);
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

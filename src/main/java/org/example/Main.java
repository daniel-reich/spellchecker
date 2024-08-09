package org.example;

import org.example.dictionaries.HashSetWordDictionary;
import org.example.dictionaries.WordDictionary;
import org.example.models.Word;
import org.example.terminal.TerminalHelper;
import org.example.wordutils.BKTreeFuzzyFinder;
import org.example.wordutils.FuzzyFinder;
import org.example.wordutils.WordChecker;
import org.example.wordutils.WordParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String dictFilePath = args[0];
        String textFilePath = args[1];

        // OPTIONS
        // These chars will be considered the end of sentence, and the following
        // word will be marked as the beginning of sentence.
        Set<Character> sentenceTerminators = Set.of('.', '?', '!');
        // Any non alphabet chars will be considered a word separator, unless specified here
        Set<Character> validWordChars = Set.of('\'');

        // Some words we might want to skip/ignore when checking spelling.
        // If a word matches one of the predicates in this list, the spell checker
        // will always mark that word as correct
        // NOTE: A better solution is a better dictionary. This should be used sparingly, if at all.
        ArrayList<Predicate<Word>> skips = new ArrayList<>(List.of(
                // Skip proper nouns, as defined here
                (Word w) -> !w.isStartSentence && Character.isUpperCase(w.value.charAt(0))
        ));

        // Some words (depending on how we defined a word above) might need to be modified
        // in order to find a match in the dictionary. If a word can't be found, for each function
        // in the list below we will modify the word and try searching in the dictionary again.
        // NOTE: A better solution is a better dictionary. This should be used sparingly, if at all.
        ArrayList<Function<String, String>> tryModify = new ArrayList<>(List.of(
                // Try removing 's (make possessive words non-possessive)
                (String s) -> {
                    int len = s.length();
                    if (len > 2 && s.substring(len-2).equals("'s")) {
                        return s.substring(0, len - 2);
                    }
                    return s;
                }
        ));

        // SETUP DEPENDENCIES
        WordDictionary dictionary = new HashSetWordDictionary(dictFilePath);
        WordParser wordParser = new WordParser(new HashSet<>(validWordChars), new HashSet<>(sentenceTerminators));
        WordChecker wordChecker = new WordChecker(dictionary, skips, tryModify);
        FuzzyFinder fuzzyFinder = new BKTreeFuzzyFinder();

        // CHECK THE TEXT FOR MISSPELLED WORDS
        wordParser.loadFile(textFilePath);
        while (wordParser.hasNext()) {
            Word word = wordParser.next();
            if (wordChecker.isMisspelled(word)) {
                ArrayList<String> suggestions = fuzzyFinder.fuzzyFind(word.value);
                TerminalHelper.printMisspelledWord(word, suggestions);
            }
        }
    }
}
package org.example;

import org.example.dictionaries.HashSetWordDictionary;
import org.example.dictionaries.WordDictionary;
import org.example.models.Word;
import org.example.terminal.TerminalHelper;
import org.example.wordutils.*;
import org.example.wordutils.fuzzyfinders.BKTreeFuzzyFinder;
import org.example.wordutils.fuzzyfinders.FuzzyFinder;
import org.example.wordutils.fuzzyfinders.SymSpellFuzzyFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        // Check that we have 2 args
        if (args.length != 2) {
            System.out.println("2 args required: [dictionary file].txt [text to check].txt");
            System.exit(1);
        }

        // Check that both args look like txt files.
        int arg1Offset = args[0].length() - 4;
        int arg2Offset = args[1].length() - 4;
        if (!args[0].startsWith(".txt", arg1Offset) || !args[1].startsWith(".txt", arg2Offset)) {
            System.out.println("args should end in .txt");
            System.exit(1);
        }
        String dictFilePath = args[0];
        String textFilePath = args[1];

        // OPTIONS below we define the configurable parts of the spellchecker
        // Any non alphabet chars will be considered a word separator, unless specified here
        Set<Character> validWordChars = Set.of('\'', '’');

        // These chars will be considered the end of sentence, and the following
        // word will be marked as the beginning of sentence.
        Set<Character> sentenceTerminators = Set.of('.', '?', '!');

        // Some words we might want to skip/ignore when checking spelling.
        // If a word matches one of the predicates in this list, the spell checker
        // will always mark that word as correct
        // NOTE: A better solution is a larger dictionary. This should be used sparingly
        ArrayList<Predicate<Word>> skips = new ArrayList<>(List.of(
                // Skip proper nouns, as defined here
                (Word w) -> !w.isStartSentence && Character.isUpperCase(w.value.charAt(0))
        ));

        // Some words (depending on how we defined a word above) might need to be modified
        // in order to find a match in the dictionary. If a word can't be found, for each function
        // in the list below we will modify the word and try searching in the dictionary again.
        // NOTE: A better solution is a bigger dictionary. This should be used sparingly
        ArrayList<Function<String, String>> tryModify = new ArrayList<>(List.of(
                // Try removing 's (make possessive words non-possessive)
                (String s) -> {
                    int len = s.length();
                    if (len > 2 && (s.substring(len-2).equals("'s") || s.substring(len-2).equals("’s"))) {
                        return s.substring(0, len - 2);
                    }
                    return s;
                },
                (String s) -> {
                    return s.replace("'", "");
                }
        ));
        // Edit distance
        int editDistance = 2;



        // SETUP DEPENDENCIES
        // Holds set of dictionary words
        WordDictionary dictionary = new HashSetWordDictionary(dictFilePath);

        // Parse txt file into individual words
        WordParser wordParser = new WordParser(textFilePath, validWordChars, sentenceTerminators);

        // Checks if word is in dictionary while considering exceptions, unique cases, etc.
        WordChecker wordChecker = new WordChecker(dictionary, skips, tryModify);

        // Find suggestions for misspelled words
        FuzzyFinder fuzzyFinder = new SymSpellFuzzyFinder(dictionary, editDistance);
        //FuzzyFinder fuzzyFinder = new BKTreeFuzzyFinder(dictionary, EditDistance::levenshtein, editDistance); // Suggestions for words not found in dictionary


        // CHECK THE TEXT FOR MISSPELLED WORDS
        while (wordParser.hasNext()) {
            Word word = wordParser.next();
            if (wordChecker.isMisspelled(word)) {
                ArrayList<String> suggestions = fuzzyFinder.fuzzyFind(word.value);
                TerminalHelper.printMisspelledWord(word, suggestions);
            }
        }
        long finish = System.currentTimeMillis();
        System.out.println("\nFinished in " + (double)(finish - start) / 1000 + " seconds");
    }
}
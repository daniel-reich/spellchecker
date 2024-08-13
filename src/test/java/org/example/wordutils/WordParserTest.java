package org.example.wordutils;

import org.example.dictionaries.HashSetWordDictionary;
import org.example.models.Word;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class WordParserTest {

    Set<Character> sentenceTerminators = Set.of('.', '?', '!');
    // Any non alphabet chars will be considered a word separator, unless specified here
    Set<Character> validWordChars = Set.of('\'', '’');
    ArrayList<Predicate<Word>> skips = new ArrayList<>(List.of(
            // Skip proper nouns, as defined here
            (Word w) -> !w.isStartSentence && Character.isUpperCase(w.value.charAt(0))
    ));

    ArrayList<Function<String, String>> tryModify = new ArrayList<>(List.of(
            // Try removing 's (make possessive words non-possessive)
            (String s) -> {
                int len = s.length();
                if (len > 2 && (s.substring(len-2).equals("'s") || s.substring(len-2).equals("’s"))) {
                    return s.substring(0, len - 2);
                }
                return s;
            }
    ));

    @Test
    void canParseWords() {
        Queue<String> words = new LinkedList<>(List.of(
                "that's",
                "that’s",
                "hello",
                "lots",
                "Of",
                "weird",
                "spacing",
                "and",
                "characters",
                "but",
                "can",
                "the",
                "parser",
                "handle",
                "it",
                "I",
                "guess",
                "we'll",
                "see"
        ));
        WordParser wordParser = new WordParser("test.txt", new HashSet<>(validWordChars), new HashSet<>(sentenceTerminators));

        while (wordParser.hasNext()) {
            Word word = wordParser.next();
            assertEquals(words.remove(), word.value);
        }
    }
}
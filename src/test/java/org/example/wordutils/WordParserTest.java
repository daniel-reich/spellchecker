package org.example.wordutils;

import org.example.dictionaries.HashSetWordDictionary;
import org.example.models.Word;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class WordParserTest {
    @Test
    void test() {
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
        HashSetWordDictionary dictionary = new HashSetWordDictionary("test.txt");
        WordParser wordParser = new WordParser(new HashSet<>(validWordChars), new HashSet<>(sentenceTerminators));
        WordChecker wordChecker = new WordChecker(dictionary, skips, tryModify);
        while (wordParser.hasNext()) {
            wordParser.next();
        }

    }
}
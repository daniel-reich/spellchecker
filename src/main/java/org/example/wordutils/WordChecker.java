package org.example.wordutils;



import org.example.dictionaries.WordDictionary;
import org.example.models.Word;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class WordChecker {

    private final WordDictionary dictionary;
    private final List<Predicate<Word>> skips;
    private final List<Function<String, String>> tryModify;

    /**
     * Constructs a new {@code WordChecker} to check if a word is in the dictionary
     * Allows configuration to skip words, or modify certain words before searching
     * For example - skip proper nouns, remove 's from the end of a word
     *
     * @param dictionary Set of words that represent all correctly spelled words
     * @param skips list of functions that determine if a word should be skipped
     * @param tryModify if a word is not found, for each function in this list the word will be modified and searched again until a match is found
     */
    public WordChecker(WordDictionary dictionary, List<Predicate<Word>> skips, List<Function<String, String>> tryModify) {
        this.dictionary = dictionary;
        this.skips = skips;
        this.tryModify = tryModify;
    }

    public boolean isMisspelled(Word word) {
        // If the word matches one of our "skip" predicates, just return false (the word is spelled correctly)
        for (Predicate<Word> skipFunc : this.skips) {
            if (skipFunc.test(word)) {
                return false;
            }
        }
        // If word is in dictionary return false (the word is spelled correctly)
        boolean isFound = this.dictionary.contains(word.value.toLowerCase());
        if (isFound) {
            return false;
        }
        // If word is not found, try modifying it according to the list of tryModify functions
        // As soon as a match is found, return false (the word is spelled correctly)
        for (Function<String, String> tryModifyFunc: this.tryModify) {
            String modifiedWord = tryModifyFunc.apply(word.value);
            if (!word.value.equals(modifiedWord)) {
                isFound = this.dictionary.contains(modifiedWord.toLowerCase());
                if (isFound) {
                    return false;
                }
            }
        }
        // The word is definitely misspelled :(
        return true;
    }
}

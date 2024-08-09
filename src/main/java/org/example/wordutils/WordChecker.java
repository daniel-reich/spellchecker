package org.example.wordutils;



import org.example.dictionaries.WordDictionary;
import org.example.models.Word;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class WordChecker {

    private WordDictionary dictionary;
    private List<Predicate<Word>> skips;
    private List<Function<String, String>> tryModify;

    public WordChecker(WordDictionary dictionary, List<Predicate<Word>> skips, List<Function<String, String>> tryModify) {
        this.dictionary = dictionary;
        this.skips = skips;
        this.tryModify = tryModify;
    }

    public boolean isMisspelled(Word word) {
        for (Predicate<Word> skipFunc : this.skips) {
            if (skipFunc.test(word)) {
                return false;
            }
        }
        // TODO PUT LOWER IN DICT?
        boolean isFound = this.dictionary.contains(word.value.toLowerCase());
        if (isFound) {
            return false;
        }
        for (Function<String, String> tryModifyFunc: this.tryModify) {
            String modifiedWord = tryModifyFunc.apply(word.value);
            if (!word.value.equals(modifiedWord)) {
                isFound = this.dictionary.contains(modifiedWord);
                if (isFound) {
                    return false;
                }
            }
        }
        return true;
    }
}

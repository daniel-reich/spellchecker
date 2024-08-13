package org.example.wordutils.fuzzyfinders;

import org.example.dictionaries.WordDictionary;
import org.example.wordutils.EditDistanceFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class SymSpellFuzzyFinder implements FuzzyFinder {

    private HashMap<String, ArrayList<String>> fuzzyFinder;
    private final int editDistance;

    public SymSpellFuzzyFinder(WordDictionary dictionary, int editDistance) {
        this.editDistance = editDistance;
        this.loadDictionary(dictionary);
    }

    public HashSet<String> deletedCharacterPermutations(String word, int deletions) {
        ArrayList<String> latestResults = this.deleteSingleCharacterPermutations(word);
        HashSet<String> permutations = new HashSet<String>(latestResults);
        for (int i = 0; i < deletions - 1; i++) {
            latestResults = latestResults.stream().map(this::deleteSingleCharacterPermutations).flatMap(List::stream).collect(Collectors.toCollection(ArrayList::new));
            permutations.addAll(latestResults);
        }
        return permutations;
    }

    public ArrayList<String> deleteSingleCharacterPermutations(String word) {
        ArrayList<String> permutations = new ArrayList<String>();
        permutations.add(word);

        for (int i=0; i < word.length(); i++) {
            StringBuilder w = new StringBuilder(word);
            w.deleteCharAt(i);
            if (!w.isEmpty()) {
                permutations.add(w.toString());
            }
        }
        return permutations;
    }

    private void loadDictionary(WordDictionary wordDictionary) {
        this.fuzzyFinder = new HashMap<String, ArrayList<String>>();
        for (String word: wordDictionary.allWords()) {
            for (String permutation: this.deletedCharacterPermutations(word, this.editDistance)) {
                if (fuzzyFinder.containsKey(permutation)) {
                    fuzzyFinder.get(permutation).add(word);
                } else {
                    fuzzyFinder.put(permutation, new ArrayList<>(List.of(word)));
                }
            }

        }
    }


    @Override
    public ArrayList<String> fuzzyFind(String searchTerm) {
        HashSet<String> suggestions = new HashSet<>();
        HashSet<String> searchTerms = this.deletedCharacterPermutations(searchTerm, this.editDistance);
        searchTerms.add(searchTerm);
        for (String permutation: searchTerms) {
            if (fuzzyFinder.containsKey(permutation)) {
                suggestions.addAll(fuzzyFinder.get(permutation).stream().filter(s -> EditDistanceFunctions.levenshtein(searchTerm, s) <= this.editDistance).toList());
                //suggestions.addAll(fuzzyFinder.get(permutation));
            }
        }
        return new ArrayList<>(suggestions);
    }


}

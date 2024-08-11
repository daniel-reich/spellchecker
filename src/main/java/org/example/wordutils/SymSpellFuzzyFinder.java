package org.example.wordutils;

import org.example.dictionaries.HashSetWordDictionary;
import org.example.dictionaries.WordDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class SymSpellFuzzyFinder implements FuzzyFinder {

    HashMap<String, ArrayList<String>> fuzzyFinder;
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

    public void loadDictionary(HashSetWordDictionary wordDictionary) {
        this.fuzzyFinder = new HashMap<String, ArrayList<String>>();
        for (String word: wordDictionary.dictionary) {
            // TODO parameterize the distance
            for (String permutation: this.deletedCharacterPermutations(word, 2)) {
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
        ArrayList<String> suggestions = new ArrayList<>();
        // TODO Parameterize this
        HashSet<String> searchTerms = this.deletedCharacterPermutations(searchTerm, 2);
        searchTerms.add(searchTerm);
        for (String permutation: searchTerms) {
            if (fuzzyFinder.containsKey(permutation)) {
                suggestions.addAll(fuzzyFinder.get(permutation));
            }
        }
        return suggestions;
    }
}

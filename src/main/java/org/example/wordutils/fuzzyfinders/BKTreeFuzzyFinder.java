package org.example.wordutils.fuzzyfinders;

import org.example.dictionaries.WordDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

public class BKTreeFuzzyFinder implements FuzzyFinder {

    private BKNode root;
    private final BiFunction<String, String, Integer> editDistanceFunc;
    private final int searchEditDistance;

    public BKTreeFuzzyFinder(WordDictionary wordDictionary, BiFunction<String, String, Integer> editDistanceFunc, int searchEditDistance) {
        this.editDistanceFunc = editDistanceFunc;
        this.searchEditDistance = searchEditDistance;
        this.loadDictionary(wordDictionary);
    }

    @Override
    public ArrayList<String> fuzzyFind(String searchTerm) {
        return this.root.find(searchTerm, this.searchEditDistance);
    }

    private void loadDictionary(WordDictionary wordDictionary) {
        for(String word: wordDictionary.allWords()) {
            if (this.root == null) {
                this.root = new BKNode(word, this.editDistanceFunc);
            } else {
                this.root.insert(word);
            }
        }
    }
}

class BKNode {
    public String value;
    private final HashMap<Integer, BKNode> children;
    private final BiFunction<String, String, Integer> editDistanceFunc;

    public BKNode(String value, BiFunction<String, String, Integer> editDistanceFunc) {
        this.value = value;
        this.children = new HashMap<>();
        this.editDistanceFunc = editDistanceFunc;
    }

    public void insert(String word) {
        int distance = this.editDistanceFunc.apply(this.value, word);
        if (this.children.containsKey(distance)) {
            this.children.get(distance).insert(word);
        } else {
            this.children.put(distance, new BKNode(word, this.editDistanceFunc));
        }
    }

    public ArrayList<String> find(String searchTerm, int maxDistance) {
        ArrayList<String> matches = new ArrayList<>();
        int distance = this.editDistanceFunc.apply(this.value, searchTerm);
        if (distance <= maxDistance) {
            matches.add(this.value);
        }
        List<Integer> validChildren = this.children
                .keySet()
                .stream()
                .filter(d -> d >= (maxDistance - distance) && d <= maxDistance + distance).toList();

        for (int child: validChildren) {
            matches.addAll(this.children.get(child).find(searchTerm, maxDistance));
        }
        return matches;
    }
}

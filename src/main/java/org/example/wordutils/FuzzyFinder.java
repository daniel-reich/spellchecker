package org.example.wordutils;

import org.example.dictionaries.HashSetWordDictionary;

import java.util.ArrayList;

public interface FuzzyFinder {
    public ArrayList<String> fuzzyFind(String searchTerm);
    public void loadDictionary(HashSetWordDictionary wordDictionary);
}

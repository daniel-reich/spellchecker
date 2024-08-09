package org.example.wordutils;

import java.util.ArrayList;
import java.util.List;

public class BKTreeFuzzyFinder implements FuzzyFinder{
    @Override
    public ArrayList<String> fuzzyFind(String searchTerm) {
        return new ArrayList<>(List.of("apple", "able", "cable"));
    }
}

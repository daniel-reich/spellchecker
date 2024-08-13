package org.example.wordutils.fuzzyfinders;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.example.dictionaries.WordDictionary;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SymSpellFuzzyFinderTest {

    WordDictionary dummyDict = new DummyDict();

    @Test
    void symmetricDeletePermutations_distance1()
    {
        String word = "software";
        int distance = 1;
        SymSpellFuzzyFinder fuzzyFinder = new SymSpellFuzzyFinder(dummyDict, distance);
        HashSet<String> permutations = fuzzyFinder.deletedCharacterPermutations(word, distance);

        assertEquals(this.calculateNumberPermutations(word.length(), distance), permutations.size());
    }

    @Test
    void symmetricDeletePermutations_distance2() {
        String word = "software";
        int distance = 2;
        SymSpellFuzzyFinder fuzzyFinder = new SymSpellFuzzyFinder(dummyDict, distance);
        HashSet<String> permutations = fuzzyFinder.deletedCharacterPermutations(word, distance);
        assertEquals(this.calculateNumberPermutations(word.length(), distance), permutations.size());
    }

    @Test
    void symmetricDeletePermutations_distance3() {
        String word = "software";
        int distance = 3;
        SymSpellFuzzyFinder fuzzyFinder = new SymSpellFuzzyFinder(dummyDict, distance);
        HashSet<String> permutations = fuzzyFinder.deletedCharacterPermutations(word, distance);

        assertEquals(this.calculateNumberPermutations(word.length(), distance), permutations.size());
    }

    private int calculateNumberPermutations(int wordLength, int deletes) {
        int total = 1; // The original word counts as 1
        for (int i = 1; i <= deletes; i++) {
            total += CombinatoricsUtils.factorial(wordLength) /
                    CombinatoricsUtils.factorial(i)/
                    CombinatoricsUtils.factorial(wordLength-i);
        }
        return total;
    }
}

class DummyDict implements WordDictionary {

    @Override
    public void load(String filepath) {

    }

    @Override
    public boolean contains(String word) {
        return false;
    }

    @Override
    public Iterable<String> allWords() {
        return new ArrayList<>();
    }
}
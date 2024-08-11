package org.example.wordutils;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SymSpellFuzzyFinderTest {
    @Test
    void test()
    {
        String word = "software";
        int distance = 1;
        SymSpellFuzzyFinder fuzzyFinder = new SymSpellFuzzyFinder();
        HashSet<String> permutations = fuzzyFinder.deletedCharacterPermutations(word, distance);

        assertEquals(this.calculateNumberPermutations(word.length(), distance), permutations.size());
    }

    @Test
    void test2() {
        String word = "software";
        int distance = 2;
        SymSpellFuzzyFinder fuzzyFinder = new SymSpellFuzzyFinder();
        HashSet<String> permutations = fuzzyFinder.deletedCharacterPermutations(word, distance);
        assertEquals(this.calculateNumberPermutations(word.length(), distance), permutations.size());
    }

    @Test
    void test3() {
        String word = "software";
        int distance = 3;
        SymSpellFuzzyFinder fuzzyFinder = new SymSpellFuzzyFinder();
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
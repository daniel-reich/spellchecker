package org.example.wordutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BKTreeFuzzyFinderTest {
    @Test
    void test3() {
        BKTreeFuzzyFinder fuzzyFinder = new BKTreeFuzzyFinder();
        assertEquals(6, fuzzyFinder.calculateLevenshteinDistance("polynomial", "exponential"));
    }

    @Test
    void test4() {
        BKTreeFuzzyFinder fuzzyFinder = new BKTreeFuzzyFinder();
        assertEquals(3, fuzzyFinder.calculateLevenshteinDistance("britain", "brin"));
    }
}
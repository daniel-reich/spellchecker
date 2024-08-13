package org.example.wordutils.fuzzyfinders;

import org.example.wordutils.EditDistance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
// TODO move these tests to correct folder
class BKTreeFuzzyFinderTest {
    @Test
    void canCalculateLevenshteinDistance() {
        Assertions.assertEquals(6, EditDistance.levenshtein("polynomial", "exponential"));
        assertEquals(3, EditDistance.levenshtein("britain", "brin"));
        assertEquals(1, EditDistance.levenshtein("banker", "baker"));
        assertEquals(2, EditDistance.levenshtein("banker", "bakner"));
    }
}
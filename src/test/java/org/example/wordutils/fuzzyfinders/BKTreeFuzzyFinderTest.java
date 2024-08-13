package org.example.wordutils.fuzzyfinders;

import org.example.wordutils.EditDistanceFunctions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
// TODO move these tests to correct folder
class BKTreeFuzzyFinderTest {
    @Test
    void canCalculateLevenshteinDistance() {
        Assertions.assertEquals(6, EditDistanceFunctions.levenshtein("polynomial", "exponential"));
        assertEquals(3, EditDistanceFunctions.levenshtein("britain", "brin"));
        assertEquals(1, EditDistanceFunctions.levenshtein("banker", "baker"));
        assertEquals(2, EditDistanceFunctions.levenshtein("banker", "bakner"));
    }
}
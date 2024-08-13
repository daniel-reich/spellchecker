package org.example.dictionaries;

public interface WordDictionary {
 void load(String filepath);
 boolean contains(String word);
 Iterable<String> allWords();
}
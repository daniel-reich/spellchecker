package org.example.dictionaries;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class HashSetWordDictionary implements WordDictionary {

    private HashSet<String> dictionary;

    public HashSetWordDictionary(String filepath) {
        this.load(filepath);
    }

    @Override
    public void load(String filePath) {
        this.dictionary = new HashSet<>();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                dictionary.add(word.toLowerCase());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not create a dictionary from " + filePath + ". File not found");
            System.exit(1);
        }
    }

    @Override
    public boolean contains(String word) {
        return this.dictionary.contains(word);
    }

    @Override
    public Iterable<String> allWords() {
        return this.dictionary;
    }
}

package dictionaries;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class HashSetWordDictionary implements WordDictionary {

    private HashSet<String> dictionary;
    public HashSetWordDictionary(String filepath) {
        this.dictionary = new HashSet<>();
        this.load(filepath);
    }

    @Override
    public void load(String filePath) {
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                dictionary.add(word);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public boolean contains(String word) {
        return this.dictionary.contains(word);
    }
}

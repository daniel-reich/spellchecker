import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String dictFilePath = args[0];
        String textFilePath = args[1];
        HashSet<String> dictionary = new HashSet<>();
        try {
            File myObj = new File(dictFilePath);
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

        try {
            File myObj = new File(textFilePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine()
                        .replace(".", "")
                        .replace(",", "")
                        .replace("?", "");
                String[] words = line.split("\s");
                for(String word: words) {
                    if (!dictionary.contains(word.toLowerCase())) {
                        System.out.println("Misspelled!  ->  " + word);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
}
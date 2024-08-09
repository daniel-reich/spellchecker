package dictionaries;

public interface WordDictionary {
 void load(String filepath);
 boolean contains(String word);
}
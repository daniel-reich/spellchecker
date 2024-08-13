# Spellchecker

### Dependencies
- Java JDK (openjdk-22)
- Maven
### Build
- Navigate to root of this project
- run `mvn package`
### Run
- From root of this project, run `java -cp target/spellchecker-1.0-SNAPSHOT.jar org.example.Main path/to/dictionary.txt path/to/text-to-check.txt`

## Implementation
If a word is not found in the dictionary, the spellchecker will look for all words within levenshtein distance 2 and return them as suggestions. This lookup is made more efficient using a BK Tree.

There is also a light implementation of the [SymSpell algorithm](https://wolfgarbe.medium.com/1000x-faster-spelling-correction-algorithm-2012-8701fcd87a5f), you can use this implementation by un-commenting [line 92 in src/main/java/org/example
/Main.java
](https://github.com/daniel-reich/spellchecker/blob/main/src/main/java/org/example/Main.java#L92) and commenting out the following line.

When compared to the BK Tree, this SymSpell implementation takes longer to preprocess the dictionary, but looking up words is much faster. For this reason I would recommend using the BK tree if the dictionary is exceptionally large, and/or the text to check is relatively small. In contrast, the SymSpell algorithm would better be suited for situations when the faster lookup time offsets the slower preprocessing time, or in other words when there is so much text to check that it offsets the cost of preprocessing the dictionary.

### Test files
#### Included in this repo are two dictionaries:
- `dictionary.txt` ~170K words
- `big-dictionary.txt` ~455k words
#### There are also a few text files to check
- `wiki.txt`
- `random.txt`
- `moon_chatgpt.txt`

### Example output
`java -cp target/spellchecker-1.0-SNAPSHOT.jar org.example.Main dictionary.txt moon_chatgpt.txt`

<img width="992" alt="image" src="https://github.com/user-attachments/assets/27b169f9-d2e4-431b-be3e-3ab4bb59473e">


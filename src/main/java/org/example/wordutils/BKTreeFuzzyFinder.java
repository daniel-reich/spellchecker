package org.example.wordutils;

import org.example.dictionaries.HashSetWordDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BKTreeFuzzyFinder implements FuzzyFinder{
    private BKNode root;
    @Override
    public ArrayList<String> fuzzyFind(String searchTerm) {
        return this.root.find(searchTerm, 2);
    }

    @Override
    public void loadDictionary(HashSetWordDictionary wordDictionary) {
        for(String word: wordDictionary.dictionary) {
            if (this.root == null) {
                this.root = new BKNode(word);
            } else {
                this.root.insert(word);
            }
        }
    }

    public static int editDistance(String a,String b){

        int m=a.length(),n=b.length();

        int[][] dp=new int[m+1][n+1];

        for(int i=0;i<=m;i++)dp[i][0]=i;for(int j=0;j<=n;j++)dp[0][j]=j;for(int i=1;i<=m;i++){

            for(int j=1;j<=n;j++){

                if(a.charAt(i-1)!=b.charAt(j-1)){

                    dp[i][j]=Math.min( Math.min( 1 + dp[i-1][j], // deletion
                                    1 + dp[i][j-1]), // insertion
                            1 + dp[i-1][j-1] // replacement
                    );


                }else{

                    dp[i][j]=dp[i-1][j-1];

                }
            }

        }
        return dp[m][n];
    }


    public static int calculateLevenshteinDistance(String word1, String word2) {
        int m = word1.length() + 1;
        int n = word2.length() + 1;
        int[][] table = new int[n][m];
        for (int i = 0; i < m; i ++) {
            table[0][i] = i;

        }
        for (int j = 0; j < n; j++) {
            table[j][0] = j;
        }
        for(int j = 1; j < n; j++) {
            for(int i = 1; i < m; i++) {
                int charDiff = charDifference(word1, i-1, word2, j-1);
                if (charDiff == 1) {
                    table[j][i] = lowestNeighbor(j, i, table) + charDiff;
                } else {
                    table[j][i] = table[j-1][i-1];
                }

            }
        }
        return table[n-1][m-1];
    }

    private static int charDifference(String word1, int index1, String word2, int index2) {
        return (word1.charAt(index1) == word2.charAt(index2)) ? 0 : 1;
    }

    private static int lowestNeighbor(int row, int col, int[][] table) {
        return min(table[row-1][col], table[row-1][col-1], table[row][col-1]);
    }

    private static int min(int num1, int num2, int num3) {
        return Math.min(Math.min(num1, num2), num3);
    }
}

class BKNode {
    public String value;
    private HashMap<Integer, BKNode> children;

    public BKNode(String value) {
        this.value = value;
        this.children = new HashMap<>();
    }

    public void insert(String word) {
        int distance = BKTreeFuzzyFinder.calculateLevenshteinDistance(this.value, word);
        if (this.children.containsKey(distance)) {
            this.children.get(distance).insert(word);
        } else {
            this.children.put(distance, new BKNode(word));
        }
    }

    public ArrayList<String> find(String searchTerm, int maxDistance) {
        ArrayList<String> matches = new ArrayList<>();
        int distance = BKTreeFuzzyFinder.calculateLevenshteinDistance(this.value, searchTerm);
        if (distance <= maxDistance) {
            matches.add(this.value);
        }
        List<Integer> validChildren = this.children
                .keySet()
                .stream()
                .filter(d -> d >= (maxDistance - distance) && d <= maxDistance + distance).toList();

        for (int child: validChildren) {
            matches.addAll(this.children.get(child).find(searchTerm, maxDistance));
        }
        return matches;
    }
}

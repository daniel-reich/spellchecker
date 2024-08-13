package org.example.wordutils;

public class EditDistance {

    public static int levenshtein(String word1, String word2) {
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

import edu.princeton.cs.algs4.*;

import java.util.Set;

/**
 * Created by tongs on 3/13/2017.
 */
public class BoggleSolver {

    private int width, height;
    private TrieST<Integer> stringTree;
    private TrieSET testTree;
    private neatTries tree;
    private SET<String> validWord;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
        validWord = new SET<>();
        tree = new neatTries();
        int i = 0;

        for(String s:dictionary){

            tree.put(s,i);
            i++;
        }
    }
    private void searchwords(BoggleBoard board){
        int row = board.rows();
        int col = board.cols();
        boolean[][] marked = new boolean[row][col];
        for(int i = 0;i<row;i++){
            for(int j= 0;j<col;j++){
                marked[i][j] = false;
            }
        }
        //dfs(board,0,0,marked,"");
        for(int i = 0;i<row;i++){ // recursive DFS for every starting vertex
             for(int j= 0;j<col;j++){
                dfs(board,i,j,marked,"");
           }
        }
    }
    // use a simpler and neater DFS method to find all words in single boggle board
    private void dfs(BoggleBoard board, int i, int j, boolean[][] marked, String word) {
        // i = row, j = col
        // word = chosen word.
        int row = board.rows();
        int col = board.cols();
        if(marked[i][j]){  // if the vertex is marked, skip
            return;
        }
        if(!tree.contains(word))  // if the word does not have a prefix in the dic, skip
        {
            return;
        }
        marked[i][j] = true;  // marked is the vertex has been processed
        if(word.length() > 2) {
           validWord.add(word);  // add word which has points
        }
        word = word + board.getLetter(i,j);

        if(i>=1){ // the vertex is not in the upper row

            dfs(board, i - 1, j, marked, word);
        }
        if(i < row-1){ // the vertex is not in the lower row

            dfs(board, i + 1, j, marked, word);
        }
        if(j>=1){

            dfs(board, i, j - 1, marked, word);
        }
        if(j < col-1){

            dfs(board, i, j + 1, marked, word);
        }

        marked[i][j] = false;
        // for every recursive process, the marked symbol should be true.
        // but for different thread, the marked should be false.
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    //public Iterable<String> getAllValidWords(BoggleBoard board)

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        int i = word.length();
        int score;
        if(i >= 8){ score = 11;}
        else if(i == 7){ score = 5; }
        else if(i == 6){ score = 3; }
        else if(i == 5){ score = 2; }
        else if(i == 3 || i == 4) { score = 1; }
        else{ score = 0; }
        return score;
    }
    public SET<String> words(){
        return validWord;
    }
    public static void main(String[] args){
        char[][] a =  {
                { 'O', 'A', 'D', 'Y' },
                { 'I', 'B', 'S', 'F' },
                { 'T', 'R', 'T', 'O' },
                { 'C', 'A', 'B', 'W' }
        };
        BoggleBoard board = new BoggleBoard(a);
        In in = new In("dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver temp = new BoggleSolver(dictionary);
        temp.searchwords(board);
        for(String s: temp.words()){
            StdOut.println(s);
        }
    }

}


import edu.princeton.cs.algs4.*;

import java.lang.Iterable;

/**
 * Created by tongs on 1/13/2017.
 */


public class Board {
    private int dimension;
    private int[][] blocks;

    public Board(int[][] b)           // construct a board from an n-by-n array of blocks
    {
        if (b == null){
            throw new NullPointerException("no argument passed");
        }
        this.dimension = b.length;
        this.blocks = b;
    }
    // (where blocks[i][j] = block in row i, column j)
    public int dimension()                /** TESTED */ // board dimension n
    {
        return dimension;
    }
    public int hamming()                  /** TESTED */  // number of blocks out of place
    {
        int rightNum = 0;
        for(int i = 0;i<dimension;i++)
        {
            for(int j = 0;j<dimension;j++){
                if(blocks[i][j] == 0)
                {
                    continue;
                }
                if(blocks[i][j]-1 != i*dimension + j){
                    rightNum++;
                }
            }
        }
        return rightNum;
    }



    public int manhattan()                /** TESTED */ // sum of Manhattan distances between blocks and goal
   {
        int allDistance = 0;
        int temp;
        for(int i = 0;i<dimension;i++)
            for(int j = 0;j<dimension;j++){
                if(blocks[i][j] == 0)
                {
                    continue;
                }
                if(blocks[i][j] != i*dimension + j + 1){
                    temp = blocks[i][j] - 1;
                    allDistance = allDistance + Math.abs(temp/dimension - i) + Math.abs(temp%dimension - j);
                }
            }
        return allDistance;
    }

    public boolean isGoal()              /** TESTED */  //  is this board the goal board?
    {
        /*for(int i = 0;i<dimension;i++)
        {
            for(int j = 0;j<dimension;j++){
                if(i == dimension - 1&&j ==dimension - 1)
                {
                    if(blocks[i][j] != 0){
                        return false;
                    }
                }
                else {
                    if (blocks[i][j] - 1 != i * dimension + j) {
                        return false;
                    }
                }
            }
        }*/
        return hamming() == 0;
    }
    private int[][] copyboard(int[][] origin){
        int[][] temp = new int[origin.length][origin.length];
        for (int x = 0;x<origin.length;x++){
            for (int y=0;y<origin.length;y++){
                temp[x][y] = origin[x][y];
            }
        }
        return temp;
    }
    private Board swapUpper(int i,int j){
        int [][] b = copyboard(blocks);
        int temp = b[i][j];
        b[i][j] = b[i-1][j];
        b[i-1][j] = temp;
        Board newBoard = new Board(b);

        return newBoard;
    }
    private Board swapLower(int i,int j){
        int [][] b = copyboard(blocks);
        int temp = b[i][j];
        b[i][j] = b[i+1][j];
        b[i+1][j] = temp;
        Board newBoard = new Board(b);

        return newBoard;
    }
    private Board swapLeft(int i,int j){
        int [][] b = copyboard(blocks);
        int temp = b[i][j];
        b[i][j] = b[i][j-1];
        b[i][j-1] = temp;
        Board newBoard = new Board(b);

        return newBoard;
    }
    private Board swapRight(int i,int j){
        int [][] b = copyboard(blocks);
        int temp = b[i][j];
        b[i][j] = b[i][j+1];
        b[i][j+1] = temp;
        Board newBoard = new Board(b);

        return newBoard;
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        /*boolean successSwap = false;
        Board blockTwin = new Board(blocks);
        int x = 0;
        int y = 0;
        for(int i = 0;i<dimension;i++){
            for(int j = 0;j<dimension;j++) {
                if (blocks[i][j] == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }


        while(!successSwap) {
            int randomAlter = StdRandom.uniform(1,5);
            switch (randomAlter) {
                case 1: {
                    if(x==0){
                        break;
                    }
                    else{
                        blockTwin = swapUpper(x,y);
                        successSwap = true;
                    }
                    break;
                }


                case 2: {
                    if(x==dimension-1){
                        break;
                    }
                    else{
                        blockTwin = swapLower(x,y);
                        successSwap = true;
                    }
                    break;
                }

                case 3: {
                    if(y==0){
                        break;
                    }
                    else{
                        blockTwin = swapLeft(x,y);
                        successSwap = true;
                    }
                    break;
                }

                case 4:{
                    if(y == dimension-1){
                        break;
                    }
                    else{
                        blockTwin = swapRight(x,y);
                        successSwap = true;
                    }
                    break;
                }
            }
        }

        return blockTwin;*/

        int row = 0;
        boolean haveZero = false;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    haveZero = true;
                    break;
                }
            }
            if(haveZero){
                row++;
                break;
            }
            else{
                break;
            }
        }
        return swapRight(row,0);
    }


    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.blocks[i][j] != that.blocks[i][j])
                    return false;
            }
        }
        return true;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    public Iterable<Board> neighbors() {    //return all neighbor boards
        Queue<Board> q = new Queue<>();
        int xBlank = 0;
        int yBlank = 0;
        for(int i = dimension - 1;i>=0;i--){
            for(int j = dimension - 1;j>=0;j--)
            {
                if(blocks[i][j] == 0)
                {
                    xBlank = i;
                    yBlank = j;
                }
            }
        }
        if(xBlank > 0){
            q.enqueue(swapUpper(xBlank,yBlank));
        }
        if(xBlank < dimension-1){

            q.enqueue(swapLower(xBlank,yBlank));
        }
        if(yBlank > 0){
            q.enqueue(swapLeft(xBlank,yBlank));
        }
        if(yBlank < dimension - 1){
            q.enqueue(swapRight(xBlank,yBlank));
        }
        return q;
    }

   /*public static void main(String[] args) // unit tests (not graded)
    {
        int dimension = 4;
        int blocks[][] = new int[dimension][dimension];

        for (int i = 0;i<dimension;i++)
        {
            for(int j = 0;j<dimension;j++)
            {
                blocks[i][j] = i*dimension + j + 1;
            }
        }
        blocks[1][1] = 0;
        blocks[3][0] = 13;
        blocks[3][1] = 14;
        blocks[3][2] = 15;
        blocks[3][3] = 6;
        Board test = new Board(blocks);
        StdOut.println(test.isGoal());
        StdOut.println(test.toString());
        StdOut.println(test.hamming());
        StdOut.println(test.manhattan());
        StdOut.println(test.twin().toString());
    }*/
}



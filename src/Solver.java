/**
 * Created by tongs on 1/15/2017.
 */
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;

public class Solver{
    private int moves;
    private boolean solvable = true;
    private SearchNode node = null;
    private SearchNode nodeTwin = null;
    private SearchNode solutionNode;
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null){
            throw new NullPointerException("no argument passed");
        }
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pqTwin = new MinPQ<>();

        //this.node = new SearchNode(initial,new SearchNode(initial,null));
        //this.nodeTwin = new SearchNode(initial.twin(),new SearchNode(initial,null));
        this.node = new SearchNode(initial,null);
        //StdOut.println("test22");
        this.nodeTwin = new SearchNode(initial.twin(),null);

        //StdOut.println("test twin" + initial.twin());
        pq.insert(node);
        pqTwin.insert(nodeTwin);

        while(!node.board.isGoal() && !nodeTwin.board.isGoal()){


            node = pq.delMin();
            nodeTwin = pqTwin.delMin();
            for(Board t:node.board.neighbors())
            {
                if(node.preNode == null|| !t.equals(node.preNode.board)) {
                    SearchNode pqNext = new SearchNode(t, node);
                    pq.insert(pqNext);
                }
            }
            for(Board q:nodeTwin.board.neighbors()){
                if(nodeTwin.preNode == null|| !q.equals(nodeTwin.preNode.board)) {
                    SearchNode pqNextTwin = new SearchNode(q, nodeTwin);
                    pqTwin.insert(pqNextTwin);
                }
            }
        }

        this.solutionNode = node;
        if(nodeTwin.board.isGoal())
        {
            solvable = false;
        }
    }
    private class SearchNode implements Comparable<SearchNode>{
        private int moves;
        private Board board;
        private SearchNode preNode;
        private int priorityOrder;
        public SearchNode(Board b,SearchNode preNode) {
            board = b;
            if(preNode == null){
                this.preNode = null;
                this.moves = 0;
            }
            else{
                this.moves = preNode.moves + 1;
                this.preNode = preNode;
            }
            this.priorityOrder = this.board.manhattan() + this.moves;
        }
        public int compareTo(SearchNode that){
            if(this.priorityOrder == that.priorityOrder){
                return this.board.hamming() - that.board.hamming();
            }
            else{
                return this.priorityOrder - that.priorityOrder;
            }
        }
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return solvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if(solvable)
        {
            return node.moves;
        }
        else{
            return -1;
        }
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if(!solvable){
            return null;
        }
            Stack<Board> steps = new Stack<>();
            //for(SearchNode i = node;i != null;i = i.preNode){
                //steps.push(i.board);
            //}
            SearchNode j = node;
            while(j != null){
                steps.push(j.board);
                j = j.preNode;
            }
        return steps;
    }
    /*public static void main(String[] args) // solve a slider puzzle (given below)
    {
        int dimension = 3;
        int[][] blocks = new int[dimension][dimension];

        for (int i = 0;i<dimension;i++)
        {
            for(int j = 0;j<dimension;j++)
            {
                blocks[i][j] = i*dimension + j + 1;
            }
        }
        blocks[0][1] = 0;
        blocks[dimension-1][dimension-1] = 2;
        Board t = new Board(blocks);
        StdOut.println(t);
        StdOut.println("test");
        Solver solver = new Solver(t);
        StdOut.println("test1");
        int n = 0;
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()){

                StdOut.println(n++);
                StdOut.println(board);
            }
        }
        StdOut.println(solver.moves());
    }*/
}

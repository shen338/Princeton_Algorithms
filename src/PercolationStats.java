/**
 * Created by tongs on 1/2/2017.
 */

import edu.princeton.cs.algs4.*;


public class PercolationStats {

    private int gridSize;
    private int trails;
    private Percolation test = null;
    private double result[] = null;

    public PercolationStats(int n, int trails){ // perform trials independent experiments on an n-by-n grid
        this.trails = trails;
        gridSize = n;
        result = new double[trails];
        for(int i = 0;i < trails;i++){

            test = new Percolation(gridSize);
            int loop = 0;
            while(!test.percolates()){
                int ram = (int)(Math.random() * gridSize*gridSize);
                int p = ram/gridSize + 1;
                int q = ram%gridSize + 1;

                //if (test.percolates()){
                  //  StdOut.println("test3");
                    //break;
                //}
                if (test.isOpen(p,q))
                {
                    continue;
                }
                test.open(p,q);
                loop++;
            }

            result[i] = (double)loop/(double)(gridSize*gridSize);
        }


    }

    public double mean() {                         // sample mean of percolation threshold
        double temp = 0;
        double d;
        for (int i = 0;i < trails;i++){
            d = result[i];
            temp = temp + d;
        }
        return temp/trails;
    }

    public double stddev() {                      // sample standard deviation of percolation threshold
        double temp = 0;
        for (int i = 0;i < trails;i++){
            temp = temp + (result[i] - mean())*(result[i] - mean());
        }
        return Math.sqrt(temp/(trails - 1));

    }

    public double confidenceLo() {                 // low  endpoint of 95% confidence interval
        double temp;
        temp = mean() - 1.96*stddev()/Math.sqrt(trails);
        return temp;

    }

    public double confidenceHi() {                 // high endpoint of 95% confidence interval
        double temp;
        temp = mean() + 1.96*stddev()/Math.sqrt(trails);
        return temp;
    }


    public static void main(String[] args) {
        int n,trails;
        n = StdIn.readInt();
        trails = StdIn.readInt();
        if(n <= 0 || trails <= 0)
        {
            throw new IllegalArgumentException();
        }
        PercolationStats uf = new PercolationStats(n,trails);
        StdOut.println("mean:" + uf.mean() + "\n");
        StdOut.println("std:" + uf.stddev() + "\n");
        StdOut.println("Higher Bound:" + uf.confidenceHi() + "\n");
        StdOut.println("Lower Bound:" + uf.confidenceLo() + "\n");
    }
}


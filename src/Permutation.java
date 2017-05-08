import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by tongs on 1/6/2017.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> test = new RandomizedQueue<String>();
        String temp;
        int x = 0;
        while (x<k){
            temp = StdIn.readString();
            test.enqueue(temp);
            x ++;
        }
        for(String s: test) StdOut.println(s);
    }
}

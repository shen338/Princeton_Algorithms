import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by tongs on 2/13/2017.
 */
public class Outcast {
    private WordNet wordnet;

    private String outcast;

    private int distance;
    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        this.wordnet = wordnet;
        this.outcast = null;
        this.distance = 0;
    }
    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {

        int comDis = 0;
        String currectOut = nouns[0];
        for(String temp: nouns){
            int currectDis = 0;
            for(String temp1:nouns) {
                currectDis = currectDis + wordnet.distance(temp1,temp);
            }
            if(currectDis > comDis){
                currectOut = temp;
                comDis = currectDis;
            }
        }
        this.distance = comDis;
        return currectOut;
    }
    /*public static void main(String[] args)  // see test client below
    {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }*/
}

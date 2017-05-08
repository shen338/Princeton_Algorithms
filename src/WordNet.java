import edu.princeton.cs.algs4.*;


import java.util.HashMap;
import java.util.Map;


/**
 * Created by tongs on 2/12/2017.
 */
public class WordNet {
    private SAP sap;
    private Map<Integer,String> idNoun;
    private Map<String,Bag<Integer>> nounID;
    private int cap;
    public WordNet(String synsets, String hypernyms){
        readSym(synsets);
        readHypersym(hypernyms);
    }
    private void readSym(String synsetsFile){
        cap = 0;
        nounID = new HashMap<>();
        idNoun = new HashMap<>();
        In input = new In(synsetsFile);
        while (input.hasNextLine()) {
            String[] temps = input.readLine().split(",");
            int id = Integer.parseInt(temps[0]);
            String[] syms = temps[1].split(" ");
            Bag<Integer> bag;
            for(int i = 0;i<syms.length;i++){
                if(!nounID.containsKey(syms[i])){
                    bag = new Bag<Integer>();
                }
                else{
                    bag = nounID.get(syms[i]);
                }
                bag.add(id);
                nounID.put(syms[i],bag);
            }
            idNoun.put(id,temps[1]);
            cap ++;
        }
    }
    private void readHypersym(String hypernymsfile){

        In input = new In(hypernymsfile);
        Digraph dig = new Digraph(nounID.size());
        while(input.hasNextLine()){
            String[] temps = input.readLine().split(",");
            int id = Integer.parseInt(temps[0]);
            for(int i = 0;i < temps.length;i++){
                dig.addEdge(id,Integer.parseInt(temps[i]));
            }
        }
        if(!checkCycle(dig) || !checkRoot(dig)){
            throw new IllegalArgumentException();
        }
        sap = new SAP(dig);
    }
    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
        return nounID.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word == null){
            throw new NullPointerException();
        }
        return nounID.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        if(!isNoun(nounA)|| !isNoun(nounB))
        {
            throw new IllegalArgumentException();
        }
        if(nounA == null || nounB == null){
            throw new NullPointerException();
        }return sap.length(nounID.get(nounA),nounID.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(!isNoun(nounA)|| !isNoun(nounB))
        {
            throw new IllegalArgumentException();
        }
        if(nounA == null || nounB == null){
            throw new NullPointerException();
        }
        int id = sap.ancestor(nounID.get(nounA),nounID.get(nounB));

        return idNoun.get(id);
    }
    private boolean checkCycle(Digraph G) {
        DirectedCycle temp = new DirectedCycle(G);
        if (temp.hasCycle()) {
            return false;
        }else{
            return true;
        }
    }
    private boolean checkRoot(Digraph digraph){
        int roots = 0;
        for (int i = 0, sz = digraph.V(); i < sz; i++) {
            if (!digraph.adj(i).iterator().hasNext()) {
                roots += 1;
            }
        }
        if (roots != 1) {
            return false;
        }else {
            return true;
        }
    }
    // do unit testing of this class
    /*public static void main(String[] args){
        WordNet wordNet = new WordNet(args[0], args[1]);

        while (!StdIn.isEmpty()) {
            String nounA = StdIn.readString();
            String nounB = StdIn.readString();

            if (!wordNet.isNoun(nounA)) {
                StdOut.printf("%s is not a noun!\n", nounA);
                continue;
            }

            if (!wordNet.isNoun(nounB)) {
                StdOut.printf("%s is not a noun!\n", nounB);
                continue;
            }

            int distance = wordNet.distance(nounA, nounB);
            String ancestor = wordNet.sap(nounA, nounB);

            StdOut.printf("distance = %d, ancestor = %s\n", distance, ancestor);
        }
    }*/

}

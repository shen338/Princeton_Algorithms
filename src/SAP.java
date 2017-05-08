/**
 * Created by tongs on 2/9/2017.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;

import java.util.HashMap;
import java.util.Map;



public class SAP {

    private Digraph dig = null;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        dig = G;
    }

    private Map<Integer,Integer> getAns(int v){
        Map<Integer,Integer> ancesters = new HashMap<>();
        Queue<Integer> waitingList = new Queue<>();
        waitingList.enqueue(v);
        ancesters.put(v,0);
        while(!waitingList.isEmpty()){
            int first = waitingList.dequeue();
            int num = ancesters.get(first);
            for(Integer temp:dig.adj(first)){
                if(!ancesters.containsKey(temp) || ancesters.get(temp) > num + 1 ){//quite important here.
                    waitingList.enqueue(temp);
                    ancesters.put(temp,num + 1);
                }
            }
        }
        return ancesters;
    }

    private boolean vertexValid(int v){

        if(v < 0 || v >dig.V()){
            return false;
        }
        else{
            return true;
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        if(!vertexValid(v) || !vertexValid(w)){
            throw new IndexOutOfBoundsException();
        }
        Map<Integer,Integer> ancesterV = getAns(v);
        Map<Integer,Integer> ancesterW = getAns(w);

        Integer distance = -1;
        for (Map.Entry<Integer, Integer> entry : ancesterV.entrySet()) {
            Integer vertex = entry.getKey();

            Integer dis = entry.getValue();

            if(ancesterW.containsKey(vertex)){


                Integer newDis = ancesterW.get(vertex) + dis;
                if(distance < 0 || distance > newDis){
                    distance = newDis;
                }
            }
        }
        return distance;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        if(!vertexValid(v) || !vertexValid(w)){
            throw new IndexOutOfBoundsException();
        }
        if(!vertexValid(v) || !vertexValid(w)){
            throw new IndexOutOfBoundsException();
        }
        Map<Integer,Integer> ancesterV = getAns(v);
        Map<Integer,Integer> ancesterW = getAns(w);
        Integer distance = -1;
        Integer target = -1;
        for (Map.Entry<Integer, Integer> entry : ancesterV.entrySet()) {
            Integer vertex = entry.getKey();
            Integer dis = entry.getValue();
            if(ancesterW.containsKey(entry.getKey())){
                Integer newDis = ancesterW.get(vertex) + dis;
                if(distance < 0 || distance > newDis){
                    distance = newDis;
                    target = vertex;
                }
            }
        }
        return target;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null){
            throw new NullPointerException();
        }
        int dis = -1;
        int tempDis;
        for(int tempV : v){
            for (int tempW : w){
                tempDis = length(tempV,tempW);
                if(tempDis < dis || dis < 0){
                    dis = tempDis;
                }
            }
        }
        return dis;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null){
            throw new NullPointerException();
        }
        int dis = -1;
        int tempDis;
        int coAns = -1;
        for(int tempV : v){
            for (int tempW : w){
                tempDis = length(tempV,tempW);
                if(tempDis < dis || dis < 0){
                    dis = tempDis;
                    coAns = ancestor(tempV,tempW);
                }
            }
        }
        return coAns;
    }


    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 3;
        int w = 11;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
    }

}

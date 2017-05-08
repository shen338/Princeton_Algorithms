/**
 * Created by tongs on 3/13/2017.
 */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class neatTries {

    private Node root;
    private int N;

    private class Node{
        private int value;
        private char c;
        private Node left, mid, right;
    }

    public neatTries(){
        N = 0;
    }

    public void put(String key, int value){
        root = put(root,key,0,value);
        N++;
    }

    private Node put(Node x, String key, int d, int value){
        char c = key.charAt(d);
        if (x == null) { x = new Node(); x.c = c; }
        if (c < x.c) x.left = put(x.left, key, d, value);
        else if (c > x.c) x.right = put(x.right, key, d, value);
        else if (d < key.length() - 1) x.mid = put(x.mid, key, d+1, value);
        else x.value = value;
        return x;
    }

    public boolean contains(String key){
        if(key == ""){
            return true;
        }
        if(get(key) != -1){
            return true;
        }
        else{
            return false;
        }
    }

    private int get(String key){
        Node x = get(root,key,0);
        if(x == null){return -1;}
        return x.value;
    }

    private Node get(Node x, String key, int d){
        if (x == null) return null;
        char c = key.charAt(d);
        if (c < x.c) return get(x.left, key, d);
        else if (c > x.c) return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid, key, d+1);
        else return x;
    }

    public static void main(String[] args){
        neatTries temp = new neatTries();
        In in = new In("dictionary-algs4.txt");
        int p = 0;
       // while(!in.isEmpty()) {
            //String t = in.readString();
           // temp.put(t,p);
           // p++;
      //  }
        String[] dic = in.readAllStrings();
        for(String s:dic){
            temp.put(s,p);
            p++;
        }
        StdOut.println(temp.contains(""));
    }

}

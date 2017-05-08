import edu.princeton.cs.algs4.StdOut;

/**
 * Created by tongs on 2/4/2017.
 */
public class TestTree {
    private static class Node{
        private int x;
        private Node left;
        private Node right;
        private Node(int x,Node left,Node right){
            this.x = x;
            this.left = left;
            this.right = right;
        }
    }

    private Node rootNode;
    private int size;

    public void insert(int p){
        rootNode = put(rootNode,p);
    }
    private Node put(Node root, int p){
        if(root == null){
            size++;
            return new Node(p,null,null);
            // if root == null, create one
        }
        int cmp = root.x - p;
        if(cmp ==0){
            return root;
        }

        if(cmp < 0){
            root.right = put(root.right,p);
        }
        else{
            root.left = put(root.left,p);
        }
        return root;
    }
    public boolean contains(int p) {
        return contain(rootNode, p);
    }

    public boolean contain(Node root,int p){
        if(root == null){
            return false;
        }
        if(root.x == p){
            return true;
        }
        int cmp = root.x - p;
        if(cmp < 0){
            return contain(root.right,p);
        }
        else{
            return contain(root.left,p);
        }
    }
    public static void main(String[] args){
        TestTree s = new TestTree();
        for(int i = 0;i<100;i++){
            s.insert(i);
        }
        int ss = 101;
        boolean d = s.contains(ss);
        StdOut.println(d);

    }
}

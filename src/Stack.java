import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by tongs on 1/4/2017.
 */
public class Stack<Item>{
    private Node first;
    private int num;
    private class Node{
        Item item;
        Node next;
    }

    public boolean isEmpty(){
        return num == 0;
    }

    public int size(){
        return num;
    }

    public void push(Item item){

        Node oldfirst = first;
        first = new Node();
        first.next = oldfirst;
        first.item = item;
        num++;
    }

    public Item pop(){
        Item temp;
        temp = first.item;
        first= first.next;
        num--;
        return temp;
    }

    public static void main(String[] args) {
        Stack<String> s = new Stack<String>();

        while(!StdIn.isEmpty()){
            String sent = StdIn.readString();
            if (sent.equals("-")) {
                s.push(sent);
            }

            else if(!s.isEmpty()){
                StdOut.println(s.pop());
            }
            if (s.isEmpty())
            {
                break;
            }
            StdOut.println(s.size() + " left in the stack");
            StdOut.println("test");
        }
        StdOut.println("test1");
    }
}

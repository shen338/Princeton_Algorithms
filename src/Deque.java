import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.lang.Iterable;
import java.util.NoSuchElementException;


/**
 * Created by tongs on 1/5/2017.
 */
public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Node pre;
    private Node next;

    private class Node {   // class Node provides new linked list to operate (double linked)
        Item item;
        Node pre = null;
        Node next = null;
    }

    public Deque() {                          // construct an empty deque
        n = 0;
        pre = new Node();
        next = new Node();
        //next.pre = pre;
        //pre.next = next;
    }

    public boolean isEmpty()   {              // is the deque empty?
         return n == 0;
    }

    public int size() {                      // return the number of items on the deque
        return n;
    }

    public void addFirst(Item item) {        // add the item to the front
        if (item == null)
        {
            throw new NullPointerException();
        }
        Node newfirst = new Node();
        newfirst.item = item;

        if (isEmpty()){
            newfirst.pre = null;
            newfirst.next = null;
            pre = newfirst;
            next = newfirst;
        }
        else{
            pre.pre = newfirst;
            newfirst.next = pre;
            newfirst.pre = null;
            pre = newfirst;
        }
        n++;
    }

    public void addLast(Item item)   {
        if (item == null)
        {
            throw new NullPointerException();
        }
        Node newlast = new Node();
            newlast.item = item;

            if(isEmpty()){
                newlast.pre = null;
                newlast.next = null;
                pre = newlast;
                next = newlast;
            }
            else{
                next.next = newlast;
                newlast.pre = next;
                newlast.next = null;
                next = newlast;
            }
            n++;
    }        // add the item to the end

    public Item removeFirst() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        Item temp;
        temp = pre.item;
        if (pre.next == null) {
            pre = null;
            next = null;
        }
        else {
            pre = pre.next;
            pre.pre = null;
        }
        n--;
        return temp;
    }       // remove and return the item from the front

    public Item removeLast()  {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        Item temp;
        temp = next.item;
        if (next.pre == null) {
           pre = null;
           next = null;
        }
        else {
            next = next.pre;
            next.next = null;
        }
        n--;
        return temp;
    }      // remove and return the item from the end

    private class Doublelinkedlist implements Iterator<Item>{
        private Node current = pre;

        public boolean hasNext(){
            return current != null;
        }
        public Item next(){
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                Item temp = current.item;
                current = current.next;
                return temp;

        }
    }
    public Iterator<Item> iterator()  {       // return an iterator over items in order from front to end
         return new Doublelinkedlist();
    }
    public static void main(String[] args){
         Deque<String> list = new Deque<String>();
        // list.addFirst("Fisrtone");
         //list.addFirst("Fisrtone");
        // list.addFirst("Fisrtone");


         list.addLast("Lastone");

         StdOut.println(list.size());
         //String firstout = list.removeFirst();
         //StdOut.println(firstout);
         String out = list.removeLast();
         StdOut.println(out);
         StdOut.println(list.isEmpty());
         //StdOut.println(list.size());
        // StdOut.println(list);
         for(String s: list) StdOut.println(s);// first iteration method
         StdOut.println("test1");
         Iterator<String> ddd = list.iterator();
        while(ddd.hasNext()){
            String x = ddd.next();
            StdOut.println("test2");
            StdOut.println(x); // Second iteration method
        }
    }   // unit testing
}

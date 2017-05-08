import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;
import java.lang.Iterable;
import java.util.NoSuchElementException;

/**
 * Created by tongs on 1/6/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;
    private Item[] a;
    private int capicity;
    public RandomizedQueue(){
        n = 0;
        a = (Item[]) new Object[4];
        capicity = 4;
    }

    private void resize(int max){  //shrink or dilate a queue
        capicity = max;
        Item[] temp = (Item[]) new Object[max];
        for(int x = 0;x < n;x ++)
            temp[x] = a[x];
        a = temp;

    }

    public boolean isEmpty()                 // is the queue empty?
    {
        return n == 0;
    }
    public int size()                        // return the number of items on the queue
    {
        return n;
    }
    public void enqueue(Item item)           // add the item
    {
        if (item == null){
            throw new NullPointerException();
        }
        if (n + 1 > capicity){
            resize(2*capicity);
        }
        a[n++] = item;
    }
    public Item dequeue()                    // remove and return a random item
    {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(n);
        Item temp = a[i];
        a[i] = a[--n];
        a[n] = null;
        return temp;
    }
    public Item sample()                     // return (but do not remove) a random item
    {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(n);
        Item temp = a[i];
        return temp;
    }

    public Iterator<Item> iterator(){
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item>{
        private int current = 0;
        private int shuffle[] = new int[n];
        public boolean hasNext(){
            if(current == 0){
                for(int i = 0;i<n;i++){
                    shuffle[i] = i;
                }
                StdRandom.shuffle(shuffle);
            }
            return current < n;
        }
        public Item next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return a[shuffle[current++]];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        int n = StdIn.readInt();
        for(int i = 0;i<n;i++)
            test.enqueue(i);
        for(Integer s: test) StdOut.println(s);
    }
}

import edu.princeton.cs.algs4.StdOut;

/**
 * Created by tongs on 3/29/2017.
 */
public class CircularSuffixArray {
    private int len;
    private int[] index;

    public CircularSuffixArray(String s)  // circular suffix array of s
    {
        int N = s.length();
        len = N;
        index = new int[N];
        for(int i = 0;i<len;i++){
            index[i] = i;
        }

        sort(s,0,N-1);
    }
    public int length()                   // length of s
    {
        return len;
    }
    public int index(int i)               // returns index of ith sorted suffix
    {
        return index[i];
    }
    private int lessOrNot(String s,int x,int y){
        int x1;
        int x2;
        for(int i = 0;i<len;i++){
            x1 = x+i;
            x2 = y+i;
            if(x1 >= len){
                x1 = x+i - len;
            }
            if(y+i >= len){
                x2 = y+i - len;
            }
            if(s.charAt(x1) < s.charAt(x2)){
                return 1;
            }
            if(s.charAt(x1) > s.charAt(x2)){
                return 2;
            }
        }
        return 0;
    }

    //three way quick sort for the sorting of the suffix circle
    private void sort(String s,int lo,int hi){
        if(hi<=lo) return;
        int lt = lo,gt = hi;
        int temp = index[lo];
        int i = lo;
        //StdOut.println("test " + hi);
        while(i<=gt){
          // StdOut.println("test3 "+lessOrNot(s,i,temp));
            if(lessOrNot(s,index[i],temp) == 1) {
                exch(i++, lt++);
                //StdOut.println("test1 " + lt);
            }
            else if(lessOrNot(s,index[i],temp) == 2) {
                exch(i,gt--);
              //StdOut.println("test2 " + gt);
            }
            else i++;
        }
        sort(s,lo,lt-1);
        sort(s,gt+1,hi);
    }
    private void exch(int x,int y){
        int temp = index[x];
        index[x] = index[y];
        index[y] = temp;
    }
    private void prints(){
        for(int s:index) {
            StdOut.println(s);
        }
    }
    public static void main(String[] args)// unit testing of the methods (optional)
    {
        //CircularSuffixArray test = new CircularSuffixArray("ABRACADABRA!");
        //test.prints();
    }
}

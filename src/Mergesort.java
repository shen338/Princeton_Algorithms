import edu.princeton.cs.algs4.StdOut;

/**
 * Created by tongs on 1/12/2017.
 */
public class Mergesort {
    private int[] test;
    private int[] aux;
    public Mergesort(int[] xxx,int hi,int lo){
        aux = new int[xxx.length];
        test = xxx;
        sort(test,hi,lo);
    }
    public void sort(int[] test,int hi,int lo){

        if(hi <= lo) return;
        int mid = (hi - lo)/2 + lo;
        //sort(test,hi,mid+1);
        myPrint(test);
        sort(test,mid,lo);
        sort(test,hi,mid+1);
        myMerge(test,hi,mid,lo);

    }
    public void myMerge(int[] usage,int hi,int mid,int lo){
        int i = lo;
        int j = mid+1;

        for(int k = lo;k<hi+1;k++){
            aux[k] = usage[k];
        }
        myPrint(aux);
        for(int k = lo;k<hi+1;k++){
            if(i > mid)               usage[k] = aux[j++];
            else if (j> hi)           usage[k] = aux[i++];
            else if(aux[i] < aux[j])  usage[k] = aux[i++];
            else                      usage[k] = aux[j++];
        }
    }
    public void myPrint(int [] test){
        for(int i = 0;i<test.length;i++)
            StdOut.print(test[i] + ",");
        StdOut.println();
    }
    public static void main(String[] args) {
        int[] test = {5,6,73,2,6,2,5,7};
        int hi = test.length -1;
        int lo = 0;
        Mergesort xxx = new Mergesort(test,hi,lo);
        int mid = (hi-lo)/2 + lo;
        StdOut.println(hi  + lo);
        xxx.myMerge(test,hi,mid,lo);
        for(int i = 0;i<test.length;i++)
            StdOut.print(test[i] + ",");
    }
}

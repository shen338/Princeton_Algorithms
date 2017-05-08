
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by tongs on 1/12/2017.
 */
public class Insertion{
    private double[] test;
    public Insertion(double[] items){
        test = items;
        for(int i = 0;i<test.length - 1;i++){
            for(int j = i;j>=0;j--){
                if(test[j+1] < test[j]){
                    double temp = test[j+1];
                    test[j+1] = test[j];
                    test[j] = temp;
                }
                else{
                    break;
                }
            }
        }
    }
    public void myPrint(){
        for(int i = 0;i<test.length;i++)
            StdOut.println(test[i]);
    }
    public static void main(String[] args) {
        double[] test = {5,6,73,2,6,2,5,7};
        Insertion xxx = new Insertion(test);
        xxx.myPrint();

    }
}

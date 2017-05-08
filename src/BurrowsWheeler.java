import edu.princeton.cs.algs4.BinaryStdIn;

/**
 * Created by tongs on 3/30/2017.
 */
public class BurrowsWheeler {
    private static int first;
    private static String code;
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public BurrowsWheeler{
        first = 0;
        code = null;
    }
    public static void encode()
    {
        String s = BinaryStdIn.readString();
        int N = s.length();
        CircularSuffixArray temp = new CircularSuffixArray(s);
        for(int i = 0;i<N;i++){
            if(temp.index(i) == 0){
                first = i;
            }

        }
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode()
    {

    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args)
    {

    }
}

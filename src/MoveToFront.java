import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Created by tongs on 3/29/2017.
 */
public class MoveToFront {
        // apply move-to-front encoding, reading from standard input and writing to standard output
        public static void encode()
        {
            char[] r;
            r = letterList();
            char temp1,temp2,i;
            while(!BinaryStdIn.isEmpty()) {
                char readIn = BinaryStdIn.readChar();
                temp2 = r[0];
                for (i = 0; readIn != r[i]; i++) {
                    temp1 = r[i];
                    r[i] = temp2;
                    temp2 = temp1;
                }
                r[i] = temp2;
                BinaryStdOut.write(i);
                r[0] = readIn;
            }

        }

        // apply move-to-front decoding, reading from standard input and writing to standard output
        public static void decode()
        {
            char[] r = letterList();
            char i;
            while(!BinaryStdIn.isEmpty()) {
                char readIn = BinaryStdIn.readChar();
                for (i = r[readIn];readIn > 0; readIn--) {
                    r[readIn] = r[readIn-1];
                }
                r[0] = i;
                BinaryStdOut.write(i);
            }

        }

        private static char[] letterList(){
            char[] r = new char[256];
            for(char i = 0;i<256;i++){
                r[i] = i;
            }
            return r;
        }

        // if args[0] is '-', apply move-to-front encoding
        // if args[0] is '+', apply move-to-front decoding
        public static void main(String[] args)
        {
            if(args[0] == "+") {
                encode();
            }
            else if(args[0] == "-") {
                decode();
            }
            else {
                throw new IllegalArgumentException();
            }
        }


}

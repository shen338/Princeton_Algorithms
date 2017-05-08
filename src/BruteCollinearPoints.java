import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by tongs on 1/8/2017.
 */


public class BruteCollinearPoints{
    private ArrayList<LineSegment> foundSegments = new ArrayList<>();


    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {

        if ( points== null)
            throw new NullPointerException();

        Point[] Seg = points.clone();

        int loop = Seg.length;
        Arrays.sort(Seg);
        for (int i = 0; i < loop; i++) {
            if (Seg[i] == null)
                throw new NullPointerException();
        }
        for (int i = 0;i<loop-1;i++)
        {
            if(Seg[i] ==Seg[i+1])
            throw new java.lang.IllegalArgumentException();
        }

        for(int i = 0;i<loop -3 ;i++){
            for (int j = i + 1;j < loop -2;j++){
                for (int p = j + 1;p < loop - 1;p++){
                    for (int q = p + 1;q < loop;q++){
                        double slope1 = Seg[i].slopeTo(Seg[j]);
                        double slope2 = Seg[i].slopeTo(Seg[p]);
                        double slope3 = Seg[i].slopeTo(Seg[q]);
                        if (slope1 ==slope2 && slope1 == slope3){
                             foundSegments.add((new LineSegment(Seg[i], Seg[q])));
                        }
                    }
                }
            }
        }

    }
    public int numberOfSegments()        // the number of line segments
    {
        return foundSegments.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        return foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }
    /*public static void main(String[] args){
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        Merge.sort(points);


        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }*/
}

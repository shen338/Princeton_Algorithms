
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by tongs on 1/9/2017.
 */
public class FastCollinearPoints {

    private ArrayList<LineSegment> foundSegments = new ArrayList<>();
    private ArrayList<String> segString = new ArrayList<>();
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        int loop = points.length;
        Point[] Seg = points.clone();
        Point[] segPoints = new Point[4];
        for(int i = points.length - 1;i >= 0; i--) {
            double refSlope = Double.NEGATIVE_INFINITY;


            Arrays.sort(Seg);   // sort the point with scale
            Arrays.sort(Seg, Seg[i].slopeOrder());   // sort the points with slope order

            for (int j = 1 ; j < loop - 2; j++) {
                double slope1 = Seg[0].slopeTo(Seg[j]);   // Seg[0] should be sorted as the first point
                double slope2 = Seg[0].slopeTo(Seg[j + 1]);
                double slope3 = Seg[0].slopeTo(Seg[j + 2]);    //get the slope of every three neighbor points

                if (slope1 == slope2 && slope1 == slope3) {

                    if (slope1 != refSlope) {
                        segPoints[0] = Seg[0];
                        segPoints[1] = Seg[j];
                        segPoints[2] = Seg[j + 1];
                        segPoints[3] = Seg[j + 2];              // segPoint should be the collinear points
                        refSlope = slope1;
                    } else {
                        continue;    // if slope in this three points is the same to the previous ones,
                        //  skip the get Segment part
                    }
                    if (j + 3 == loop) {

                        getSegments(segPoints);
                    } else if (Seg[0].slopeTo(Seg[j + 3]) != slope1) {

                        getSegments(segPoints);

                        // if the slope of is not the same with the previous
                        // point and the next point. run getSegment
                    }

                }
            }
             //when this point is done, delete is from Seg.

                /*double slopes1 = Seg[i].slopeTo(Seg[j + 1]);
                StdOut.println(slopes1);
                double slopes2 = Seg[i].slopeTo(Seg[j + 2]);
                StdOut.println(slopes2);
                double slopes3 = Seg[i].slopeTo(Seg[j + 3]);
                StdOut.println("test" + slopes3);
                if (slopes2 == slopes1 && slopes1 == slopes3) {
                    if (j == i) {
                        if (Seg[j].slopeTo(Seg[j + 3]) != slopes2) {
                            foundSegments.add(new LineSegment(Seg[j + 3], Seg[j]));
                        }
                    } else if (j == loop - 4) {
                        if (Seg[j].slopeTo(Seg[j - 1]) != slopes2) {
                            foundSegments.add(new LineSegment(Seg[j + 3], Seg[j]));
                        }
                    } else {
                        if (Seg[j].slopeTo(Seg[j + 3]) != slopes2 && Seg[j].slopeTo(Seg[j - 1]) != slopes2) {
                            foundSegments.add(new LineSegment(Seg[j + 3], Seg[j]));
                        }
                    }
                }*/
        }

    }
    private void getSegments(Point[] test){
        Arrays.sort(test);
        LineSegment xxx = new LineSegment(test[3],test[0]);
        if(!segString.contains(xxx.toString())) {
            foundSegments.add(new LineSegment(test[3], test[0]));
            segString.add(xxx.toString());
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
        Point[] test = new Point[7];
        for (int i = 0;i<3;i++)
            test[i] = new Point(i,i);
        for (int j = 3;j<6;j++)
            test[j] = new Point(j,2*j);
        test[6] = new Point(4,4);
        FastCollinearPoints collinear = new FastCollinearPoints(test);
        StdOut.println(collinear.numberOfSegments());
        LineSegment[] temp = collinear.segments();
        StdOut.println(temp[0].toString());
    }*/

}

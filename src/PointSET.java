/**
 * Created by tongs on 1/29/2017.
 */

import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeSet;
import java.util.Stack;

public class PointSET {
    private int num;
    private TreeSet<Point2D> points = new TreeSet<>();

    public PointSET()                               // construct an empty set of points
    {
        num = 0;
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return num == 0;
    }

    public int size()                         // number of points in the set
    {
        return num;
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if(!points.contains(p)) {
            points.add(p);
            num++;
        }
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return points.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for(Point2D d:points){
            d.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        Stack<Point2D> result = new Stack<>();
        for (Point2D d : points) {
            if (rect.contains(d)) {
                result.add(d);
            }
        }

        return result;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {

        if(isEmpty())
        {
            return null;
        }
        else{
            Point2D minPoint = null;
            for(Point2D test:points){
                if(minPoint == null || minPoint.distanceSquaredTo(p) > test.distanceSquaredTo(p)){
                    minPoint = test;
                }
            }
            return minPoint;
        }

    }
    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        PointSET test = new PointSET();
        Point2D x1 = new Point2D(0.2,0.2);
        Point2D x2 = new Point2D(0.3,0.3);
        Point2D x3 = new Point2D(0.4,0.4);
        test.insert(x1);
        test.insert(x2);
        test.insert(x2);
        test.insert(x3);
        StdOut.println(test.isEmpty());
        StdOut.println(test.size());
        RectHV t = new RectHV(0.1,0.1,0.2,0.2);
        StdOut.println(test.range(t));
        StdOut.println(test.nearest(x3));
        test.draw();

    }
}


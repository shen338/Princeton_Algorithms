/**
 * Created by tongs on 2/2/2017.
 */

import edu.princeton.cs.algs4.*;

import java.util.Stack;

public class KdTree {
    private static class Node{
        private double x;
        private double y;
        private int level;
        private Node left;
        private Node right;
        private RectHV rec;
        private Node(double x, double y, Node left, Node right,RectHV rec, int level){
            this.x = x;
            this.y = y;
            this.left = left;
            this.right = right;
            this.rec = rec;
            this.level = level;
        }  //initialize Node in the first place
    }

    private static final RectHV CONTAINER = new RectHV(0, 0, 1, 1);
    private Node rootNode;
    private int size;


    public KdTree(){
        this.rootNode = null;
        this.size = 0;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public int size(){
        return this.size;
    }

    //insert a Node in the Kdtree
    public void insert(Point2D p){
        if(p == null){
            throw new NullPointerException();
        }

        rootNode = put(rootNode,p,0,0,1,1,1);
    }
    private Node put(Node temp, Point2D p, double xmin, double ymin, double xmax, double ymax, int level) {
        RectHV range = null;

        if (temp == null) {
            size++;
            range = new RectHV(xmin, ymin, xmax, ymax);
            return new Node(p.x(), p.y(), null, null, range, level);
        }

        double cmp;     // cmp as a criteria for Node comparision

        if (level % 2 == 1) {
            cmp = temp.x - p.x();
        } else {
            cmp = temp.y - p.y();
        }// check current Node and the given Point
        if(temp.x - p.x() != 0 || temp.y != p.y()) {
            if (cmp > 0) {
                if (level % 2 == 1) { // x coordinate
                    temp.left = put(temp.left, p, xmin, ymin, temp.x, ymax, level + 1);
                } else {
                    temp.left = put(temp.left, p, xmin, ymin, xmax, temp.y, level + 1);
                }
            } else {
                if (level % 2 == 1) { // x coordinate
                    temp.right = put(temp.right, p, temp.x, ymin, xmax, ymax, level + 1);
                } else {
                    temp.right = put(temp.right, p, xmin, temp.y, xmax, ymax, level + 1);
                }
            }
        }
        return temp;
    }

    public boolean contains(Point2D p){
        if(isEmpty()){
            return false;
        }
        if(p == null){
            throw new NullPointerException();
        }
        return search(rootNode,p,1);
    }

    private boolean search(Node s, Point2D p,int level){
        if(s == null){
            return false;
        }
        if(s.x == p.x() && s.y == p.y()){
            return true;
        }
        double cmp;
        if(level%2 == 1){
            cmp = s.x - p.x();
        }
        else{
            cmp = s.y - p.y();
        }
        if(cmp <= 0){
            return search(s.right,p,level+1);  //++level maybe an error here
        }
        else
        {
            return search(s.left,p,level+1);
        }
    }
    public Point2D nearest(Point2D p){
        if(isEmpty()){
            return null;
        }
        if(p == null){
            throw new NullPointerException();
        }
        Point2D result =null;
        result = nearest(rootNode,p,result);
        return result;
    }
    private Point2D nearest(Node temp,Point2D p, Point2D result)
    {
        if (result == null){
            result = new Point2D(temp.x,temp.y);
        }
        double champ = result.distanceSquaredTo(p);
        if(temp != null && temp.rec.distanceSquaredTo(p) < champ){
            Point2D p1 = new Point2D(temp.x,temp.y);
            if(p1.distanceSquaredTo(p) < champ){
                result = p1;
            }
            result = nearest(temp.left,p,result);
            result = nearest(temp.right,p,result);
        }
        return result;
    }
    private void drawLine(Node x, int level) {
        if (x != null) {
            drawLine(x.left, level + 1);
            StdDraw.setPenRadius();
            if (level % 2 == 1) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.x, x.rec.ymin(), x.x, x.rec.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rec.xmin(), x.y, x.rec.xmax(), x.y);
            }

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.1);
            Point2D p = new Point2D(x.x,x.y);
            p.draw();
            drawLine(x.right, level + 1);
        }
    }

    public void draw() {
        StdDraw.clear();
        drawLine(rootNode, 1);
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        if(rect == null){
            throw new NullPointerException();
        }
        Stack<Point2D> points = new Stack<>();
        range(rect,rootNode,points);
        return points;
    }
    private void range(RectHV rect,Node root,Stack<Point2D> p){
        if(root != null && root.rec.intersects(rect)){
            Point2D temp = new Point2D(root.x,root.y);
            if(rect.contains(temp)) {
                p.push(temp);
            }
            range(rect,root.right,p);
            range(rect,root.left,p);
        }

    }


        public static void main(String[] args) {

            In in = new In(".idea/circle100.txt");

            StdDraw.enableDoubleBuffering();

            // initialize the two data structures with point from standard input
            PointSET brute = new PointSET();
            KdTree kdtree = new KdTree();

            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                kdtree.insert(p);
                brute.insert(p);
            }
            StdOut.println(kdtree.size());

            while (true) {

                // the location (x, y) of the mouse
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                Point2D query = new Point2D(x, y);

                // draw all of the points
                StdDraw.clear();
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                brute.draw();

                // draw in red the nearest neighbor (using brute-force algorithm)
                StdDraw.setPenRadius(0.03);
                StdDraw.setPenColor(StdDraw.RED);
                brute.nearest(query).draw();
                StdDraw.setPenRadius(0.02);

                // draw in blue the nearest neighbor (using kd-tree algorithm)
                StdDraw.setPenColor(StdDraw.BLUE);
                kdtree.nearest(query).draw();
                StdDraw.show();
                StdDraw.pause(40);
            }
        }
    }


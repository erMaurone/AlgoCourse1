package lesson5.assignment.kdTrees;

import princeton.algs4.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 02/12/2014
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
public class KdTree {

    public           boolean isEmpty(){return true;}                      // is the set empty?
    public               int size()    {return 0;}                     // number of points in the set
    public              void insert(Point2D p)   {}           // add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p)   {return true;}         // does the set contain point p?
    public              void draw() {}                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {return null;}             // all points that are inside the rectangle
    public           Point2D nearest(Point2D p) {return null;}            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {}                  // unit testing of the methods (optional)
}

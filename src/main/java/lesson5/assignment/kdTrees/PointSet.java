package lesson5.assignment.kdTrees;

import princeton.algs4.Point2D;
import princeton.stdlib.StdDraw;

import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 01/12/2014
 * Time: 12:29
 */

public class PointSET {

    private TreeSet<Point2D> bst;

    // construct an empty set of points
    public PointSET() { bst = new TreeSet<Point2D>(); }

    // is the set empty?
    public boolean isEmpty() { return bst.isEmpty(); }

    // number of points in the set
    public int size() { return bst.size(); }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        bst.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return bst.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenRadius(0.01);
        for (Point2D p : bst)
            p.draw();
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        TreeSet<Point2D> inRectangle = new TreeSet<Point2D>();
        for (Point2D p : bst)
            if (rect.contains(p)) inRectangle.add(p);
        return inRectangle;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (bst == null) return null;
        if (bst.contains(p) )return p;

        Point2D champ = null;
        for (Point2D point : bst) {
            if (champ == null ) {
                champ = point;
                continue;
            }
            if (point.distanceTo(p) < champ.distanceTo(p) ) champ = point;
        }
        return champ;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) { }
}

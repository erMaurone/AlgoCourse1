package lesson3.convexHull.assignment.patternRecognition;

/*************************************************************************
 * Name:  Mauro
 * Email: mauro.vilasi@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Date: 10/11/2014
 * Time: 18:29
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import princeton.stdlib.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            double slopeP1 = slopeTo(p1);
            double slopeP2 = slopeTo(p2);
            if (slopeP1 > slopeP2) return 1;
            if (slopeP1 < slopeP2) return -1;
            return 0;
        }
    };

    private final int x,y;

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() { StdDraw.point(x, y); }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) { StdDraw.line(this.x, this.y, that.x, that.y); }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that.y == this.y && that.x == this.x) return Double.NEGATIVE_INFINITY; // degenerate slope
        if (that.y == this.y) return +0.0; // horizontal line
        if (that.x == this.x) return Double.POSITIVE_INFINITY; // vertical line
        double slope = (double) (that.y - this.y) / (that.x - this.x );
        return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;
        return 0;
    }

    // return string representation of this point
    public String toString() { return "(" + x + ", " + y + ")"; }
}
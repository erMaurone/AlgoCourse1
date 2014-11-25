package lesson3.convexHull.assignment.patternRecognition;

import lesson2.dataStructures.GenericLinkedQueue;
import lesson2.mergeSort.MergeTopDown;
import princeton.stdlib.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 11/11/2014
 * Time: 10:32
 * To change this template use File | Settings | File Templates.
 */

public class Fast<Item extends Comparable> {
    private final Point[] points;
    private static final int POINTS_IN_LINE = 4;
    GenericLinkedQueue[] lines = new GenericLinkedQueue[10];
    int linesAdded=0;

    public Fast(String fileName) { points = InOutParser.processInput(fileName); }

    public static void main(String[] args ) {
        Fast faster = new Fast(args[0]);
        faster.publishPointsInLine();
    }

    public void publishPointsInLine() {
        if (points == null || points.length < POINTS_IN_LINE) throw new IllegalArgumentException();
        MergeTopDown.sort(points);
        for (int i = 0; i < points.length ;i++) {
            Point p = points[i];
            GenericLinkedQueue<Point> pointsInLine = new GenericLinkedQueue<Point>();
            pointsInLine.enqueue(p);
            Point testPoint = new Point(3000,4000);
            if (p.compareTo(testPoint) == 0)
                System.out.println("got it");
            Point[] orderedPoints = sort(points, p);
            double minSlope = p.slopeTo(orderedPoints[0]);
            for (int j =0; j < orderedPoints.length;j++) {
                Point other = orderedPoints[j];
                if ( inLine(p,other,minSlope )) {
                    if ( alreadyAdded(p, other)) break;
                    pointsInLine.enqueue(other);
                } else if (pointsInLine.size() < POINTS_IN_LINE) {
                    minSlope = p.slopeTo(other);
                    pointsInLine = new GenericLinkedQueue<Point>();
                    pointsInLine.enqueue(p);
                }
            }
            if (pointsInLine.size() >= 4) lines[linesAdded++] = pointsInLine; }
        showLines();
    }

    private void showLines() {
        for (int i = 0; i < linesAdded;i++)
            StdOut.println(InOutParser.format(lines[i], POINTS_IN_LINE));
    }

    private void showPoints(Point[] points, Point first) {
        System.out.println("*****" + first);
        for (int k =0 ; k< points.length;k++) {
            System.out.print(" slope = " + first.slopeTo(points[k]));
            System.out.println(" *****" + points[k]);
        }
    }

    private boolean inLine(Point p, Point other, double slope) {
        if (slope == (double) p.slopeTo(other))
              return true;
        return false;
    }

    private boolean alreadyAdded(Point p, Point that) {
        if (linesAdded == 0) return false;
        for (int i = 0; i < linesAdded;i++)
            if (lines[i].contains(p, that)) return true;
        return false;
    }

    private Point[] sort(Point[] points, Point p) {
        Point[] ordered = new Point[points.length-1];
        int index_delta =0;
        for (int i =0; i< points.length; i++) {
            if ((points[i] == p)) {
                index_delta++;
                continue;
            }
            ordered[(i - index_delta)] = points[i];
        }
        Comparator<Point> cmp = p.SLOPE_ORDER;
        Arrays.sort(ordered, cmp);
        return ordered;
    }
}

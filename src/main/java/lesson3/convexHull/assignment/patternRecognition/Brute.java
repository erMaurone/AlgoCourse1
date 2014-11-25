package lesson3.convexHull.assignment.patternRecognition;

import lesson2.mergeSort.MergeTopDown;
import princeton.stdlib.StdOut;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 10/11/2014
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public class Brute {
    private final Point[] points;
    private static final int POINTS_IN_LINE = 4;

    public static void main(String[] args) {
        Brute bruteForce= new Brute(args[0]);
        bruteForce.findPointsInLine(bruteForce.getPoints());
    }

    public Brute(String fileName) {points = InOutParser.processInput(fileName);}

    public void findPointsInLine(Point[] points) {
        if (points == null || points.length < POINTS_IN_LINE) throw new IllegalArgumentException();
        MergeTopDown.sort(points);
        for (int i = 0; i < (points.length - (POINTS_IN_LINE - 1)) ; i++)
            for (int j = i+1; j < (points.length - (POINTS_IN_LINE - 2)) ; j++)
                for(int k=j+1; k < (points.length - (POINTS_IN_LINE - 3)); k++)
                    for (int l = k+1; l < (points.length ); l++) {
                        double slope = points[i].slopeTo(points[j]);
                        if ( slope == points[i].slopeTo(points[k]) &&
                                slope == points[i].slopeTo(points[l]) ) {
                                Point[] pointsInLine = {points[i], points[j], points[k], points[l]};
                                StdOut.println(InOutParser.format(pointsInLine, POINTS_IN_LINE));
                        }
                    }
    }

    public Point[] getPoints() {return points;}
}
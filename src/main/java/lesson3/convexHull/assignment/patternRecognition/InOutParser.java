package lesson3.convexHull.assignment.patternRecognition;

import lesson2.dataStructures.GenericLinkedQueue;
import princeton.stdlib.In;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 11/11/2014
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
public class InOutParser {
    private InOutParser() {}

    public static Point[] processInput(String fileName) {
        In fileReader = new In(fileName);
        int pointsQty = fileReader.readInt();
        Point[] points = new Point[pointsQty];
        int pointsReceived=0;
        while (fileReader.hasNextLine()) {
            Point thisPoint = readPoint(fileReader);
            points[pointsReceived++] = thisPoint;
        }
        return points;
    }

    private static Point readPoint(In fileReader) {
        int x = fileReader.readInt();
        int y = fileReader.readInt();
        return new Point(x,y);
    }

    public static String format(Point[] points, final int POINTS_IN_LINE) {
        if (points == null || points.length < POINTS_IN_LINE) throw new IllegalArgumentException();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < points.length;i++) {
            Point p = points[i];
            output.append(p.toString());
            if (i != points.length - 1)
                output.append(" -> ");
        }
        return output.toString();
    }

    public static String format(GenericLinkedQueue points, final int POINTS_IN_LINE) {
        if (points == null || points.size() < POINTS_IN_LINE) throw new IllegalArgumentException();
        StringBuilder output = new StringBuilder();
        while (!points.isEmpty()) {
            Point p = (Point) points.dequeue();
            output.append(p.toString());
            if (!points.isEmpty())
                output.append(" -> ");
        }
        return output.toString();
    }
}
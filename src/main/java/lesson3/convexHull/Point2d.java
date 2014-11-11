package lesson3.convexHull;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 10/11/2014
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
public class Point2D {
    public final Comparator<Point2D> POLAR_ORDER = new PolarOrder();
    private final double x=0, y=0;
    // tbc...

    public static int ccw(Point2D a, Point2D b, Point2D c)
    {
        double area2 = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
        if (area2 < 0) return -1; // clockwise
        else if (area2 > 0) return +1; // counter-clockwise
        else return 0;  // collinear
    }

    private class PolarOrder implements Comparator<Point2D>
    {
        public int compare(Point2D q1, Point2D q2)
        {   int returnValue = 0;
            double dy1 = q1.y - y;
            double dy2 = q2.y - y;
            if (dy1 == 0 && dy2 == 0) {
                //tbc...
            }
            else if (dy1 >= 0 && dy2 < 0) returnValue = -1;
            else if (dy2 >= 0 && dy1 < 0) returnValue =+1;
            else returnValue = -ccw(Point2D.this, q1, q2);

            return returnValue;
        }
    }
}

package lesson5.assignment.testClients;

import lesson5.assignment.kdTrees.PointSET;
import lesson5.assignment.kdTrees.RectHV;
import princeton.algs4.Point2D;
import princeton.stdlib.StdDraw;
import princeton.stdlib.StdOut;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 04/12/2014
 * Time: 12:10
 * To change this template use File | Settings | File Templates.
 */
public class PointSETVisualizer {
    public static void main(String[] args) {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.show(0);
        PointSET pSet = new PointSET();
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                System.out.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    pSet.insert(p);
                    StdDraw.clear();
                    pSet.draw();
                }
            }
            StdDraw.show(50);
        }

    }
}

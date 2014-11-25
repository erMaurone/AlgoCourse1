package lesson1.assignment.MonteCarloPerculations;

import princeton.stdlib.StdDraw;
import princeton.stdlib.StdOut;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 05/11/2014
 * Time: 23:04
 * To change this template use File | Settings | File Templates.
 */
public class InteractivePercolationVisualizer {

    public static void main(String[] args) {
        // N-by-N percolation system (read from command-line, default = 10)
        int N = 10;
        if (args.length == 1) N = Integer.parseInt(args[0]);

        // repeatedly open site specified my mouse click and draw resulting system
        StdOut.println(N);

        StdDraw.show(0);
        Percolation perc = new PercolationExperiment(N);
        PercolationVisualizer.draw(perc, N);
        StdDraw.show(0);

        while (true) {

            // detected mouse click
            if (StdDraw.mousePressed()) {

                // screen coordinates
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                // convert to row i, column j
                int i = (int) (N - Math.floor(y));
                int j = (int) (1 + Math.floor(x));

                // open site (i, j) provided it's in bounds
                if (i >= 1 && i <= N && j >= 1 && j <= N) {
                    if (!perc.isOpen(i, j)) {
                        StdOut.println(i + " " + j);
                    }
                    perc.open(i, j);
                }

                // draw N-by-N percolation system
                StdDraw.show(0);
                PercolationVisualizer.draw(perc, N);
            }
            StdDraw.show(20);
        }
    }
}
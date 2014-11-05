package lesson1.MonteCarloPerculations;


import princeton.stdlib.StdRandom;
import princeton.stdlib.StdStats;

public class PercolationStats {

    public static final double PERCOLATION_PROBABILITY = 0.593;
    public static final double CONFIDENCE_LEVEL_95 = 1.96;
    private final Percolation[] experiments;
    private final double[] testResults;
    private final int numberOfExperiments;
    private final int gridSize;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("Size of grid and number of experiments must be greater than 0");
        numberOfExperiments = T;
        gridSize = N;
        testResults = new double[numberOfExperiments];
        experiments = new PercolationExperiment[numberOfExperiments];
        for (int i = 0; i < T; i++)
            experiments[i] = new PercolationExperiment(gridSize);
        runExperiments();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(testResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(testResults);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double result = 0.00;
        double mean = mean();
        double stddev = stddev();
        result = mean - ((CONFIDENCE_LEVEL_95 * stddev) / (Math.sqrt(numberOfExperiments)));
        return result;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double result = 0.00;
        double mean = mean();
        double stddev = stddev();
        result = mean + ((CONFIDENCE_LEVEL_95 * stddev) / (Math.sqrt(numberOfExperiments)));
        return result;
    }

    //TODO make parallel
    private final void runExperiments() {
        for (int i = 0; i < numberOfExperiments; i++) {
            Percolation experiment = experiments[i];
            int openedCellsCount = 0;
            while (!experiment.percolates()) {
                openSite(experiment);
                openedCellsCount++;
            }
            testResults[i] = calculateResult(openedCellsCount);
        }
    }

    private double calculateResult(double count) {
        double testResult = count / (gridSize * gridSize);
        return testResult;
    }

    private void openSite(Percolation test) {
        int column = generateRandom();
        int row = generateRandom();
        while (test.isOpen(row, column)) {
            column = generateRandom();
            row = generateRandom();
        }
        test.open(row, column);
    }

    private int generateRandom() {
        return StdRandom.uniform(1, (gridSize + 1));
    }

    // test client (described below)
    public static void main(String[] args) {
        int gridSize = 30;
        int testsCount = 40;
        PercolationStats monteCarlo = new PercolationStats(gridSize, testsCount);
        System.out.printf("%s \t\t\t\t\t %s %.16f\n", "mean ", "=", monteCarlo.mean());
        System.out.printf("%s \t\t\t\t\t %s %.16f\n", "stdv ", "=", monteCarlo.stddev());
        System.out.printf("%s %s %.16f%c %.16f\n", "95% confidence interval ", "=",
                monteCarlo.confidenceHi(), ',', monteCarlo.confidenceLo());
    }
}

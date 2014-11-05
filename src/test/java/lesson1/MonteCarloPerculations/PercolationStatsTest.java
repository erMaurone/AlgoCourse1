package lesson1.MonteCarloPerculations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 05/11/2014
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class PercolationStatsTest {

    private PercolationStats monteCarlo;
    private int gridSize = 20;
    private int experimentRuns = 30;

    @Before
    public void setUp() {
        monteCarlo = new PercolationStats(gridSize, experimentRuns);
    }


    @Test( expected=IllegalArgumentException.class)
    public void testInvalidGridSize() {
        PercolationStats stats = new PercolationStats(0, 5);
    }

    @Test( expected=IllegalArgumentException.class)
    public void testInvalidExpermimentSize() {
        PercolationStats stats = new PercolationStats(5,0);
    }

    @Test
    public void testMean() throws Exception {

        double hiLimit = PercolationStats.PERCOLATION_PROBABILITY + 0.2;
        double loLimit = PercolationStats.PERCOLATION_PROBABILITY - 0.2;
        double mean = monteCarlo.mean();
        Assert.assertTrue("mean " + mean + " expected be greater than " + loLimit,
                (mean > loLimit));
        Assert.assertTrue("mean " + mean + " expected be smaller than " + hiLimit,
                (mean < hiLimit));
    }

    @Test
    public void testStddev() throws Exception {

        double hiLimit = 0.1;
        double loLimit = 0.001;
        double stdev = monteCarlo.stddev();
        Assert.assertTrue("stdev " + stdev + " expected be greater than " + loLimit, (stdev > loLimit));
        Assert.assertTrue("stdev " + stdev + " expected be smaller than " + loLimit, (stdev < hiLimit));
    }

    @Test
    public void testConfidenceLo() throws Exception {
        double hiLimit = 0.7;
        double loLimit = 0.4;
        double loConfidence = monteCarlo.confidenceLo();
        Assert.assertTrue("stdev " + loConfidence + " expected be greater than " + loLimit, (loConfidence > loLimit));
        Assert.assertTrue("stdev " + loConfidence + " expected be smaller than " + loLimit, (loConfidence < hiLimit));
    }

    @Test
    public void testConfidenceHi() throws Exception {
        double hiLimit = 0.8;
        double loLimit = 0.5;
        double hiConfidence = monteCarlo.confidenceHi();
        Assert.assertTrue("stdev " + hiConfidence + " expected be greater than " + loLimit, (hiConfidence > loLimit));
        Assert.assertTrue("stdev " + hiConfidence + " expected be smaller than " + loLimit, (hiConfidence < hiLimit));
    }

    @Test         //TODO put output to a file and compare it.  - beyond the scope
    public void publishResults() {
        System.out.printf("%s \t\t\t\t\t %s %.16f\n","mean ", "=", monteCarlo.mean()  );
        System.out.printf("%s \t\t\t\t\t %s %.16f\n","stdv ","=", monteCarlo.stddev());
        System.out.printf("%s %s %.16f%c %.16f\n","95% confidence interval " , "=",
                monteCarlo.confidenceHi() , ',' ,monteCarlo.confidenceLo() );
    }
}

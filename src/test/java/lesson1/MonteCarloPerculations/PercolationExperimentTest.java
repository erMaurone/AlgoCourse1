package lesson1.MonteCarloPerculations;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 31/10/2014
 * Time: 09:48
 * To change this template use File | Settings | File Templates.
 */
public class PercolationExperimentTest {
    @Test(expected = IllegalArgumentException.class)
    public void invalidMatrixSize() {
        Percolation experiment = new PercolationExperiment(0);
    }

}

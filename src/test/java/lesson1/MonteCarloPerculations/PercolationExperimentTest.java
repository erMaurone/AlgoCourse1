package lesson1.MonteCarloPerculations;

import org.junit.Test;
import org.junit.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 31/10/2014
 * Time: 09:48
 * To change this template use File | Settings | File Templates.
 */
public class PercolationExperimentTest {
    Percolation testExperiment = new PercolationExperiment(4);

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMatrixSize() {
        Percolation experiment = new PercolationExperiment(0);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testOpenInvariant1Validation() {
        testExperiment.open(0, 1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testOpenInvariant2Validation() {
        testExperiment.open(1, 0);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIsOpenInvariant1Validation() {
        testExperiment.isOpen(0, 1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIsOpenInvariant2Validation() {
        testExperiment.isOpen(1, 0);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIsFullInvariant1Validation() {
        testExperiment.isFull(0, 1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIsFullInvariant2Validation() {
        testExperiment.isFull(1, 0);
    }

    @Test
    public void testPerculatorInitialState() {
        Percolation newPerculatior = new PercolationExperiment(3);
        for (int i=1; i< 3; i++)
            for (int j=1;j<3;j++)
                Assert.assertFalse("Initiall all sites are closed", newPerculatior.isOpen(i,j));
    }

}

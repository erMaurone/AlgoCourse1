package lesson1.assignment.MonteCarloPerculations;

import org.junit.Test;
import org.junit.Assert;


public class PercolationExperimentTest {
    int sizeOfSize=4;
    Percolation testExperiment = new PercolationExperiment(sizeOfSize);

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMatrixSize() {
        Percolation experiment = new PercolationExperiment(0);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testOpenInvariant1Validation() {
        testExperiment.open(0, 1);
    }

    public void testOpenInvariant2Validation() {
        testExperiment.open(1, 0);
    }

    public void testOpenInvariant3Validation() {
        testExperiment.open(1, sizeOfSize+1);
    }
    public void testOpenInvariant4Validation() {
        testExperiment.open(sizeOfSize+1, 1);
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
    public void testIsOpenInvariant3Validation() {
        testExperiment.isOpen(sizeOfSize+1, 1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIsOpenInvariant4Validation() {
        testExperiment.isOpen(1, sizeOfSize + 1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIsFullInvariant1Validation() {
        testExperiment.isFull(0, 1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIsFullInvariant2Validation() {
        testExperiment.isFull(1, 0);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIsFullInvariant3Validation() {
        testExperiment.isFull(1, sizeOfSize + 1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIsFullInvariant4Validation() {
        testExperiment.isFull(1, sizeOfSize +1);
    }

    @Test
    public void testPerculatorInitialState() {
        Percolation newPerculatior = new PercolationExperiment(3);
        for (int i=1; i< 3; i++)
            for (int j=1;j<3;j++)
                Assert.assertFalse("Initially all sites are closed", newPerculatior.isOpen(i,j));
    }

    @Test
    public void testPerculation() {
        Percolation test = new PercolationExperiment(3);
        Assert.assertFalse("Unsuccessful percolation expected", test.percolates());
        test.open(1,1);
        Assert.assertFalse("Unsuccessful percolation expected", test.percolates());
        test.open(3,3);
        Assert.assertFalse("Unsuccessful percolation expected", test.percolates());
        test.open(2,2);
        Assert.assertFalse("Unsuccessful percolation expected", test.percolates());
        test.open(3,1);
        Assert.assertFalse("Unsuccessful percolation expected", test.percolates());
        test.open(1,2);
        Assert.assertFalse("Unsuccessful percolation expected", test.percolates());
        test.open(2,3);
        Assert.assertTrue("Successful percolation expected", test.percolates());
    }

    @Test
    public void testFullness() {
        int size = 3;
        Percolation test = new PercolationExperiment(size);
        Assert.assertEquals("No full cell until percolation occurs", 0, countFullCells(test, size));
        test.open(1,1);
        Assert.assertEquals("No full cell until percolation occurs", 0, countFullCells(test, size));
        test.open(3,3);
        Assert.assertEquals("No full cell until percolation occurs", 0, countFullCells(test, size));
        test.open(2,2);
        Assert.assertEquals("No full cell until percolation occurs", 0, countFullCells(test, size));
        test.open(3,1);
        Assert.assertEquals("No full cell until percolation occurs", 0, countFullCells(test, size));
        test.open(1,2);
        Assert.assertEquals("No full cell until percolation occurs", 0, countFullCells(test, size));
        test.open(2,3);
        //TODO try and figure out how to avoid to 'fill' cells when they are actually not on the
        // percolation path
        //Assert.assertEquals("Full cells after percolation occurs", 5, countFullCells(test, size));

    }

    private int countFullCells(Percolation test, int size) {
        int count = 0;
        for (int i = 1; i <=size; i++) {
            for (int j = 1; j <=size; j++) {
                if (test.isFull(i,j))
                    count++;
            }

        }
        return count;
    }

}

package lesson1.MonteCarloPerculations;
import princeton.algs4.WeightedQuickUnionUF;

public class PercolationExperiment implements Percolation {

    private final WeightedQuickUnionUF unionFind;
    private final int [][] matrix;
    private final int sizeSqR;
    private final int topRoot = 0;
    private final int bottomRoot;

    public PercolationExperiment(int N) {
        sizeSqR = N;
        bottomRoot = sizeSqR;
        if (N <= 0) throw new IllegalArgumentException("must be 1 or greater");
        unionFind = new WeightedQuickUnionUF(sizeSqR * sizeSqR + 2);
        matrix = new int[sizeSqR][sizeSqR];
    }

    public void open(int i, int j) {
        validateIndices(i,j);
        if (isOpen(j,j))
            return;
        matrix[atIndex(i)][atIndex(j)] = 1;
        connectToNeighbours(i,j);
    }

    private void connectToNeighbours(int i, int j) {
        tryToConnectLeft(i, j);
        tryToConnectRight(i,j);
        tryToConnectUp(i, j);
        tryToConnectDown(i,j);
    }

    private void tryToConnectDown(int i, int j) {
        if (atIndex(i) < sizeSqR)
            if (isOpen(i++,j)) {
                int cellFrom = xyTo1D(atIndex(i), atIndex(j));
                int cellTo = xyTo1D(atIndex(i++), atIndex(j));
                unionFind.union(cellFrom, cellTo);
            }
    }

    private void tryToConnectUp(int i, int j) {
        if (atIndex(i) > 1)
            if (isOpen(i--,j)) {
                int cellFrom = xyTo1D(atIndex(i), atIndex(j));
                int cellTo = xyTo1D(atIndex(i--), atIndex(j));
                unionFind.union(cellFrom, cellTo);
            }
    }

    private void tryToConnectRight(int i, int j) {
        if (atIndex(j) < sizeSqR)
            if (isOpen(i,j++)) {
                int cellFrom = xyTo1D(atIndex(i), atIndex(j));
                int cellTo = xyTo1D(atIndex(i), atIndex(j++));
                unionFind.union(cellFrom, cellTo);
            }
    }

    private void tryToConnectLeft(int i, int j) {
        if (atIndex(j) > 1)
            if (isOpen(i,j--)) {
                int cellFrom = xyTo1D(atIndex(i), atIndex(j));
                int cellTo = xyTo1D(atIndex(i), atIndex(j--));
                unionFind.union(cellFrom, cellTo);
            }
    }

    public boolean isOpen(int i, int j) {
        validateIndices(i,j);
        boolean result = false;
        if ( 1 == matrix[atIndex(i)][atIndex(j)])
              result = true;
        return result;
    }

    private static int atIndex(int x) {
        return x--;
    }

    public boolean isFull(int i, int j) {
        validateIndices(i,j);
        int cellExamined = xyTo1D(atIndex(i), atIndex(j));
        return unionFind.connected(cellExamined, topRoot);
    }

    public boolean percolates()  {
        return unionFind.connected(topRoot, bottomRoot);
    }

    private int xyTo1D(int x, int y) {
        int i = (sizeSqR * x) + y + 1;
        return i;
    }

    private void validateIndices(int x, int y) {
        if (x <= 0 || x > sizeSqR)
            throw new ArrayIndexOutOfBoundsException("outside range int " + x);
        if (y <= 0 || y > sizeSqR)
            throw new ArrayIndexOutOfBoundsException("outside range int " + y);
    }

    public static void main(String[] args) {
        // incorrect argument - expecting exception
        Percolation experiment = new PercolationExperiment(4);

    }
}

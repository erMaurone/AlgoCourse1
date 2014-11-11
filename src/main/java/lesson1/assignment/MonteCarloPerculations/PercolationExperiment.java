package lesson1.assignment.MonteCarloPerculations;

import princeton.algs4.WeightedQuickUnionUF;

public class PercolationExperiment implements Percolation {
    private final WeightedQuickUnionUF unionFind;
    private final int[][] rowsColumnsGrid;
    private final int sizeSqR;
    private final int topRoot = 0;
    private final int bottomRoot;

    public PercolationExperiment(int N) {
        sizeSqR = N;
        bottomRoot = sizeSqR * sizeSqR + 1;
        if (N <= 0) throw new IllegalArgumentException("must be 1 or greater");
        unionFind = new WeightedQuickUnionUF(sizeSqR * sizeSqR + 2);
        rowsColumnsGrid = new int[sizeSqR][sizeSqR];
        for (int column = 1; column <= sizeSqR; column++) {
            int topCell = xyTo1D(1, column);
            unionFind.union(topRoot, topCell);
            int bottomCell = xyTo1D(sizeSqR, column);
            unionFind.union(bottomRoot, bottomCell);
        }
    }

    public void open(int i, int j) {
        validateIndices(i, j);
        if (isOpen(i, j))
            return;
        rowsColumnsGrid[atIndex(i)][atIndex(j)] = 1;
        connectToNeighbours(i, j);
    }

    private void connectToNeighbours(int i, int j) {
        tryToConnectLeft(i, j);
        tryToConnectRight(i, j);
        tryToConnectUp(i, j);
        tryToConnectDown(i, j);
    }

    private void tryToConnectDown(int i, int j) {
        if (i < sizeSqR) {
            int newValue = i + 1;
            if (isOpen(newValue, j)) {
                int cellFrom = xyTo1D(i, j);
                int cellTo = xyTo1D(newValue, j);
                unionFind.union(cellFrom, cellTo);
            }
        }
    }

    private void tryToConnectUp(int i, int j) {
        if (i > 1) {
            int newValue = i - 1;
            if (isOpen(newValue, j)) {
                int cellFrom = xyTo1D(i, j);
                int cellTo = xyTo1D(newValue, j);
                unionFind.union(cellFrom, cellTo);
            }
        }
    }

    private void tryToConnectRight(int i, int j) {
        if (j < sizeSqR) {
            int newValue = j + 1;
            if (isOpen(i, newValue)) {
                int cellFrom = xyTo1D(i, j);
                int cellTo = xyTo1D(i, newValue);
                unionFind.union(cellFrom, cellTo);
            }
        }
    }

    private void tryToConnectLeft(int i, int j) {
        if (j > 1) {
            int newValue = j - 1;
            if (isOpen(i, newValue)) {
                int cellFrom = xyTo1D(i, j);
                int cellTo = xyTo1D(i, newValue);
                unionFind.union(cellFrom, cellTo);
            }
        }
    }

    public boolean isOpen(int i, int j) {
        validateIndices(i, j);
        boolean result = false;
        int cellValue = rowsColumnsGrid[atIndex(i)][atIndex(j)];
        if (1 == cellValue)
            result = true;
        return result;
    }

    private static int atIndex(int x) {
        return --x;
    }

    public boolean isFull(int i, int j) {
        validateIndices(i, j);
        if (!isOpen(i, j) || !percolates())
            return false;
        int cellExamined = xyTo1D(i, j);
        boolean connectedToTop = unionFind.connected(cellExamined, topRoot);
        boolean connectedToBottom = unionFind.connected(cellExamined, bottomRoot);
        return connectedToTop && connectedToBottom;
    }

    public boolean percolates() {
        return unionFind.connected(topRoot, bottomRoot);
    }

    private int xyTo1D(int x, int y) {
        int X = atIndex(x);
        int Y = atIndex(y);
        int i = (sizeSqR * X) + Y + 1;
        return i;
    }

    private void validateIndices(int x, int y) {
        if (x <= 0 || x > sizeSqR)
            throw new ArrayIndexOutOfBoundsException("outside range int " + x);
        if (y <= 0 || y > sizeSqR)
            throw new ArrayIndexOutOfBoundsException("outside range int " + y);
    }
}

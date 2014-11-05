package lesson1.MonteCarloPerculations;

public interface Percolation {
    public void open(int i, int j);          // open site (row i, column j) if it is not open already

    public boolean isOpen(int i, int j);     // is site (row i, column j) open?

    public boolean isFull(int i, int j);     // is site (row i, column j) full?

    public boolean percolates();             // does the system percolate?
}

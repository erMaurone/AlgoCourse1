package lesson4.assignment.puzzle;

import princeton.algs4.MinPQ;
import princeton.stdlib.StdRandom;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 20/11/2014
 * Time: 15:11
 */

public class Board {
    private int[][] blocks;
    private int manhattan = -1, hamming = -1;
    private final int size;
    private static final Comparator<Board> TIE_BREAKER = new BoardOrder();
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)
    {
        size = blocks.length;
        this.blocks = blocks;
    }
    // board dimension N
    public int dimension() { return size; }

    public int hamming()                   // number of blocks out of place
    {
        if (hamming > -1) return hamming;
        int goalValue = 1;
        int hammingCount = 0;

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size;j++)
                if (goalValue++ != blocks[i][j] && goalValue <= (size * size))
                    hammingCount++;
        hamming = hammingCount;
        return hamming;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {   if(manhattan > -1) return manhattan;
        int goalValue = 1;
        int manhattanCount = 0;

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size;j++) {
                int searchValue = blocks[i][j];
                if (goalValue++ != searchValue  && searchValue != 0) {
                    int column = 0, row = 0;
                        if (searchValue <= size) row = 0;
                        else if(searchValue % size == 0) row = (searchValue - 1) /size;
                        else row = searchValue / size;
                        column = searchValue - (row * size);
                        manhattanCount += abs(i - row) + abs(column - (j + 1));
                    }
                }
        manhattan = manhattanCount;
        return manhattan;
    }

    private int abs(int value) {
        if (value < 0)
            return  (value * -1);
        return value;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        int place = 1;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size;j++)
                if (place++ != blocks[i][j] && place <= (size * size))
                    return false;
        return true;
    }

    public Board twin()                    // a board that is obtained by exchanging two adjacent blocks in the same row
    {
        int row = generateRandom();
        int col = generateRandom();
        int zeroRow = 0;

        int[][] swappedBlocks = new int[size][size];
        for (int i = 0; i< size;i++)
            for(int j=0;j< size;j++) {
                if (blocks[i][j] == 0 ) zeroRow =i;
                swappedBlocks[i][j] = blocks[i][j];
            }
        while (row == zeroRow) row = generateRandom();

        int adjCol;
        if (col != 0) adjCol = col - 1;
        else adjCol = col + 1;

        swapItems(swappedBlocks, col, row, adjCol, row);
        return new Board(swappedBlocks);
    }

    private void swapItems(int[][] swappedBlock, int col, int row, int adjCol, int adjRow) {
        int swap = swappedBlock[row][col];
        swappedBlock[row][col]  = swappedBlock[adjRow][adjCol];
        swappedBlock[adjRow][adjCol] = swap;
    }

    private int generateRandom() {
        return StdRandom.uniform(0, size);
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (!( y instanceof Board )) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        if (!(this.toString().equals(y.toString()))) return false;

        return true;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        MinPQ<Board> neighbours = new MinPQ<Board>(TIE_BREAKER);
        int zeroCol=0;
        int zeroRow=0;
        outer:
        for (int i = 0; i< size;i++)
            for(int j=0;j< size;j++) {
                if (blocks[i][j] == 0 ) {
                    zeroRow =i;
                    zeroCol = j;
                    break outer;
                }
            }
        moveUp(neighbours, blocks, zeroCol, zeroRow);
        moveDown(neighbours, blocks, zeroCol, zeroRow);
        moveLeft(neighbours, blocks, zeroCol, zeroRow);
        moveRight(neighbours, blocks, zeroCol, zeroRow);

        return neighbours;
    }

    private void moveUp(MinPQ<Board> neighbours, int[][] source, int zeroCol, int zeroRow) {
        if (zeroRow != 0 ) {
            int downRow = zeroRow - 1;
            neighbours.insert(move(source, zeroCol, zeroRow, zeroCol, downRow));
        }
    }

    private void moveDown(MinPQ<Board> neighbours, int[][] source, int zeroCol, int zeroRow) {
        if (zeroRow != (size -1)) {
            int upRow = zeroRow + 1;
            neighbours.insert(move(source, zeroCol, zeroRow, zeroCol, upRow));
        }
    }

    private void moveLeft(MinPQ<Board> neighbours, int[][] source, int zeroCol, int zeroRow) {
        if (zeroCol != 0) {
            int leftCol = zeroCol - 1;
            neighbours.insert(move(source, zeroCol, zeroRow, leftCol, zeroRow));
        }
    }

    private void moveRight(MinPQ<Board> neighbours, int[][] source, int zeroCol, int zeroRow) {
        if (zeroCol != (size - 1)) {
            int rightCol = zeroCol + 1;
            neighbours.insert(move(source, zeroCol, zeroRow, rightCol, zeroRow));
        }
    }

    private Board move(int[][] source, int zCol, int zRow, int nCol, int nRow) {
        int[][] db = duplicate(source);
        swapItems(db, zCol, zRow, nCol, nRow);
        return new Board(db);
    }

    private int[][] duplicate(int[][] originalBlocks) {
        int[][] copyBlocks = new int[originalBlocks.length][originalBlocks.length];
        for (int i = 0; i< originalBlocks.length;i++)
            for(int j=0;j< originalBlocks.length;j++)
                copyBlocks[i][j] = blocks[i][j];
        return copyBlocks;
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private static class BoardOrder implements Comparator<Board>{
        public int compare(Board a, Board that) {
            if (a.manhattan() > that.manhattan()) return 1;
            if (a.manhattan() < that.manhattan()) return -1;
            if (a.hamming() > that.hamming()) return  1;
            if (a.hamming() < that.hamming()) return -1;
            return 0;
        }
    }

    public Comparator<Board> getComparator() {
        return TIE_BREAKER;
    }
}

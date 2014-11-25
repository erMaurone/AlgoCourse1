package lesson4.assignment.puzzle;

import org.junit.Assert;
import org.junit.Test;
import princeton.algs4.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 20/11/2014
 * Time: 15:33
 */
public class BoardTest {
    private final int[][] goalBlocks = {{1,2,3},{4,5,6},{7,8,0}};
    private int[][] searchBlocks = {{8,1,3},{4,0,2},{7,6,5}};
    @Test
    public void testDimension() throws Exception {
        Board testBoard = new Board(goalBlocks);
        int expectedSize = goalBlocks.length;
        Assert.assertTrue(expectedSize == testBoard.dimension());
    }

    @Test
    public void testHamming() throws Exception {
        Board testBoard = new Board(searchBlocks);
        int expectedCost = 5;
        Assert.assertTrue(" expected = " + expectedCost + ", actual = " + testBoard.hamming(),
                expectedCost == testBoard.hamming());
    }

    @Test
    public void testManhattan() throws Exception {
        Board testBoard = new Board(searchBlocks);
        int expectedCost = 10;
        Assert.assertTrue(" expected = " + expectedCost + ", actual = " + testBoard.manhattan(),
                expectedCost == testBoard.manhattan());
    }

    @Test
    public void testIsGoal() throws Exception {
        Board goalBoard = new Board(goalBlocks);
        Board searchBoard = new Board(searchBlocks);
        Assert.assertTrue("testing goal board", goalBoard.isGoal());
        Assert.assertFalse("testing search board", searchBoard.isGoal());
    }

    @Test
    public void testTwin() throws Exception {
        Board goalBoard = new Board(goalBlocks);
        Board twin = goalBoard.twin();
        System.out.println("original " + goalBoard);
        System.out.println("twin " + twin);
        Assert.assertFalse(goalBoard.equals(twin));
    }

    @Test
    public void testEquals() throws Exception {
        Board goalBoard = new Board(goalBlocks);
        Board searchBoard = new Board(searchBlocks);
        Board goalBoardTwin = new Board(goalBlocks);

        Assert.assertTrue(goalBoard.equals(goalBoardTwin));
        Assert.assertFalse(goalBoard.equals(searchBoard));
        Assert.assertTrue(goalBoard.equals(goalBoard));
    }

    @Test
    public void testNeighbors() throws Exception {

        Board maxMoves = new Board(searchBlocks);
        Stack<Board> stackMax = (Stack<Board>) maxMoves.neighbors();
        Assert.assertEquals(4, stackMax.size());

        int[][] edgeCaseBlocks = {{0,1,5}, {7,8,3},{2,4,6}};
        Board minMoves = new Board(edgeCaseBlocks);
        Stack<Board> stackMin = (Stack<Board>) minMoves.neighbors();
        Assert.assertEquals(2, stackMin.size());

        int[][] oneMove = {{1,2,3},{4,5,0},{7,8,6}};
        Board boardMin= new Board(oneMove);
        Stack<Board> stackOne = (Stack<Board>) boardMin.neighbors();
        Assert.assertEquals(3, stackOne.size());
    }

    @Test
    public void testToString() throws Exception {
        StringBuilder goalResult= new StringBuilder();
        int size = goalBlocks.length;
        goalResult.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                goalResult.append(String.format("%2d ", goalBlocks[i][j]));
            goalResult.append("\n");
        }
        Board goalBoard = new Board(goalBlocks);

        Assert.assertEquals(goalResult.toString(), goalBoard.toString());
    }

}

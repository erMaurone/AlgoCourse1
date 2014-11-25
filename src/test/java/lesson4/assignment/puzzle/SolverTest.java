package lesson4.assignment.puzzle;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 21/11/2014
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class SolverTest {
    @Test
    public void testSolvable() throws Exception {
        //int[][] goalBlocks = {{1,2,3},{4,5,0},{7,8,6}};
        //int[][] goalBlocks = {{0,1,3},{4,2,5},{7,8,6}};
        int[][] goalBlocks = {{1,0,2},{7,5,4},{8,6,3}};
        Board goal = new Board(goalBlocks);
        Solver solver = new Solver(goal);
        System.out.println("moves = " + solver. moves());
        Assert.assertEquals(11, solver.moves());
        System.out.println("is solveable = " + solver.isSolvable());
        for (Board b : solver.solution())
            System.out.println(b);
    }

    @Test
    public void testUnsolvable() throws Exception {
        int[][] goalBlocks = {{1,2,3},{4,5,6},{8,7,0}};
        Board goal = new Board(goalBlocks);
        Solver solver = new Solver(goal);
        System.out.println("moves = " + solver. moves());
        System.out.println("is solveable = " + solver.isSolvable());
        for (Board b : solver.solution())
            System.out.println(b);
    }

    @Test(expected = NullPointerException.class)
    public void initTest() throws Exception {
        Solver solverTest = new Solver(null);
    }
}

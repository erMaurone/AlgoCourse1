package lesson4.assignment.puzzle;

import princeton.stdlib.In;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 23/11/2014
 * Time: 12:40
 */
/*************************************************************************
 *  Compilation:  javac PuzzleChecker.java
 *  Execution:    java PuzzleChecker filename1.txt filename2.txt ...
 *  Dependencies: Board.java Solver.java In.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 *************************************************************************/

public class PuzzleChecker {
    public static void main(String[] args) {
        String dir = args[0];
        Path testsDir = Paths.get(dir);
        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(testsDir, "puzzle*.txt")) {
            for (Path entry : stream)
                    processFile(dir + entry.getFileName().toString());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private static void processFile(String filename) {

        // read in the board specified in the filename
        In in = new In(filename);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        // solve the slider puzzle
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        System.out.println(filename + ": " + solver.moves());
    }
}

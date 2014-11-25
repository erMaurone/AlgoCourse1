package lesson4.assignment.puzzle;

import princeton.algs4.MinPQ;
import princeton.algs4.Stack;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 21/11/2014
 * Time: 18:24
 */

public class Solver {

    private int moves = 0;
    private Node goalNode = null;
    private MinPQ<Node> queue = new MinPQ<Node>();
    private MinPQ<Node> twinQueue = new MinPQ<Node>();


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException("expected board");

        Node initialNode = new Node(initial, moves, null);
        Node twinNode =  initialNode.twinNode();

        if (initial.isGoal()) {
            goalNode = initialNode;
            return;
        } else if (twinNode.isGoal()) return;

        queue.insert(initialNode);
        twinQueue.insert(twinNode);
        //publishQueue(queue);
        Node twinGoal = null;
        while (goalNode == null && twinGoal == null) {
            //publishQueue(queue);
            goalNode = makeMoves(queue);
            twinGoal = makeMoves(twinQueue);
        }
        if (goalNode != null) moves = goalNode.movesMade();
    }

    private void publishQueue(MinPQ<Node> aQueue) {
        System.out.println("======= Step: " + moves + "======");
        Iterator<Node> iter = aQueue.iterator();
        while(iter.hasNext()) {
            Node n = iter.next();
            System.out.println(n);
        }
        System.out.println("==========================" + moves);
    }

    private Node makeMoves(MinPQ<Node> queue) {
        Node searchNode = queue.delMin();
        int currentMoves = searchNode.movesMade() + 1;
        Node parent = searchNode.getParent();
        Board nextMove = searchNode.getBoard();
        if (nextMove.isGoal())
            return searchNode;
        for( Board b : nextMove.neighbors() ) {
            if (parent != null && parent.getBoard().equals(b)) continue;
            Node move = new Node(b, currentMoves, searchNode);
            queue.insert(move);
        }
        moves = currentMoves;
        return null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return goalNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (goalNode != null) return moves;
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (goalNode == null) return null;
        Stack<Board> solution = new Stack<Board>();
        Node currentNode = goalNode;
        while(currentNode != null) {
            solution.push(currentNode.getBoard());
            currentNode = currentNode.parent;
        }
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        int[][] goalBlocks = {{1,2,3},{4,5,0},{7,8,6}};
        Board goal = new Board(goalBlocks);
        Solver solver = new Solver(goal);
        System.out.println("moves = " + solver. moves());
        System.out.println("is solvable = " + solver.isSolvable());
        for (Board b : solver.solution())
            System.out.println(b);
    }

    private class Node implements Comparable<Node>{
        private Board move;
        private int currentMoves;
        private int priority = -1;
        private Node parent;

        public Node(Board move, int moves, Node parent) {
            this.move = move;
            this.currentMoves = moves;
            this.parent = parent;
        }

        public Node twinNode() {
            return new Node(move.twin(), 0, null);
        }

        public boolean isGoal() {
            return move.isGoal();
        }

        @Override
        public int compareTo(Node that) {
            if (this.priority() < that.priority()) return -1;
            if (this.priority() > that.priority()) return 1;
            Comparator<Board> tieBreaker = move.getComparator();
            return tieBreaker.compare(move, that.getBoard());
            //return 0;
        }

        public int priority() {
            if (priority == -1)
                priority = currentMoves + move.manhattan();
            return priority;
        }

        @Override
        public String toString() {
            StringBuilder output = new StringBuilder("priority = " + this.priority() + '\n');
            output.append("moves = " + currentMoves + '\n');
            output.append("manhattan = " + move.manhattan() + '\n');
            output.append(move);
            return output.toString();
        }

        public Board getBoard() { return move; }

        public Node getParent() {return parent;}

        public int movesMade() { return currentMoves;}
    }
}

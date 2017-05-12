import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

/**
 * Created by zhzhong on 2017/5/10.
 */
public class Solver {
    private Node fuckNode;

    private class Node implements Comparable<Node> {
        private Node previous;
        private Board board;
        private int moves = 0;
        private int priority;

        public Node(Board board) {
            this.board = board;
            this.priority = moves + board.manhattan();
        }

        public Node(Board board, Node previous) {
            this.board = board;
            this.previous = previous;
            this.moves = previous.moves + 1;
            this.priority = moves + board.manhattan();
        }

        public int compareTo(Node node) {
            return (this.priority - node.priority);
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        MinPQ<Node> PQ = new MinPQ<Node>();
        PQ.insert(new Node(initial));

        MinPQ<Node> twinPQ = new MinPQ<Node>();
        twinPQ.insert(new Node(initial.twin()));

        while(true) {
            fuckNode = expand(PQ);
            if (fuckNode != null || expand(twinPQ) != null) return;
        }

    }          // find a solution to the initial board (using the A* algorithm)

    private Node expand(MinPQ<Node> nodes) {
        if(nodes.isEmpty()) return null;
        Node bestNode = nodes.delMin();
        if (bestNode.board.isGoal()) return bestNode;
        for (Board neighbor : bestNode.board.neighbors()) {
            if (bestNode.previous == null || !neighbor.equals(bestNode.previous.board)) {
                nodes.insert(new Node(neighbor, bestNode));
            }
        }
        return null;
    }


    public boolean isSolvable() {
        return fuckNode != null;
    }           // is the initial board solvable?
    public int moves() {
        return isSolvable() ? fuckNode.moves : -1;
    }                    // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> stack = new Stack<Board>();
        Node idiotNode = fuckNode;
        while (idiotNode != null) {
            stack.push(idiotNode.board);
            idiotNode = idiotNode.previous;
        }
        LinkedList<Board> list = new LinkedList<Board>();
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        return list;
    }     // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Created by zhzhong on 2017/5/10.
 */
public class Solver {
    public Solver(Board initial) {}          // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable() {
        return false;
    }           // is the initial board solvable?
    public int moves() {
        return 0;
    }                    // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return null;
            }
        };
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

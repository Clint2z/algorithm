import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zhzhong on 2017/5/10.
 */
public class Board {
    private int[][] blocks;
    private int dimension;
    public Board(int[][] blocks) {
        this.dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                this.blocks[i][j] = blocks[i][j];
    }          // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return dimension;
    }                // board dimension n
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (blocks[i][j] != (i*dimension + j + 1) && blocks[i][j] != 0) hamming++;
        return hamming;
    }                  // number of blocks out of place
    public int manhattan() {
        int manhattan = 0;
        int vd, hd;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0) {
                    vd = blocks[i][j] / dimension;
                    hd = blocks[i][j] % dimension - 1;
                    manhattan += Math.abs(vd - i) + Math.abs(hd - j);
                }
            }
        return manhattan;
    }                // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        return hamming() == 0;
    }               // is this board the goal board?
    public Board twin() {
        Random rand = new Random();
        int i = rand.nextInt(dimension);
        int j = rand.nextInt(dimension);
        int n = rand.nextInt(dimension);
        int m = rand.nextInt(dimension);
        while (n == i && m == j) {
            n = rand.nextInt(dimension);
            m = rand.nextInt(dimension);
        }
        int[][] twin = blocks.clone();
        int temp = twin[i][j];
        twin[i][j] = twin[n][m];
        twin[n][m] = temp;
        return new Board(twin);
    }                   // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y) {
        return toString().equals(((Board)y).toString());
    }       // does this board equal y?
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int i = 0, j = 0;
        flag:
        for (; i < dimension; i++)
            for (; j < dimension; j++)
                if (blocks[i][j] == 0) break flag;
        int[][] neblocks;
        Board neighbor;
        if (i - 1 >= 0) {
            neblocks = blocks.clone();
            neblocks[i][j] = neblocks[i-1][j];
            neblocks[i-1][j] = 0;
            neighbor = new Board(neblocks);
            neighbors.add(neighbor);
        }
        if (i + 1 < dimension) {
            neblocks = blocks.clone();
            neblocks[i][j] = neblocks[i+1][j];
            neblocks[i+1][j] = 0;
            neighbor = new Board(neblocks);
            neighbors.add(neighbor);
        }
        if (j - 1 >= 0) {
            neblocks = blocks.clone();
            neblocks[i][j] = neblocks[i][j-1];
            neblocks[i][j-1] = 0;
            neighbor = new Board(neblocks);
            neighbors.add(neighbor);
        }
        if (j + 1 < 0) {
            neblocks = blocks.clone();
            neblocks[i][j] = neblocks[i][j+1];
            neblocks[i][j+1] = 0;
            neighbor = new Board(neblocks);
            neighbors.add(neighbor);
        }
        return neighbors;
    }    // all neighboring boards
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append(blocks[i][j]+" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }              // string representation of this board (in the output format specified below)

    public static void main(String[] args) {

    }// unit tests (not graded)
}

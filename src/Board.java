import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by zhzhong on 2017/5/10.
 */
public class Board {
    private static final int SPACE = 0;
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
    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                copy[row][col] = blocks[row][col];

        return copy;
    }
    public int dimension() {
        return dimension;
    }                // board dimension n
    public int hamming() {
        int count = 0;
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                if (blockIsNotInPlace(row, col)) count++;

        return count;
    }                  // number of blocks out of place

    private boolean blockIsNotInPlace(int row, int col) {
        int block = getBlock(row, col);

        return !isSpace(block) && block != goalFor(row, col);
    }
    public int manhattan() {
        int sum = 0;
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                sum += calculateDistances(row, col);

        return sum;
    }                // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        return hamming() == 0;
    }               // is this board the goal board?
    public Board twin() {
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length - 1; col++)
                if (!isSpace(getBlock(row, col)) && !isSpace(getBlock(row, col + 1)))
                    return new Board(swap(row, col, row, col + 1));
        throw new RuntimeException();
    }                   // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y) {
        if (y==this) return true;
        if (y==null || !(y instanceof Board) || ((Board)y).blocks.length != blocks.length) return false;
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                if (((Board) y).getBlock(row, col) != getBlock(row, col)) return false;

        return true;
    }       // does this board equal y?
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<Board>();

        int[] location = spaceLocation();
        int spaceRow = location[0];
        int spaceCol = location[1];

        if (spaceRow > 0)               neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol)));
        if (spaceRow < dimension() - 1) neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol)));
        if (spaceCol > 0)               neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1)));
        if (spaceCol < dimension() - 1) neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol + 1)));

        return neighbors;
    }    // all neighboring boards

    private int[] spaceLocation() {
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                if (isSpace(getBlock(row, col))) {
                    int[] location = new int[2];
                    location[0] = row;
                    location[1] = col;

                    return location;
                }
        throw new RuntimeException();
    }
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(dimension() + "\n");
        for (int row = 0; row < blocks.length; row++) {
            for (int col = 0; col < blocks.length; col++)
                str.append(String.format("%2d ", getBlock(row, col)));
            str.append("\n");
        }

        return str.toString();
    }              // string representation of this board (in the output format specified below)

    private boolean isSpace(int block) {
        return block == SPACE;
    }
    private int getBlock(int row, int col) {
        return blocks[row][col];
    }
    private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] copy = copy(blocks);
        int tmp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = tmp;

        return copy;
    }

    private int calculateDistances(int row, int col) {
        int block = getBlock(row, col);

        return (isSpace(block)) ? 0 : Math.abs(row - row(block)) + Math.abs(col - col(block));
    }

    private int row (int block) {
        return (block - 1) / dimension();
    }

    private int col (int block) {
        return (block - 1) % dimension();
    }

    private int goalFor(int row, int col) {
        return row*dimension() + col + 1;
    }
    public static void main(String[] args) {

    }// unit tests (not graded)
}

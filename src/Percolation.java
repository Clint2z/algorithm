/**
 * Created by zhzhong on 2017/1/8.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private boolean grid[][];
    private int n;
    private int OSNum = 0;
    private WeightedQuickUnionUF UF;
    private WeightedQuickUnionUF UFT;
    public Percolation(int n) {// create n-by-n grid, with all sites blocked
        if (n < 1){
            throw new IllegalArgumentException("the initial num should be larger than 1.");
        }
        grid = new boolean[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j< n; j++) {
                grid[i][j] = false;
            }
        }
        /**
         * add two elements at first and last position,
         * the first element union all open elements at first row
         * the last element union all open elements at last row
         * when first element connected to last element ,the gird is percolated
         * */
        UF = new WeightedQuickUnionUF(n*n+2);
        /**
         * add one elements at first position,union all open elements at first row
         *
         * */
        UFT = new WeightedQuickUnionUF(n*n+1);
        this.n = n;
    }
    private void checkRange(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IndexOutOfBoundsException("the row and col should between 1 and " + n);
        }
    }
    public void open(int row, int col) {// open site (row, col) if it is not open already
        if (!isOpen(row, col)) {
            grid[row-1][col-1] = true;
            OSNum ++;
            unionAround(row, col);
        }
    }
    private void unionAround(int row, int col) {

        int[] offsetX = {1, -1, 0, 0};
        int[] offsetY = {0, 0, 1, -1};
        int p = (row - 1) * n + col;
        if(row == 1){
            UF.union(p, 0);
            UFT.union(p, 0);
        }
        if(row == n){
            UF.union(p,n*n+1);
        }
        for(int i=0; i<4; i++){
            int rowX = row + offsetX[i];
            int colY = col + offsetY[i];
            if (rowX >= 1 && rowX <= n && colY >= 1 && colY <= n && isOpen(rowX,colY)) {
                int q = (rowX - 1) * n + colY;
                UF.union(p, q);
                UFT.union(p, q);
            }
        }
    }

    public boolean isOpen(int row, int col) {// is site (row, col) open?
        checkRange(row, col);
        return grid[row-1][col-1];
    }
    public boolean isFull(int row, int col) {// is site (row, col) full? connect to first row.
        checkRange(row, col);
        int p = (row - 1) * n + col;
        return UFT.connected(p,0);
    }
    public int numberOfOpenSites() {// number of open sites
        return OSNum;
    }
    public boolean percolates() {// does the system percolate?
        return UF.connected(0,n*n+1);
    }

    public static void main(String[] args) {// test client (optional)
        Percolation percol = new Percolation(10);
        percol.open(10,10);
        percol.open(1,1);
        percol.open(2,1);
        percol.open(5,2);
        percol.open(4,3);
        percol.open(3,1);
        percol.open(3,2);
        percol.open(3,3);
        percol.open(5,3);
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                System.out.print(percol.grid[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println(percol.isFull(5,2));
        System.out.println(percol.percolates());
    }
}

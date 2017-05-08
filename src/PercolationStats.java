/**
 * Created by zhzhong on 2017/1/10.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private Percolation percol;
    private int trials;
    private double[] x;
    private int row,col;
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0)throw new IllegalArgumentException("Illeagal Argument");
        this.trials = trials;
        x = new double[trials];
        for(int i=0; i<trials; i++){
            percol = new Percolation(n);
            x[i] = 0;
            while(!percol.percolates()){
                do{
                    row = StdRandom.uniform(n)+1;
                    col = StdRandom.uniform(n)+1;
                }while(percol.isOpen(row,col));
                percol.open(row,col);
                x[i]++;
            }
            x[i] /= (double)n*n;
        }

    }    // perform trials independent experiments on an n-by-n grid
    public double mean() {
        return StdStats.mean(x);
    }                         // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(x);
    }                       // sample standard deviation of percolation threshold
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(trials);

    }                 // low  endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(trials);
    }                 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        Stopwatch watch = new Stopwatch();
        int n = Integer.parseInt(args[0]);;
        int trials = Integer.parseInt(args[0]);;
        PercolationStats percStats = new PercolationStats(n, trials);
        System.out.println("mean = "+percStats.mean());
        System.out.println("stddev = "+ percStats.stddev());
        System.out.println("95% confidence interval = "+ percStats.confidenceLo() +","+ percStats.confidenceHi());
        System.out.println("cost time:"+watch.elapsedTime());
    }       // test client (described below)
}

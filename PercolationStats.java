import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
        private int size;
        private int trials;
        private double[] threshold;

        public PercolationStats(int n, int trials) {
                // perform trials independent experiments on an n-by-n grid
                if (n <= 0 || trials <= 0) throw new IllegalArgumentException("n and trails must be greater than zero");
                
                size = n;
                this.trials = trials;
                threshold = new double[trials];

                for (int i = 0; i < trials; i++) {
                        Percolation p = new Percolation(n);

                        int count = 0;
                        boolean[][] a = new boolean[size][size];
                        while (!p.percolates()) {
                                int row = StdRandom.uniform(1, size+1);
                                int col = StdRandom.uniform(1, size+1);
                                
                                while (a[row-1][col-1]) {
                                        row = StdRandom.uniform(1, size+1);
                                        col = StdRandom.uniform(1, size+1);
                                }
                                p.open(row, col);
                                a[row-1][col-1] = true;
                                count++;
                        }
                        threshold[i] = count * 1.0 / (size * size);
                }
        }

        public double mean() {
                // sample mean of percolation threshold
                return StdStats.mean(threshold);
        }

        public double stddev() {
                // sample standard deviation of percolation threshold
                return StdStats.stddev(threshold);
        }

        public double confidenceLo() {
                // low  endpoint of 95% confidence interval
                return mean() - 1.96 * stddev() / Math.sqrt(trials);
        }

        public double confidenceHi() {
                // high endpoint of 95% confidence interval
                return mean() + 1.96 * stddev() / Math.sqrt(trials);
        }

        public static void main(String[] args) {
                // test client (described below)
                try {
                        int n = Integer.parseInt(args[0]);
                        int t = Integer.parseInt(args[1]);
                        PercolationStats stats = new PercolationStats(n, t);
                        System.out.printf("mean                     =  %f%n", stats.mean());
                        System.out.printf("stddev                   =  %f%n", stats.stddev());
                        System.out.printf("95%% confidence interval = [%f, %f] %n", stats.confidenceLo(), stats.confidenceHi());
                } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Arguments must be an Integer");
                }
        }
}
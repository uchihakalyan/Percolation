import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
        private WeightedQuickUnionUF weightedQuickUnionUF, weightedQuickUnionUF1;
        private boolean[][] open;
        private int rowMax, max, count;

        public Percolation(int n) {
                if (n <= 0) throw new java.lang.IllegalArgumentException("Index size should be greater than 1");
                rowMax = n;
                max = n*n+2;
                weightedQuickUnionUF = new WeightedQuickUnionUF(max);
                weightedQuickUnionUF1 = new WeightedQuickUnionUF(max-1);
                open = new boolean[rowMax+1][rowMax+1];
        }

        public void open(int row, int col) {
                if (row <= 0 || row > rowMax) throw new java.lang.IndexOutOfBoundsException("Row index i out of bounds");
                if (col <= 0 || col > rowMax) throw new java.lang.IndexOutOfBoundsException("Col index i out of bounds");
                
                if (!isOpen(row, col)) {
                        open[row][col] = true;
        
                        if (row == 1) {
                                weightedQuickUnionUF.union(col, 0);
                                weightedQuickUnionUF1.union(col, 0);
                                }
                        if (row == rowMax) {
                                weightedQuickUnionUF.union((rowMax-1)*rowMax+col, max-1);
                        }
                        if (col > 1 && open[row][col-1]) {
                                weightedQuickUnionUF.union((row-1)*rowMax+col, (row-1)*rowMax+col-1);
                                weightedQuickUnionUF1.union((row-1)*rowMax+col, (row-1)*rowMax+col-1);
                        }
                        if (col < rowMax && open[row][col+1]) {
                                weightedQuickUnionUF.union((row-1)*rowMax+col, (row-1)*rowMax+col+1);
                                weightedQuickUnionUF1.union((row-1)*rowMax+col, (row-1)*rowMax+col+1);
                        }
                        if (row > 1 && open[row-1][col]) {
                                weightedQuickUnionUF.union((row-1)*rowMax+col, (row-2)*rowMax+col);
                                weightedQuickUnionUF1.union((row-1)*rowMax+col, (row-2)*rowMax+col);
                        }
                        if (row < rowMax && open[row+1][col]) {
                                weightedQuickUnionUF.union((row-1)*rowMax+col, (row)*rowMax+col);
                                weightedQuickUnionUF1.union((row-1)*rowMax+col, (row)*rowMax+col);
                        }
                        count++;
                }
        }

        public boolean isOpen(int row, int col) {
                if (row <= 0 || row > rowMax) throw new java.lang.IndexOutOfBoundsException("row index i out of bounds");
                if (col <= 0 || col > rowMax) throw new java.lang.IndexOutOfBoundsException("col index i out of bounds");
                return open[row][col];
        }

        public boolean isFull(int row, int col) {
                if (row <= 0 || row > rowMax) throw new java.lang.IndexOutOfBoundsException("row index i out of bounds");
                if (col <= 0 || col > rowMax) throw new java.lang.IndexOutOfBoundsException("col index i out of bounds");
                return weightedQuickUnionUF1.connected(0, (row-1) * rowMax+col);
        }

        public int numberOfOpenSites() {
                return count;
        }

        public boolean percolates() {
                return weightedQuickUnionUF.connected(0, max-1);
        }

        public static void main(String[] args) {
                Percolation p = new Percolation(7);
                p.open(1, 1);
                p.open(1, 2);
                p.open(1, 3);
                p.open(1, 4);
                p.open(1, 5);
                p.open(1, 6);
                p.open(2, 6);
                p.open(1, 7);
                System.out.println(p.isOpen(1, 4));
                System.out.println(p.isOpen(2, 4));
                System.out.println(p.isFull(1, 7));
                System.out.println(p.isFull(2, 6));
                System.out.println(p.percolates());
                System.out.println(p.numberOfOpenSites());
        }

}

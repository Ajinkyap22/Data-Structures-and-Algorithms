import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] sites;
    private final WeightedQuickUnionUF uf;
    private final int n;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        uf = new WeightedQuickUnionUF(n * n + 2);
        sites = new boolean[n * n + 2];

        this.n = n;

        openSites = 0;
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        isValid(row, col);

        int index = indexOf(row, col);

        // mark as true in sites array
        if (!sites[index]) {
            sites[index] = true;
            openSites++;

            // union with virtual top/bottom site
            if (row == 1) {
                uf.union(index, 0);
            }

            if (row == n) {
                uf.union(index, n * n + 1);
            }

            // union with open neighbours
            if (row != 1 && isOpen(row - 1, col)) {
                uf.union(index, indexOf(row - 1, col));
            }

            if (row != n && isOpen(row + 1, col)) {
                uf.union(index, indexOf(row + 1, col));
            }

            if (col != 1 && isOpen(row, col - 1)) {
                uf.union(index, indexOf(row, col - 1));
            }

            if (col != n && isOpen(row, col + 1)) {
                uf.union(index, indexOf(row, col + 1));
            }
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        isValid(row, col);

        return sites[indexOf(row, col)];
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        isValid(row, col);

        if (!isOpen(row, col)) {
            return false;
        }

        return uf.find(indexOf(row, col)) == uf.find(0);
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }


    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(n * n + 1);
    }

    private void isValid(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
    }

    private int indexOf(int row, int col) {
        return (row - 1) * n + (col - 1);
    }


    // test client (optional)
    public static void main(String[] args) {
        // Percolation p = new Percolation(2);
    }
}

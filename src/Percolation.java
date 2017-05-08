import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private boolean[] open;
    private int n;
    private int top;
    private int bottom;
    private int ddd = 0;

    public Percolation(int n) {
        // create n-by-n grid, with all sites
        if (n <= 0) throw new IllegalArgumentException("n <= 0");
        int size = (n + 2) * (n + 2);
        uf = new WeightedQuickUnionUF(size);
        uf2 = new WeightedQuickUnionUF(size);
        open = new boolean[size];
        this.n = n;

        top = 0;
        for (int j = 1; j <= n; j++) {
            uf.union(top, twoD2OneD(1, j));
            uf2.union(top, twoD2OneD(1, j));
        }

        bottom = size - 1;
        for (int j = 1; j <= n; j++)
            uf.union(bottom, twoD2OneD(n, j));
    }

    private int twoD2OneD(int i, int j) {
        return i * (n + 2) + j;
    }

    private void checkIndex(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n)
            throw new IndexOutOfBoundsException("bad input");
    }

    public void open(int i, int j) {

        // open site (row i, column j) if it is not open already
        checkIndex(i, j);
        int pos = twoD2OneD(i, j);
        if (!open[pos]) {
            open[pos] = true;
            ddd++;
            int up = twoD2OneD(i - 1, j);
            if (open[up]) {
                uf.union(pos, up);
                uf2.union(pos, up);
            }

            int down = twoD2OneD(i + 1, j);
            if (open[down]) {
                uf.union(pos, down);
                uf2.union(pos, down);
            }

            int left = twoD2OneD(i, j - 1);
            if (open[left]) {
                uf.union(pos, left);
                uf2.union(pos, left);
            }

            int right = twoD2OneD(i, j + 1);
            if (open[right]) {
                uf.union(pos, right);
                uf2.union(pos, right);
            }
        }
    }

    public boolean isOpen(int i, int j) {
        // is site (row i, column j) open?
        checkIndex(i, j);
        return open[twoD2OneD(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        checkIndex(i, j);
        return isOpen(i, j) && uf2.connected(top, twoD2OneD(i, j));
    }

    public int numberOfOpenSites(){
        return ddd;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1) return isOpen(1, 1);
        return uf.connected(top, bottom);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}

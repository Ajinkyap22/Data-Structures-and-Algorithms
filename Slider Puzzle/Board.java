import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private final int n;

    private final int[][] grid;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        grid = copyBoard(tiles, n);
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", grid[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0 && grid[i][j] != tileValue(i, j)) count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dist = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0 && grid[i][j] != tileValue(i, j)) {
                    int iVal = (grid[i][j] - 1) / n;
                    int jVal = (grid[i][j] - 1) % n;
                    dist += Math.abs(i - iVal) + Math.abs(j - jVal);
                }
            }
        }
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;

        if (y == null) return false;

        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        return Arrays.deepEquals(this.grid, that.grid);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> bStack = new Stack<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    // if 0 not at top
                    if (i > 0) {
                        int[][] copy = copyBoard(grid, n);
                        copy[i][j] = grid[i - 1][j];
                        copy[i - 1][j] = grid[i][j];
                        bStack.push(new Board(copy));
                    }

                    // if 0 not at left
                    if (j > 0) {
                        int[][] copy = copyBoard(grid, n);
                        copy[i][j] = grid[i][j - 1];
                        copy[i][j - 1] = grid[i][j];
                        bStack.push(new Board(copy));
                    }

                    // if 0 not at bottom
                    if (i < n - 1) {
                        int[][] copy = copyBoard(grid, n);
                        copy[i][j] = grid[i + 1][j];
                        copy[i + 1][j] = grid[i][j];
                        bStack.push(new Board(copy));
                    }

                    // if 0 not at right
                    if (j < n - 1) {
                        int[][] copy = copyBoard(grid, n);
                        copy[i][j] = grid[i][j + 1];
                        copy[i][j + 1] = grid[i][j];
                        bStack.push(new Board(copy));
                    }
                    break;
                }
            }
        }
        return bStack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copy = copyBoard(grid, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (copy[i][j] != 0 && copy[i][j + 1] != 0) {
                    int temp = copy[i][j];
                    copy[i][j] = copy[i][j + 1];
                    copy[i][j + 1] = temp;

                    return new Board(copy);
                }
            }
        }
        return null;
    }

    private int tileValue(int i, int j) {
        return (i * n + j + 1);
    }

    private int[][] copyBoard(int[][] blocks, int size) {
        int[][] copied = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copied[i][j] = blocks[i][j];
            }
        }
        return copied;
    }

    public static void main(String[] args) {
        int[][] arr = { { 8, 2, 3 }, { 4, 5, 6 }, { 7, 1, 0 } };
        Board b = new Board(arr);
        StdOut.println("String");
        StdOut.println(b.toString());

        Iterable<Board> neighbours = b.neighbors();
        for (Board n : neighbours) {
            StdOut.println(n.toString());
        }
    }
}

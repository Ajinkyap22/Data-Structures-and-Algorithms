import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private boolean isSolvable;

    private int solMoves;

    private SearchBoard goalBoard;

    private class SearchBoard implements Comparable<SearchBoard> {
        private final Board board;

        private final SearchBoard prevBoard;

        private final int moves;

        private final int priority;

        public SearchBoard(Board board, int moves, SearchBoard prevBoard) {
            this.board = board;
            this.moves = moves;
            this.prevBoard = prevBoard;

            priority = board.manhattan() + this.moves;
        }

        public int compareTo(SearchBoard that) {
            return Integer.compare(this.priority, that.priority);
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchBoard> pq = new MinPQ<>();
        MinPQ<SearchBoard> pqTwin = new MinPQ<>();

        pq.insert(new SearchBoard(initial, 0, null));
        pqTwin.insert(new SearchBoard(initial.twin(), 0, null));

        while (!pq.isEmpty()) {
            SearchBoard current = pq.delMin();
            SearchBoard twin = pqTwin.delMin();

            // if cur is goal
            if (current.board.isGoal()) {
                goalBoard = current;
                isSolvable = true;
                solMoves = current.moves;
                break;
            }

            // if twin is goal
            if (twin.board.isGoal()) {
                goalBoard = twin;
                isSolvable = true;
                solMoves = twin.moves;
                break;
            }

            insertNeighbours(current, pq);
            insertNeighbours(twin, pqTwin);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> sol = new Stack<>();

        SearchBoard board = goalBoard;

        while (board != null) {
            sol.push(board.board);
            board = board.prevBoard;
        }
        return sol;
    }

    private void insertNeighbours(SearchBoard board, MinPQ<SearchBoard> pq) {
        for (Board next : board.board.neighbors()) {
            if ((board.prevBoard == null) || (!next.equals(board.prevBoard.board))) {
                pq.insert(new SearchBoard(next, board.moves + 1, board));
            }
        }
    }


    // test client
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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

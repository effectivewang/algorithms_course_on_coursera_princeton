import java.util.*;
//import java.lang.*;

public class Solver {
    private Board root;    
    private ArrayList<Board> solution;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        root = initial;
        
        solution();
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (isSolvable())
            return solution.size();
        
        return -1;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        solution = new ArrayList<Board>();
        
        MinPQ<Board> pq = new MinPQ<Board>(4, new Comparator<Board>() {
            
            public int compare(Board a, Board b) {
                if (a.hamming() != b.hamming())
                    return a.hamming() - b.hamming();
                else
                    return a.manhattan() - b.manhattan();
            }
        });
        
        Board board = root;        
        while (!board.isGoal()) {
            for (Board temp : board.neighbors()) {
                pq.insert(temp);
                
                board = temp;
                if (temp.twin().isGoal()) {
                    solution = null;
                    return solution;
                }
            }
        }
        
        int i = 0;
        for (Board b : pq) {
            if (i == 0) {
                solution.add(b);
            }
            else if (((pq.size() - 1) / i) % 2 == 0) {
                solution.add(b);
            }
        }
        
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {        
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
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
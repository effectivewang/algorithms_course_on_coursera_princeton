//import java.util.*;

public class Solver {
    private Board root;    
    private Stack<Board> solution;
    
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
        solution = new Stack<Board>();
        
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();  
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();        
        Board board = root;       
        pq.insert(new SearchNode(board, null, 0));
        
        Board twinBoard = root.twin();
        twinPQ.insert(new SearchNode(twinBoard, null, 0));
        
        while (!pq.isEmpty()) {
            SearchNode minNode = pq.min();
            SearchNode twinNode = twinPQ.min();
            
            pq.delMin();
            twinPQ.delMin();
                        
            if(minNode.getBoard().isGoal()) {
                SearchNode prev = minNode;
                while(prev.previous != null) {
                    solution.push(prev.getBoard());
                    prev = prev.previous;
                }                
                return solution;
            }
            
            if(twinNode.getBoard().isGoal()) {
                solution = null;
                return null;
            }
            
            for (Board neighbor : minNode.getBoard().neighbors()) {
                if (minNode.previous != null && minNode.previous.getBoard().equals(neighbor)) 
                    continue;
                
                pq.insert(new SearchNode(neighbor, minNode, minNode.moves() + 1));
            }
            
            for (Board neighbor : twinNode.getBoard().neighbors()) {
                if (twinNode.previous != null && twinNode.previous.getBoard().equals(neighbor)) 
                    continue;
                
                twinPQ.insert(new SearchNode(neighbor, twinNode, twinNode.moves() + 1));
            }
        }        
        
        solution = null;
        return solution;
    }
    
    class SearchNode implements Comparable<SearchNode> {
        private SearchNode previous;  
        private Board board;          
        private int move;        
        
        public SearchNode(Board board, SearchNode previous, int move) {
            this.previous = previous;
            this.board = board;
            this.move = move;
        }
        
        public Board getBoard() { return board; }
        public int moves() { return move; }        
        
        public int compareTo(SearchNode that) {
            int m1 = this.board.manhattan() + this.move;
            int m2 = that.board.manhattan() + that.move;
            return m1 - m2;
        }
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
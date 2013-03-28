public class Board {

    private int[][] board;
    private int N;
    
    private int blankI;
    private int blankJ;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null)
            throw new IllegalArgumentException();

        N = blocks[0].length;
        board = copy(blocks);
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (isBlank(board[i][j])) {
                    blankI = i;
                    blankJ = j;
                    break;
                }
            }
        }
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (isBlank(board[i][j])) {
                    continue;
                }
                else if (board[i][j] != ((i) * N + j) + 1) {                    
                    // out of space
                    count++;
                }
            }
        }

        return count;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!isBlank(board[i][j])) {
                    int posX = (board[i][j] - 1) / N;
                    int posY = (board[i][j] - 1) % N;

                    dist += Math.abs((posX - i)) + Math.abs((posY - j));
                }
            }
        }

        return dist;
    }

    private boolean isBlank(int value) {
        if (value == 0)
            return true;

        return false;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (!isBlank(board[N-1][N-1]))
            return false;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!isBlank(board[i][j])) {
                    if (board[i][j] != i * N + j + 1)
                        return false;
                }
                else if (i != N - 1 || j != N - 1) {
                    return false;
                }                
            }
        }

        return true;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] twin = this.copy();
        boolean exchange = false;
        while (!exchange) {
            int i = StdRandom.uniform(N);
            int j = StdRandom.uniform(N);
            
            int adjacentJ = j;
            if (adjacentJ == N - 1)
                adjacentJ--;
            else
                adjacentJ++;
            
            if (!isBlank(twin[i][j]) && !isBlank(twin[i][adjacentJ])) {
                int temp = twin[i][j];
                twin[i][j] = twin[i][adjacentJ];
                twin[i][adjacentJ] = temp;
                exchange = true;
            }
        }       
        
        return new Board(twin);
    }
        
    private int[][] copy() {       
        return copy(this.board);
    }
    
    private int[][] copy(int[][] blocks) {  
        int[][] temp = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                temp[i][j] = blocks[i][j];
            }
        }
        
        return temp;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        assert (y != null);
        
        if (!(y instanceof Board))
            return false;
        
        Board other = (Board) y;
        if (this.dimension() != other.dimension())
            return false;                
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.board[i][j] != other.board[i][j])
                    return false;
            }
        }
        
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<Board>();
        
        if (blankI > 0) {
            Board left = new Board(copy());            
            left.board[blankI][blankJ] = left.board[blankI - 1][blankJ];
            left.board[blankI - 1][blankJ] = 0;
            
            --left.blankI;
            neighbors.enqueue(left);
        }
        if (blankI < N - 1) {
            Board right = new Board(copy());
            right.board[blankI][blankJ] = right.board[blankI + 1][blankJ];
            right.board[blankI + 1][blankJ] = 0;
            
            ++right.blankI;            
            neighbors.enqueue(right);
        }
        if (blankJ > 0) {
            Board upper = new Board(copy());
            upper.board[blankI][blankJ] = upper.board[blankI][blankJ - 1];
            upper.board[blankI][blankJ - 1] = 0;
            
            --upper.blankJ;
            neighbors.enqueue(upper);
        }
        if (blankJ < N - 1) {
            Board down = new Board(copy());
            down.board[blankI][blankJ] = down.board[blankI][blankJ + 1];
            down.board[blankI][blankJ + 1] = 0;
            
            ++down.blankJ;            
            neighbors.enqueue(down);
        }                
        
        return neighbors;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(N);
        sb.append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(board[i][j]);

                if (j == N - 1)
                    sb.append("\n");
                else
                    sb.append(" ");
            }
        }

        return sb.toString();
    }
    
    public static void main(String[] args) {   
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        System.out.println("Hamming: " + initial.hamming());
        System.out.println("Manhattan: " + initial.manhattan());
        System.out.println("toString:\n" + initial.toString());
        System.out.println("Twin:\n" + initial.twin().toString());
    }
}

public class Percolation {

    private int N;
    private boolean[] sites;
    private WeightedQuickUnionUF UF;
    private int virtualTopSite;
    private int virtualBottomSite;

    public Percolation(int N) {
        this.N = N;
        sites = new boolean[N*N];
        
        UF = new WeightedQuickUnionUF(N*N+2);
        virtualTopSite = N*N;  
        virtualBottomSite = N*N+1;
        
        for (int j = 1; j <= N; j++) {
            UF.union(getPos(1, j), virtualTopSite);
            UF.union(getPos(N, j), virtualBottomSite);
        }
    }
    
    private int getPos(int i, int j) {
        check(i, j);
        return N*i-(N-j+1);
    }
    
    private void check(int i, int j) {
        if (!exist(i, j)) 
            throw new IndexOutOfBoundsException("index out of bounds");
    }    
    
    public boolean isOpen(int i, int j) {     
        check(i, j);        
        return sites[getPos(i, j)];
    } 
     
    public boolean isFull(int i, int j) {    
        check(i, j);
        
        return isOpen(i, j) && UF.connected(virtualTopSite, getPos(i, j));    
    }

    public void open(int i, int j) {
        check(i, j);
        this.sites[getPos(i, j)] = true;
        
        if (exist(i-1, j) && isOpen(i-1, j)) { 
            UF.union(getPos(i, j), getPos(i-1, j)); 
        }
        
        if (exist(i+1, j) && isOpen(i+1, j)) { 
            UF.union(getPos(i, j), getPos(i+1, j)); 
        }
        
        if (exist(i, j-1) && isOpen(i, j-1)) { 
            UF.union(getPos(i, j), getPos(i, j-1)); 
        }
        
        if (exist(i, j+1) && isOpen(i, j+1)) { 
            UF.union(getPos(i, j), getPos(i, j+1));
        }        
    }
    
    private boolean exist(int i, int j) {
        if (i <= 0 || i > N) { return false; }
        if (j <= 0 || j > N) { return false; }
        
        return true;
    }
    
    public boolean percolates() {
        if (N == 1) return isFull(1, 1);
        
        if (UF.connected(virtualTopSite, virtualBottomSite)) 
            return true;
        
        return false;
    }    
    
    public static void main(String[] args) {
        Percolation per = new Percolation(1);
        //per.open(1, 1);
        System.out.println(per.percolates());
        
        per = new Percolation(2);
        per.open(1, 1);         
        per.open(1, 2);       
        per.open(2, 2);
        System.out.println(per.isFull(1, 1));
    }
}
public class PercolationStats {
    
    private int N; 
    private int T; 
    private double[] results;
    
    public PercolationStats(int N, int T) {        
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException(
                           "N or T must be greater than 0");
        }      
        
        this.N = N;
        this.T = T;
        results = new double[T];
        
        for (int t = 0; t < T; t++) {
            results[t] = run();
        } 
    }
    
    private double run() {        
        Percolation percolation = new Percolation(N);        
        double count = 0;
        
        while (!percolation.percolates()) {        
            count++;
            
            //pick a random site 
            //(N+1 because second value to uniform is exclusive)
            int i = StdRandom.uniform(1, N+1);
            int j = StdRandom.uniform(1, N+1);
            
            //generate new random sites until a blocked one is found
            while (percolation.isOpen(i, j)) {
                
                i = StdRandom.uniform(1, N+1);
                j = StdRandom.uniform(1, N+1);
                
            }
            
            //open that site
            percolation.open(i, j);
        
        }
        return count / (N*N); //percolation threshold estimate
    }
    

    public double mean() {
        return StdStats.mean(results);
    }
    
    public double stddev() {
        return StdStats.stddev(results);
    }
    
    public double confidenceHi() {
        return  mean() - 1.96 * stddev() / Math.sqrt(T);    
    }
    
    public double confidenceLo() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
    
    public static void main(String[] args) {
       
        int N;
        int T;
  
        if (args.length == 0) {
            N = 100;
            T = 10;
        } else {
            N = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }              
        
        //double startTime = System.nanoTime();
        PercolationStats stats =  new PercolationStats(N, T);
        
        double confidenceLow  = stats.confidenceHi();        
        double confidenceHigh = stats.confidenceLo();
                
        System.out.println("mean                    = "  + stats.mean()); 
        System.out.println("stddev                  = "  + stats.stddev());
        System.out.println("95% confidence interval = "  + confidenceLow 
                               + ", " + confidenceHigh);  
        
        // performance measuring
        //double endTime = System.nanoTime();
        //System.out.println("time cost: " + (endTime - startTime));
    
    }
}
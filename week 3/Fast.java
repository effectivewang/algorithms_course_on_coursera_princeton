import java.util.Arrays;

public class Fast {
    private static Point[] printedPoints;
    private static int printCount;
    
    private static Point[] readInput(String fileName) {
         In in = new In(fileName);
         int n = in.readInt();
         Point[] points = new Point[n];
         for (int i = 0; i < n; i++) {
             points[i] = new Point(in.readInt(), in.readInt());
         }
         return points;
     }
          
     private static void sort(Point p, Point[] others) {
         if (p == null || others == null)
             throw new NullPointerException();
     
         Arrays.sort(others, p.SLOPE_ORDER);         
     }
    
     private static boolean hasPrinted(Point[] points) {
         if(points == null)
             throw new NullPointerException();
         
         int equalCount = 0;
         for(int i = 0; i < points.length; i++) {
             for(int j = 0; j < printCount; j++) {
                 if(printedPoints[j] == points[i]) {
                     equalCount++;
                 }
             }
         }
         
         return equalCount == points.length;
     }
     
     private static void display(Point[] points) {
         if (points == null)
             throw new NullPointerException();
         else if (points.length < 3)
             throw new IllegalArgumentException();         
         
         Arrays.sort(points);
         
         for(int i = 0; i < points.length; i++) {
             boolean exist = false;
             for(int j = 0; j < printCount; j++) {
                 if(points[i].compareTo(printedPoints[j]) == 0) {
                     exist = true;
                     continue;
                 }
             }
             
             if(!exist)
                 printedPoints[printCount++] = points[i];
         }
         
         print(points);
         draw(points);
     }
     
     private static void print(Point[] points) {     
         // (14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (21000, 10000)
         for (int i = 0; i < points.length - 1; i++) {
             System.out.print(points[i] + " -> ");
         }
         System.out.println(points[points.length - 1]);
     }
     
     private static void draw(Point[] points) {     
         // draw lines
          for (int i = 0; i < points.length - 1; i++) {     
             points[i].drawTo(points[i+1]);
          }          
     }
     
     private static Point[] getOthers(Point[] points, int i) {
        if (points == null)
             throw new NullPointerException();
         else if (i > points.length - 1 || i < 0)
             throw new IllegalArgumentException();    
            
        Point[] others = new Point[points.length - 1];
        int index = 0; // get other points except itself.
        for (int j = 0; j < points.length; j++) {
            if (j != i)
                others[index++] = points[j];
        }
            
         return others;
     }
     
    public static void main(String[] args) {
        String fileName = args[0];
        Point[] points = readInput(fileName);
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        
        printedPoints = new Point[points.length];
        printCount = 0;
                        
        for (int i = 0; i < points.length; i++) {    
            Point it = points[i];   
            it.draw();
            
            Point[] others = getOthers(points, i);        
            sort(it, others);
            
            Point[] result = null;
            for (int k = 0; k < others.length;) {
                double slope = it.slopeTo(others[k]);
                
                int start = k++;
                int end = k;
                                
                while (end < others.length && slope == it.slopeTo(others[end])) { 
                        end++;
                        k++;
                }                
                
                // If there are not 4 or more points collinear, drop it.
               if ((end - start) < 3) continue;
               
               result = new Point[end - start + 1];
               result[0] = it;
               for (int s = start; s < end; s++) {
                   result[s-start+1] = others[s];
               }
                              
               // display the result if there are 4 or more points collinear
               if(!hasPrinted(result))
                   display(result); 
               
               int existingCount = points.length - result.length;
               if (existingCount < 4) return;                              
            }            
        }
        
        StdDraw.show(0);
    }    
}
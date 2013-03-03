import java.util.Arrays;

public class Brute {
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
     
     private static boolean collinear(Point[] points) {
         if (points == null)
             throw new NullPointerException();
         else if (points.length < 3)
             throw new IllegalArgumentException();
         
         Point p = points[0];
         double slope = p.slopeTo(points[1]);
         
         for (int i = 2; i < points.length; i++) {
             if (slope != p.slopeTo(points[i]))
                 return false;
         }
         
         return true;
     }
     
     private static void display(Point[] points) {
         if (points == null)
             throw new NullPointerException();
         else if (points.length < 4)
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
     
    public static void main(String[] args) {
        String fileName = args[0];
        Point[] points = readInput(fileName);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        
        int N = points.length;
        printedPoints = new Point[N]; // Save the printed points;
        printCount = 0;
        
        // N * N-1 * N-2 * N-3 algorithm
        for (int i = 0; i < N; i++) {
            points[i].draw();
             
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    for (int m = k + 1; m < N; m++) {
                        Point[] target = new Point[4];
                        target[0] = points[i];
                        target[1] = points[j];
                        target[2] = points[k];
                        target[3] = points[m];
                                                
                        if (collinear(target) && !hasPrinted(target)) {
                            display(target);
                        }
                    }
                }
            }
        }
        
        StdDraw.show(0);
    }
}
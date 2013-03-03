/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        public int compare(Point p, Point p2) {
            if (p == null || p2 == null)
                throw new NullPointerException();
            
            Point me = new Point(x, y);
            
            double slope = me.slopeTo(p);
            double slope2 = me.slopeTo(p2);
                        
            if (slope > slope2)
                return +1;
            else if (slope < slope2)
                return -1;
            else
                return 0;
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
        
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {        
        if (that.y == this.y) {
            if (that.x == this.x)
                return Double.NEGATIVE_INFINITY;
            
            return +0;
        } else if (that.x == this.x) {
            return Double.POSITIVE_INFINITY;
        }
        
        
        double yd = (that.y - this.y);
        double xd = (that.x - this.x);
        
        return yd / xd;        
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y)
            return -1;
        else if (this.y > that.y)
            return +1;
        else if (this.x > that.x)
            return +1;
        else if (this.x < that.x)
            return -1;
        else
            return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }    

    // unit test
    public static void main(String[] args) {
        StdDraw.setXscale(0, 5);
        StdDraw.setYscale(0, 5);
            
        Point p = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(2, 3);
        Point p5 = new Point(3, 2);           

        p.drawTo(p2);
        p.drawTo(p3);
        p.drawTo(p4);
        p.drawTo(p5);
        
       System.out.println(p.SLOPE_ORDER.compare(p2, p3));
       System.out.println(p.SLOPE_ORDER.compare(p3, p4));
       System.out.println(p.SLOPE_ORDER.compare(p4, p5));
       
       System.out.println(p.SLOPE_ORDER.compare(p3, p5) == Double.POSITIVE_INFINITY);
       System.out.println(p.SLOPE_ORDER.compare(p3, p4) == Double.NEGATIVE_INFINITY);
    }
}
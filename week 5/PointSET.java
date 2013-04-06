public class PointSET {
   private SET<Point2D> set;
   
    // construct an empty set of points
   public PointSET()                   
   {
       set = new SET<Point2D>();
   }
    // is the set empty?
   public boolean isEmpty()   
   {
       return set.isEmpty();
   }
   
   // number of points in the set
   public int size()   {
       return set.size();
   }                   
   
   // add the point p to the set (if it is not already in the set)
   public void insert(Point2D p) 
   {
       if(p == null)
           throw new IllegalArgumentException();
       
       set.add(p);
   }
   
    // does the set contain the point p?
   public boolean contains(Point2D p)
   {
       if(p == null)
           return false;
       
       return set.contains(p);
   }
   
   // draw all of the points to standard draw
   public void draw() 
   {
       for (Point2D point : set) {
           point.draw();
       }
   }
   
   // all points in the set that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect)   
   {
       if(rect == null)
           throw new IllegalArgumentException();
       
       Queue<Point2D> queue = new Queue<Point2D>();
       for (Point2D point : set) {
           if(rect.contains(point)) {
               queue.enqueue(point);
           }
       }
       
       return queue;
   }
   
   // a nearest neighbor in the set to p; null if set is empty
   public Point2D nearest(Point2D p)    
   {
       if(p == null) return null;
       
       Point2D near = null;
       double distance = Double.MAX_VALUE;
       for (Point2D point : set) {
           double dist = p.distanceTo(point);
           if(dist < distance) {
               distance = dist;
               near = point;
           }
       }
       
       return near;
   }
}
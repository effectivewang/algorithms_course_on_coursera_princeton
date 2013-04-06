public class KdTree {
    
   class TreeNode {
       TreeNode left;
       TreeNode right;
       Point2D point;
       
   }
   
   private TreeNode root;
   
   public KdTree() 
   {
       root = null;
   }
   
    // is the set empty?
   public boolean isEmpty()   
   {
       return root == null;
   }
   
   // number of points in the set
   public int size()   {
       return size(root, 0);
   }
   
   private int size(TreeNode node, int size) {
       if(node == null) return size;
       
       if (node.left != null)
           size(node.left, size + 1);
       if (node.right != null)
           size(node.right, size + 1);
       
       return size;
   }
   
   // add the point p to the set (if it is not already in the set)
   public void insert(Point2D p) 
   {
       if(p == null)
           throw new IllegalArgumentException();
       
       insert(root, p, 1);
   }
   
   private void insert(TreeNode node, Point2D p, int level) {
       if (node == null) return;
       
       TreeNode target = new TreeNode();
       target.point = p;
       
       if(level % 2 == 1) {
           if (p.x() <= node.point.x()) {
               if(node.left == null) {
                   node.left = target;
                   return;
               }
               
               insert(node.left, p, level + 1);
           }
           else{
               if(node.right == null) {
                   node.right = target;
                   return;
               }
               
               insert(node.right, p, level + 1);
           }
       } else {
           if (p.y() <= node.point.y()) {
               if(node.left == null) {
                   node.left = target;
                   return;
               }
               
               insert(node.left, p, level + 1);
           }
           else{
               if(node.right == null) {
                   node.right = target;
                   return;
               }
               
               insert(node.right, p, level + 1);
           }
       }
   }
   
   // does the set contain the point p?
   public boolean contains(Point2D p)
   {
       if(p == null || isEmpty())
           return false;
       
       return contains(root, p, 1);
   }

   private boolean contains(TreeNode node, Point2D p, int level) {
       if(node == null) return false;
       
       if(node.point.equals(p))
           return true;
       
       if(level % 2 == 1) {
           if (p.x() <= node.point.x()) 
               return contains(node.left, p, level + 1);
           else
               return contains(node.right, p, level + 1);
       } else {
           if (p.y() <= node.point.y())
               return contains(node.left, p, level + 1);
           else
               return contains(node.right, p, level + 1);
       }
   }
   
   // draw all of the points to standard draw
   public void draw() 
   {
       draw(root);
   }
   
   private void draw(TreeNode node) {
       if(node == null) return;
       
       node.point.draw();
       
       draw(node.left);
       draw(node.right);
   }
   
   // all points in the set that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect)   
   {
       int level = 1;
       
       Queue<Point2D> queue = new Queue<Point2D>();
       range(rect, root, queue, level);
       
       return queue;
   }
   
   private void range(RectHV rect, TreeNode node, Queue<Point2D> queue, int level) {
       if (node == null)
           return;
       
       if(rect.contains(node.point)) {
           queue.enqueue(node.point);
       }
       
       // At odd level, compare X, at Even level, compare Y
       if (node.left != null) {
           if((level % 2 == 1 && node.point.x() >= rect.xmin())
             || node.point.y() >= rect.ymin()) {
               range(rect, node.left, queue, level + 1);
           }
       }
       if (node.right != null) {      
           if((level % 2 == 1 && node.point.x() <= rect.xmax())
             || node.point.y() <= rect.ymax()) { 
               range(rect, node.right, queue, level + 1);  
           }
       }
   }
   
   // a nearest neighbor in the set to p; null if set is empty
   public Point2D nearest(Point2D p)    
   {
       return nearest(root, p, 1);
   }
   
   private Point2D nearestPoint;
   
   private Point2D nearest(TreeNode node, Point2D p, int level) 
   {
       if (node == null) return null;
       
       if(nearestPoint == null || node.point.distanceTo(p) > node.point.distanceTo(nearestPoint)) {
           nearestPoint = node.point;
       }
       
       if (level % 2 == 1) {
           if ( node.point.x() < p.x() && node.right != null) {
               nearest(node.right, p, level + 1);
           }
           else if ( node.point.x() >= p.x() && node.left != null) {
               nearest(node.left, p, level + 1);
           }
       }
       else {
           if ( node.point.y() < p.y() && node.right != null) {
               nearest(node.right, p, level + 1);
           }
           else if ( node.point.y() >= p.y() && node.left != null) {
               nearest(node.left, p, level + 1);
           }
       }
       
       return nearestPoint;
   }
}
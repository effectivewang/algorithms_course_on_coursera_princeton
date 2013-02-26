public class Deque<Item> implements Iterable<Item> {
    
    private class Node {
        private Item item; // node's item content
        private Node next; // the next node
        private Node prev; // the previous node
    }
    
    private Node first;
    private Node last;
    private int size;
    
   // construct an empty deque
   public Deque() {
       first = null;
       last = null;
       size = 0;
   }
  
   // is the deque empty?
   public boolean isEmpty() {
       return size == 0;
   }        
   
   // return the number of items on the deque
   public int size() {
       return size;
   }       
   
   // insert the item at the front
   public void addFirst(Item item) {
       if (item == null)
           throw new NullPointerException("Can not add null to Deque");
       
       Node node = new Node();
       node.item = item;       
              
       if (first == null) {
           first = node;
           
           if (last == null) {
               last = first;
           }
       } else {
           node.next = first;
           node.prev = null;
           first.prev = node;
       
           if (size == 1) {
               last = first;
           }    
           
           first = node;
       }
       
       
       size++;
       
   }
      
   // insert the item at the end
   public void addLast(Item item) {
       if (item == null)
           throw new NullPointerException("Can not add null to Deque");
       
       Node node = new Node();
       node.item = item;
       
       if (last == null) {
           last = node;
           
           if (first == null) {
               first = last;
           }
       } else {
           last.next = node;
           node.prev = last;
           node.next = null;
                      
           last = node;
       }
       
       size++;
       
   }
  
   // delete and return the item at the front
   public Item removeFirst() {
       if (size == 0)
           throw new java.util.NoSuchElementException("There is no first element");
              
       Item item = first.item;
       
       if (first.next != null)
           first.next.prev = null;
       
       first = first.next;
       size--;
       
       if (size == 0)
           last = null;
       
       return item;
   }        
   
   // delete and return the item at the end
   public Item removeLast() {       
       if (size == 0) 
           throw new java.util.NoSuchElementException("There is no last element");
       
       Item item = last.item;
       
       if (last.prev != null)
           last.prev.next = null;
       
       last = last.prev;
       size--;       
       
       if (size == 0)
           first = null;
       
       return item;
   }    
   
   // return an iterator over items in order from front to end
   public java.util.Iterator<Item> iterator() {
       return new DequeIterator();
   }   
   
   public java.util.Iterator<Item> myIterator() {
       return new MyDequeIterator();
   }   
   
    private class MyDequeIterator implements java.util.Iterator<Item> {
        private Node current = first;
        
        public Item next() {
            if (current == null) 
                throw new java.util.NoSuchElementException();     
            
            Item item = current.item;
            current = current.next;
            
            return item;
        }
        
        public void remove() { 
            if(current.prev != null) {
                current.prev.next = current.next;    
            }
            else {
                current = null;
            }
            
            size--;
        }
        
        public boolean hasNext() {
            return current != null;
        }
    }
   
    private class DequeIterator implements java.util.Iterator<Item> {
        private Node current = first;
        
        public Item next() {
            if (current == null) 
                throw new java.util.NoSuchElementException();     
            
            Item item = current.item;
            current = current.next;
            
            return item;
        }
        
        public void remove() { 
            throw new java.lang.UnsupportedOperationException(); 
        }
        
        public boolean hasNext() {
            return current != null;
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(5);
        System.out.println(!deque.isEmpty() && deque.size() == 1);
        
        deque.addLast(4);
        deque.addLast(3);
        System.out.println(!deque.isEmpty() && deque.size() == 3);
        
        deque.removeFirst();
        System.out.println(deque.size() == 2);
        
        deque.removeFirst();
        System.out.println(deque.size() == 1);
        
        deque.addFirst(1);
        deque.addLast(6);
        for (java.util.Iterator<Integer> it = deque.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }
    }
}
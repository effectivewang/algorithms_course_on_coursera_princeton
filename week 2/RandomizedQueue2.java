public class RandomizedQueue<Item> implements Iterable<Item> {
    private Deque<Item> deque;
    
   // construct an empty randomized queue
    public RandomizedQueue() {
        deque = new Deque<Item>();
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return deque.isEmpty();
    }
    
    // return the number of items on the queue
    public int size() {
        return deque.size();
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        
        deque.addLast(item);
    }
    
    // delete and return a random item
    public Item dequeue() {
        if (size() == 0)
            throw new java.util.NoSuchElementException();
        
        int index = StdRandom.uniform(size());
        Item item =  null;
        
        for (java.util.Iterator<Item> it = deque.myIterator(); it.hasNext();) {
            if (index-- == 0) {
                item = it.next();
                it.remove();
                break;
            }
        }             
        
        return item;
    }
    
    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) 
            throw new java.util.NoSuchElementException();
            
        int index = StdRandom.uniform(size());
        Item item =  get(index);        
         
         return item;
    }
    
    private Item get(int index) {
        int i = 0;
        for (java.util.Iterator<Item> it = deque.myIterator(); it.hasNext();) {
            if (i++ == index) {
                Item item = it.next();
                return item;
            }
        }
        
        return null;
    }
    
    // return an independent iterator over items in random order
    public java.util.Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }  
    
    private class RandomizedQueueIterator implements java.util.Iterator<Item>
    {
        private int[] indices;
        private int index;
        
        public RandomizedQueueIterator() {
            indices = new int[size()];
            for (int i = 0; i < size(); i++)
                indices[i] = i;
            
            StdRandom.shuffle(indices);
        }
        
        public boolean hasNext() {
            return index < indices.length;
        }
        
        public void remove() {
            throw new UnsupportedOperationException(); 
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            
            int curIndex = indices[index++];
            return get(curIndex);
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        
        System.out.println(queue.size() == 10);
        
        queue.dequeue();
        queue.dequeue();
        System.out.println(queue.size() == 8);
        
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        System.out.println(queue.size() == 4);
    }
}
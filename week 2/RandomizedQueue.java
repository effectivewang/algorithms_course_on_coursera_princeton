public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] items;
    
   // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[10];
        size = 0;
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    private int ensureCapacity() {
        if (items.length == size) {
            int newSize = items.length * 2;
            Item[] newItems = (Item[]) new Object[newSize];
            copy(items, newItems, size);
            
            items = newItems;
        } else if (items.length <= size / 2) {            
            Item[] newItems = (Item[]) new Object[size / 2];
            copy(items, newItems, size / 2);
            
            items = newItems;
        }
        
        return items.length;
    }
    
    private void copy(Item[] oldItems, Item[] newItems, int newSize) {
        for (int i = 0; i < newSize; i++) {
            newItems[i] = oldItems[i];
        }
    }
    
    // return the number of items on the queue
    public int size() {
        return size;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        
        ensureCapacity();
        items[size] = item;
        
        size++;
    }
    
    // delete and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new java.util.NoSuchElementException();
        
        int index = StdRandom.uniform(size);
        Item item =  items[index];
        
        items[index] = null;
        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }
        
        size--;        
        //ensureCapacity();
        
        return item;
    }
    
    // return (but do not delete) a random item
    public Item sample() {
        if (size == 0) 
            throw new java.util.NoSuchElementException();
            
        int index = StdRandom.uniform(size);
        return items[index];
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
            indices = new int[size];
            for (int i = 0; i < size; i++)
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
            return items[curIndex];
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
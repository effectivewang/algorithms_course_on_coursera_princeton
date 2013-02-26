public class Subset {
    public static void main(String[] args) {
        if (args == null || args.length < 1) 
            throw new java.lang.IllegalArgumentException();
        
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        
        while (k > 0) {
            System.out.println(queue.dequeue());
            k--;
        }
    }
}
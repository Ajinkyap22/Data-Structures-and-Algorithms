import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;


    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        n = 0;
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }


    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }


    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (n == items.length) resize(2 * n);
        items[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int random = StdRandom.uniform(n);

        // If the item isn't last, then move last one to its index
        if (random != n - 1) {
            items[random] = items[n - 1];
        }

        Item item = items[--n];
        items[n] = null;

        if (n == (items.length / 4)) resize(items.length / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        return items[StdRandom.uniform(n)];
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int s;
        private final Item[] copyData;

        private RandomizedQueueIterator() {

            copyData = (Item[]) new Object[n];

            for (int i = 0; i < n; i++) {
                copyData[i] = items[i];
            }
            StdRandom.shuffle(copyData);
        }


        public boolean hasNext() {
            return s <= n - 1;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            return copyData[s++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Create Queue
        RandomizedQueue<String> RQ = new RandomizedQueue<>();

        // isEmpty()
        StdOut.println("Empty ? " + RQ.isEmpty());
        StdOut.println("Size: " + RQ.size());

        // Enqueue
        RQ.enqueue("Zero");
        StdOut.println("Adding Zero");

        RQ.enqueue("One");
        StdOut.println("Adding One");

        RQ.enqueue("Two");
        StdOut.println("Adding Two");

        // Sample
        StdOut.println("Sample: " + RQ.sample());

        // Dequeue
        StdOut.println("Removing Randomly");
        RQ.dequeue();

        // Size
        StdOut.println("Size: " + RQ.size());

        // Iterator
        StdOut.println("Iterating over the Deque");
        for (String item : RQ) {
            StdOut.println(item);
        }

        // Add null
        // StdOut.println("Adding null");
        // RQ.enqueue(null);

        // dequeue/sample when empty
        // RQ.dequeue();
        // RQ.dequeue();
        // StdOut.println("Empty ? " + RQ.isEmpty());
        // RQ.sample();
        // RQ.dequeue();
    }


}

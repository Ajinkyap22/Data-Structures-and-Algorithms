import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queque<Item> implements Iterable<Item> {

    private Node first, last;
    private int n;

    private class Node {
        private Item item;
        private Node next;
    }

    // construct an empty queue
    public Queque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }


    // add the item to the back
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;

        if (isEmpty()) first = last;
        else oldlast.next = last;

        n++;
    }

    // remove and return the item from the front
    public Item queueue() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;
        n--;

        if (isEmpty()) last = null;

        return item;
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }

    }


    public static void main(String[] args) {
        Queue<String> queue = new Queue<>();

        // Check if empty
        StdOut.println("is Empty? " + queue.isEmpty());

        // Enqueue
        queue.enqueue("One");
        StdOut.println("adding One");

        queue.enqueue("Zero");
        StdOut.println("adding Zero");

        // Check size
        StdOut.println("Size = " + queue.size());
        StdOut.println("is Empty? " + queue.isEmpty());

        // Remove Last
        StdOut.println("Removing " + queue.dequeue());

        // Add null
        // StdOut.println("Adding null");
        // queue.enqueue(null);


        StdOut.println("Size = " + queue.size());

        // Iterator
        StdOut.println("Iterating over the queue");
        for (String item : queue) {
            StdOut.println(item);
        }

        // remove when empty
        // StdOut.println("Removing " + queue.dequeue());
        // StdOut.println("Removing " + queue.dequeue());
        // StdOut.println("Size = " + queue.size());
        // queue.dequeuet();
    }

}

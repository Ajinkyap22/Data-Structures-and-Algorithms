import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int n;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;

        if (isEmpty()) last = first;
        else oldfirst.prev = first;

        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;

        if (isEmpty()) first = last;
        else oldlast.next = last;

        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;
        n--;

        if (isEmpty()) last = null;
        else first.prev = null;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.item;
        last = last.prev;
        n--;

        if (isEmpty()) first = null;
        else last.next = null;

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

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        // Check if empty
        StdOut.println("is Empty? " + deque.isEmpty());

        // Add first
        deque.addFirst("One");
        StdOut.println("adding One at first.");

        deque.addFirst("Zero");
        StdOut.println("adding Zero at first.");

        // Add last
        deque.addLast("Two");
        StdOut.println("adding Two at last.");

        deque.addLast("Three");
        StdOut.println("adding Three at last.");

        // Check size
        StdOut.println("Size = " + deque.size());
        StdOut.println("is Empty? " + deque.isEmpty());

        // Remove First
        StdOut.println("Removing " + deque.removeFirst() + " from first");

        // Remove Last
        StdOut.println("Removing " + deque.removeLast() + " from last");

        // Add null
        // StdOut.println("Adding null");
        // deque.addFirst(null);
        // deque.addLast(null);


        StdOut.println("Size = " + deque.size());

        // Iterator
        StdOut.println("Iterating over the Deque");
        for (String item : deque) {
            StdOut.println(item);
        }

        // remove when empty
        // StdOut.println("Removing " + deque.removeFirst() + " from first");
        // StdOut.println("Removing " + deque.removeLast() + " from last");
        // StdOut.println("Size = " + deque.size());
        // deque.removeFirst();
        // deque.removeLast();
    }

}

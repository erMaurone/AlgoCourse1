package lesson2.randomizedQueuesDeques;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 08/11/2014
 * Time: 21:38
 *
 */

public class Deque<Item> {

    private Node first, last;
    private int size;

    // construct an empty deque
    public Deque() {

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
        if (item == null ) throw new NullPointerException("Cannot add null item");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        size++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null ) throw new NullPointerException("Cannot add null item");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.previous = oldlast;
        size++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty() ) throw new NoSuchElementException("Deque is empty");
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty())
            last = null;
        return item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty() ) throw new NoSuchElementException("Deque is empty");
        Item item = last.item;
        last = last.previous;
        size--;
        if (isEmpty())
            first = null;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
         return new DequeIterator();
    }

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private class DequeIterator implements Iterator<Item> {

        Node current = first;

        @Override
        public Item next() {
            if (! hasNext()) throw new NoSuchElementException("Deque is empty");
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new  UnsupportedOperationException("Invalid operation");
        }
    }

    // unit testing
    public static void main(String[] args) {


    }
}

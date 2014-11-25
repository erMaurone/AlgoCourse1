package lesson2.dataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 07/11/2014
 * Time: 23:15
 * To change this template use File | Settings | File Templates.
 */
public class ArrayStackGenerics<Item> implements Iterable<Item> {
    private Item[] s;
    private int N = 0;

    @SuppressWarnings("unchecked")
    public ArrayStackGenerics(int capacity) {
        // s = new Item[capacity]; ==> not allowed in java
        s = (Item[]) new Object[capacity];
    }

    public Item[] asArray() {
        return s;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(
            Item
                    item) {
        s[N++] = item;
    }

    public Item
    pop() {
        return s[--N];
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private int current = 0;

        public boolean hasNext()  { return s[current] != null; }
        public void remove()      { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = (Item) s[current];
            current++;
            return item;
        }
    }
}

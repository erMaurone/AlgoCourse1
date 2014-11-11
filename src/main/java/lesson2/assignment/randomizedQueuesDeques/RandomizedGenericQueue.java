package lesson2.assignment.randomizedQueuesDeques;

import princeton.stdlib.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 09/11/2014
 * Time: 00:37
 */

/**
 * invariants
 * 1) array is filled continuously
 * 2) randomness is form 0 to last item
 * 3) whenever adding or removing an item array continuity is preserved
 */

public class RandomizedGenericQueue<Item> {
    int head, tail;
    Item items[];

    // construct an empty randomized queue
    @SuppressWarnings("unchecked")
    public RandomizedGenericQueue() {
        items = (Item[])new Object[2];
    }

    // is the queue empty?
    public boolean isEmpty()   {
        return (size() == 0);
    }

    // return the number of items on the queue
    public int size() {
        return tail - head;
    }

    // add the item
    public void enqueue(Item item) {
        if (enqueueCheck(item)) return;
        items[tail] = item;
        int insertRandom = generateRandom();
        exch(items, tail, insertRandom);
        tail++;
    }

    private boolean enqueueCheck(Item item) {
        if (item == null ) throw new NullPointerException("Expected item");
        if (tail == head) {
            tail++;
            items[head] = item;
            return true;
        }
        if (tail == items.length) resize(2 * items.length);
        return false;
    }

    // delete and return a random item
    public Item dequeue() {
        dequeueCheck();
        int randomIndex = generateRandom();
        Item value = items[randomIndex];
        items[randomIndex] = null;
        tail--;
        if (size() > 1)
            exch(items, randomIndex, tail);
        return value;
    }

    private void dequeueCheck() {
        if (isEmpty() ) throw new NoSuchElementException();
        if (tail - head == items.length/4) resize(items.length/2);
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty() ) throw new NoSuchElementException();
        return items[generateRandom()];
    }

    private void resize(int capacity){
        Item[] copy = (Item[])new Object[capacity];
        for (int i = 0; i < tail; i++)
            copy[i] = items[i];
        items = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < tail;
            }
            @Override
            public Item next() {
                if (isEmpty()) throw new NoSuchElementException();
                Item item = items[index];
                index++;
                return item;
            }
            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
    }

    private int generateRandom() {
        return StdRandom.uniform(head, (tail));
    }

    private void exch(Item[] a, int i, int j){
        Item swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // unit testing
    public static void main(String[] args) {}
}

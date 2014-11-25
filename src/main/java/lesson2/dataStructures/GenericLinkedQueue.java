package lesson2.dataStructures;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 11/11/2014
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public class GenericLinkedQueue<Item extends Comparable> {

    private Node first, last;
    int N;

    private class Node {
        Item item;
        Node next;
    }

    public int size() {return N; }

    public boolean isEmpty() { return first == null; }

    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        N++;
    }

    public Item dequeue() {
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        N--;
        return item;
    }


    public boolean contains(Item item, Item other) {
        int matches = 0;
        Node current = first;
        while (current != null && matches < 2) {
            Item i = current.item;
            if (i.compareTo(item) == 0 )
                matches++;
            if (i.compareTo(other) == 0)
                matches++;
            current = current.next;
        }
        return matches == 2;
    }

    public Item[] asArray(Item[] items, int size) {
        Node current = first;
        int i = size;
        while (current != null ) {
            items[i++] = current.item;
            current = current.next;
        }
        return items;
    }
}

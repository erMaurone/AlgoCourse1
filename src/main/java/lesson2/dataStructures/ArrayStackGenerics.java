package lesson2.dataStructures;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 07/11/2014
 * Time: 23:15
 * To change this template use File | Settings | File Templates.
 */
public class ArrayStackGenerics<Item> {
    private
    Item[] s;
    private int N = 0;

    @SuppressWarnings("unchecked")
    public ArrayStackGenerics(int capacity) {
        // s = new Item[capacity]; ==> not allowed in java
        s = (Item[]) new Object[capacity];
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
}

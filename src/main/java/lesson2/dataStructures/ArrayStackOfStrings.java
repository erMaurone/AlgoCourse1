package lesson2.dataStructures;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 07/11/2014
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */
public class ArrayStackOfStrings {
    private String[] s;
    private int N = 0;

    public ArrayStackOfStrings (int capacity) {
        s = new String[capacity];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(String item) {
        s[N++] = item;
    }

    // need to null the returned item array reference to allow GC
    public String pop() {
        String item = s[--N];
        s[N] = null;
        return item;
    }
}

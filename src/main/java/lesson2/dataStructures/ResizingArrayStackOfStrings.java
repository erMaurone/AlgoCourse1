package lesson2.dataStructures;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 07/11/2014
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class ResizingArrayStackOfStrings {
    private String[] s;
    private int N = 0;

    public ResizingArrayStackOfStrings() {
        s = new String[1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(String item) {
        if (N == s.length) resize(2 * s.length);
        s[N++] = item;
    }

    private void resize(int capacity){
        String[] copy = new String[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }

    // need to null the returned item array reference to allow GC
    public String pop() {
        String item = s[--N];
        s[N] = null;
        if (N > 0 && N == s.length/4) resize(s.length/2);
        return item;
    }
}

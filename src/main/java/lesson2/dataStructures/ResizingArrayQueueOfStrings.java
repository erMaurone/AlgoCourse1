package lesson2.dataStructures;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 07/11/2014
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */
public class ResizingArrayQueueOfStrings {

    private String[] s;
    private int head = 0, tail= 0;

    public ResizingArrayQueueOfStrings() {
        s = new String[1];
    }

    public boolean isEmpty() {
        return tail == 0;
    }

    public void enqueue(String item) {
        if (tail == s.length) resize(2 * s.length);
        s[tail] = item;
        tail++;
    }

    private void resize(int capacity){
        String[] copy = new String[capacity];
        for (int i = 0; i < tail; i++)
            copy[i] = s[i];
        s = copy;
    }

    public String dequeue() {
        if (tail == 0) throw new UnsupportedOperationException("no item in the queue");
        String item = s[--head];
        s[head] = null;
        if (tail - head == s.length/4) resize(s.length/2);
        return item;
    }

}

package lesson2.dataStructures;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 07/11/2014
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class ArrayQueueOfStrings {
    private String[] s;
    private int head = 0, tail= 0;

    public ArrayQueueOfStrings(int capacity) {
        s = new String[capacity];
    }

    public boolean isEmpty() {
        return tail == 0;
    }

    public void enqueue(String item) {
        s[tail] = item;
        tail++;
    }

    public String dequeue() {
        if (tail == 0) throw new UnsupportedOperationException("no item in the queue");
        String item = s[--head];
        s[head] = null;
        return item;
    }
}

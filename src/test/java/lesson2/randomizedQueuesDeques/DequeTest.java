package lesson2.randomizedQueuesDeques;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 08/11/2014
 * Time: 22:26
 */
public class DequeTest {
    Deque<String> stringDeque = new Deque<String >();

    @Test
    public void testIsEmpty() throws Exception {
        Assert.assertTrue(stringDeque.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        int expectedSize = 0;

        Assert.assertTrue("deque is empty", expectedSize == stringDeque.size());
        stringDeque.addFirst("first");
        expectedSize++;
        Assert.assertTrue("items in deque = " + expectedSize, expectedSize == stringDeque.size());
        stringDeque.addLast("last");
        expectedSize++;
        Assert.assertTrue("items in deque = " + expectedSize, expectedSize == stringDeque.size());
        stringDeque.removeFirst();
        stringDeque.removeLast();
        expectedSize = 0;
        Assert.assertTrue("deque is empty", expectedSize == stringDeque.size());
    }

    @Test (expected = NullPointerException.class)
    public void testAddFirstNull() {
        stringDeque.addFirst(null);
    }

    @Test (expected = NullPointerException.class)
    public void testAddLastNull() {
        stringDeque.addLast(null);
    }


    @Test
    public void testIterator() throws Exception {

        int expectedCount = 4;
        int count = 0;
        stringDeque.addFirst("first");
        stringDeque.addFirst("second");
        stringDeque.addFirst("third");
        stringDeque.addFirst("fourth");
        Iterator di = stringDeque.iterator();
        while (di.hasNext()) {
            di.next();
            count++;
        }

        Assert.assertEquals(expectedCount, count);
        for (; count > 0; count--)
            stringDeque.removeFirst();
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testIteratorInvariant() {

        stringDeque.addFirst("first");
        Iterator di = stringDeque.iterator();

        while (di.hasNext()) {
            di.remove();
        }

    }
}

package lesson2.randomizedQueuesDeques;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 09/11/2014
 * Time: 00:42 */


 public class RandomizedGenericQueueTest {

    private RandomizedGenericQueue<String> randomStrings;
    @Before
    public void setUp() throws Exception {

        randomStrings = new RandomizedGenericQueue<>();
    }

    @After
    public void tearDown() throws Exception {
        randomStrings = null;
    }

    @Test
    public void testIsEmpty() throws Exception {
        int size = randomStrings.size();
        Assert.assertTrue((size == 0) == randomStrings.isEmpty());

    }

    @Test
    public void testSize() throws Exception {
        int originalSize = randomStrings.size();
        randomStrings.enqueue("test size +1");
        randomStrings.enqueue("test size +2");
        int expectedSize = 2;
        Assert.assertEquals(expectedSize, randomStrings.size());
        for(int i = 1; i <= expectedSize;i++)
            randomStrings.dequeue();
        Assert.assertEquals(originalSize, randomStrings.size());

    }

    @Test (expected = NullPointerException.class)
    public void testEnqueueNull() throws Exception {
        randomStrings.enqueue(null);
    }

    @Test (expected = NoSuchElementException.class)
    public void testDequeueEmpty() throws Exception {
        for (int i = 0; i == randomStrings.size(); i++)
            randomStrings.dequeue();
    }

    @Test
    public void testSampleEmpty() throws Exception {
        for (int i = 1; i == randomStrings.size(); i++)
            randomStrings.sample();
    }

    @Test (expected = NoSuchElementException.class)
    public void testIteratorEmpty() throws Exception {
        Iterator iter = randomStrings.iterator();
        while (iter.hasNext())
            iter.next();

        iter.next();
    }
}

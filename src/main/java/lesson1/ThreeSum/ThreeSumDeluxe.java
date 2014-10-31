package lesson1.ThreeSum;

import utils.FileIn;
import utils.FileOut;
import princeton.stdlib.Stopwatch;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 28/10/2014
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
public class ThreeSumDeluxe {
    private static int count(int[] a)
    {
        int N = a.length;
        Arrays.sort(a);
        if (containsDuplicates(a))
            throw new IllegalArgumentException("array contains duplicates integers");
        int count = 0;
        for (int i = 0; i < N; i++)
            for (int j = i+1; j < N; j++) {
                int k = Arrays.binarySearch(a, -(a[i]+a[j]));
                if (k > j) count++;
            }
        return count;
    }

    private static boolean containsDuplicates(int[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i] == a[i-1]) return true;
        return false;
    }

    public static void main(String[] args)
    {
        Stopwatch stopwatch = new Stopwatch();
        int[] a = FileIn.readInts(args[0]);
        publish("no. of ints in file: " + a.length);
        publish("zero-sum combinations: " + count(a));
        double time = stopwatch.elapsedTime();
        publish("Elapsed time: " + time);
    }

    private static void publish(String content) {
        System.out.println(content);
        FileOut.out(content);
    }
}

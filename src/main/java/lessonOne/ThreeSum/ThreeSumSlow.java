package lessonOne.ThreeSum;

import utils.FileIn;
import utils.FileOut;
import utilsPrinceton.Stopwatch;

public class ThreeSumSlow {
        private static int count(int[] a)
        {
            int N = a.length;
            int count = 0;
            for (int i = 0; i < N; i++)
                for (int j = i+1; j < N; j++)
                    for (int k = j+1; k < N; k++) {
                        if (a[i] + a[j] + a[k] == 0)
                            count++;
                    }
            return count;
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

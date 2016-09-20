package lesson2.assignment.randomizedQueuesDeques;


public class Subset {


    public static void main(String[] args) {
        RandomizedQueue<String> randomStrings = new RandomizedQueue<>();

        if (args.length < 1) throw new IllegalArgumentException("Subject <no. of outputs> [input1, input2, ...]");
        int outputs = Integer.valueOf(args[0]);
        if (outputs >= args.length) throw new IllegalArgumentException("Subject <no. of outputs> [input1, input2, ...]");

        for (int i= 1; i< args.length; i++)
            randomStrings.enqueue(args[i]);

        for (int i = 0; i < outputs; i++)
           System.out.println( randomStrings.dequeue() );

    }
}

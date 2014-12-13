package algoCourse2.lesson1.assignment.worldnet;

import princeton.stdlib.In;
import princeton.stdlib.StdOut;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 13/12/2014
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */
public class Outcast {
    private final WordNet wordNet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int[] sums = new int[nouns.length];
        int distance = 0;
        String outcast = null;

        for (int i = 0; i < nouns.length;i++) {
            for(int j = i+1;j< nouns.length;j++) {
                int pairDistance = wordNet.distance(nouns[i], nouns[j]);
                sums[i] = sums[i] + pairDistance;
                sums[j] = sums[j] + pairDistance;
            }
        }
        for(int i = 0; i < nouns.length; i++) {
            int currentDist = sums[i];
            if (currentDist > distance) {
                distance = currentDist;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    // see test client below
    public static void main(String[] args) {

        WordNet wordnet = new WordNet(args[0], args[1]);

        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}

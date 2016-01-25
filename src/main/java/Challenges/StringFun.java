package Challenges;

import princeton.stdlib.StdOut;

/**
 * Created by mauro on 18/12/2015.
 */
public class StringFun {

    private StringFun() {}

    public static String mystery(String s) {
        int N = s.length();
        if (N <= 1) return s;
        String a = s.substring(0, N/2);
        String b = s.substring(N/2, N);
        return mystery(b) + mystery(a);
    }

    public static boolean genomicSequence(String s, String t) {
        return (s.length() == t.length()) && (s.concat(s).indexOf(t) >= 0);}

    public static void mutateImmutable() {
        String string1 = "I am immutable";
        String string2 = string1;
        string1 = "I am mutable";
        System.out.println(string1);
        System.out.println(string2);
    }

    public static void main(String[] args) {
        mutateImmutable();
        StdOut.println(mystery("A man a plan a canal Panama"));
        StdOut.println(genomicSequence("ACTGACG", "TGACGAC"));
    }
}

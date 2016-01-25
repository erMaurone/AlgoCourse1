package Challenges.ProgrammingModel.binomialDistribution;

import princeton.algs4.Stopwatch;

import java.util.Scanner;

/**
 * Created by mauro on 19/01/2016.
 */
public class BinomialEstimator {

    private static int count = 0;

    public BinomialEstimator(int N, int k) {
        Stopwatch timer = new Stopwatch();
        countRecursions(N,k);
        double elapsedSeconds = timer.elapsedTime();
        System.out.printf("(%d executed)", count);
        System.out.printf("(%.2f seconds)\n", elapsedSeconds);

    }

    private static void countRecursions(int N, int k ) {
        count++;
        if (N == 0 && k == 0) return ;
        if (N < 0 || k < 0) return ;

        countRecursions(N-1, k);
        countRecursions(N-1, k-1);
    }

    public static void estimateRecursions(int N, int K) {
        Stopwatch timer = new Stopwatch();
        BinomialFun bf = new BinomialFun(N,K);
        double elapsedSeconds = timer.elapsedTime();
        System.out.println("N = " + N + ", K = " + K);
        System.out.printf("(%d estimated)", bf.iterations());
        System.out.printf("(%.2f seconds)\n", elapsedSeconds);
    }

    public static void main(String[] args) {
/*        Scanner sc = new Scanner(System.in);
        System.out.println("Enter N and k: ");
        int N = sc.nextInt();
        int K = sc.nextInt();*/

        for (int N=2,K=1; N < 40;K=K*5/2,N=N*5/2) {
            estimateRecursions(N,K);
            new BinomialEstimator(N,K);
        }

        estimateRecursions(41,30);
        new BinomialEstimator(41,30);
        estimateRecursions(100,50);


    }

}

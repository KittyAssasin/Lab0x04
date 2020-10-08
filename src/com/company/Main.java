package com.company;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        int n;
        int nStart = 32; //min 3
        int nSteps = 24;
        int nScale = 2;
        long startTime;
        int maxTrials = 30;
        int trialCount;
        long maxTime = 5_000_000_000L;

        String[] methodNames = {"fibRecur", "fibCache", "fibLoop", "fibMatrix"};

        int numMethods = methodNames.length;
        long[][] times = new long[numMethods][nSteps];

        //multi-method testing loop
        for (int methodID = 0; methodID < numMethods; methodID++) {
            n = nStart;
            for (int i = 0; i < nSteps; i++) {
                System.out.print(methodNames[methodID] + " n=" + n + " step=" + i);
                times[methodID][i] = -1; //set time to negative one to show no results when printing
                for (trialCount = 0; (trialCount < maxTrials) && (times[methodID][i] < maxTime); trialCount++) {
                    switch (methodID) {
                        case 0 -> {
                            startTime = System.nanoTime();
                            fibRecur(n);
                            times[methodID][i] += System.nanoTime() - startTime;
                        }
                        case 1 -> {
                            startTime = System.nanoTime();
                            fibCache(n);
                            times[methodID][i] += System.nanoTime() - startTime;
                        }
                        case 2 -> {
                            startTime = System.nanoTime();
                            fibLoop(n);
                            times[methodID][i] += System.nanoTime() - startTime;
                        }
                        case 3 -> {
                            startTime = System.nanoTime();
                            fibMatrix(n);
                            times[methodID][i] += System.nanoTime() - startTime;
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + methodID);
                    }
                }
                n *= nScale;
                System.out.println(" TrialCount=" + trialCount);
                times[methodID][i] /= trialCount;
            }
        }

        //print results
        String column1HeaderFormat = "%10s|";
        String tableHeaderFormat = "%13s%12s%15s|";
        String tableFirstEntryFormat = "%13d%12s%15s|";
        String column1Format = "%10d|";
        String tableEntryFormat = "%13d%12.3f%15.3f|";

        //configuration string
        System.out.println("Results with nStart=" + nStart + ", nSteps=" + nSteps + ", maxTrials=" + maxTrials);

        //header
        System.out.format(column1HeaderFormat, "");
        for (int i = 0; i < numMethods; i++)
            System.out.format(tableHeaderFormat, "", methodNames[i], "");
        System.out.println();

        System.out.format(column1HeaderFormat, "N");
        for (int i = 0; i < numMethods; i++)
            System.out.format(tableHeaderFormat, "Time", nScale + "x Ratio", "Ex. " + nScale + "x Ratio");
        System.out.println();

        n = nStart;
        //first entry (has N/A entries, needs special handling)
        System.out.format(column1Format, n);
        for (int i = 0; i < numMethods; i++)
            System.out.format(tableFirstEntryFormat, times[i][0], "N/A", "N/A");
        System.out.println();
        n *= nScale;

        //printing remaining entries
        for (int i = 1; i < nSteps; i++) {
            System.out.format(column1Format, n);
            for (int k = 0; k < numMethods; k++)
                System.out.format(tableEntryFormat, times[k][i], (double) times[k][i] / times[k][i-1], 2.0);
            System.out.println();
            n *= nScale;
        }
    }

    //fib recursive
    private static int fibRecur(int nth) {

        return -1;
    }

    //fib cached
    private static int fibCache(int nth) {

        return -1;
    }

    //classic fib
    private static int fibLoop(int nth) {

        return -1;
    }

    //fibMatrix
    private static int fibMatrix(int nth) {

        return -1;
    }
}

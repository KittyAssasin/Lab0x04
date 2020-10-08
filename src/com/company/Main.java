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

        int[] generatedArray;

        //generic multi-method testing loop
        for (int methodID = 0; methodID < numMethods; methodID++) {
            n = nStart;
            for (int i = 0; i < nSteps; i++) {
                System.out.print(methodNames[methodID] + " n=" + n + " step=" + i);
                times[methodID][i] = 0;
                for (trialCount = 0; (trialCount < maxTrials) && (times[methodID][i] < maxTime); trialCount++) {
                    generatedArray = generateUniqueArray(n, -n * 2, n * 2);
                    switch (methodID) {
                        case 0:
                            startTime = System.nanoTime();
                            threeSumBrute(generatedArray);
                            times[methodID][i] += System.nanoTime() - startTime;
                            break;
                        case 1:
                            startTime = System.nanoTime();
                            threeSumFast(generatedArray);
                            times[methodID][i] += System.nanoTime() - startTime;
                            break;
                        case 2:
                            startTime = System.nanoTime();
                            threeSumFastest(generatedArray);
                            times[methodID][i] += System.nanoTime() - startTime;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + methodID);
                    }
                }
                n *= nScale;
                System.out.println(" TrialCount=" + trialCount);
                times[methodID][i] /= trialCount;
            }
        }

        //print results
        String headerFormatString = "%10s|%13s%12s%15s|%13s%12s%15s|%13s%12s%15s|\n";
        String tableFormatString = "%10d|%13d%12.3f%15.3f|%13d%12.3f%15.3f|%13d%12.3f%15.3f|\n";
        String firstTableFormatString = "%10d|%13d%12s%15s|%13d%12s%15s|%13d%12s%15s|\n";
        System.out.println("3sum results with nStart=" + nStart + ", nSteps=" + nSteps + ", maxTrials=" + maxTrials);
        System.out.format(headerFormatString, "", "", "Brute 3sum", "", "", "Fast 3sum", "", "", "Fastest 3sum", "");
        System.out.format(headerFormatString, "N",
                "Time", nScale + "x Ratio", "Ex. " + nScale+"x Ratio",
                "Time", nScale + "x Ratio", "Ex. " + nScale+"x Ratio",
                "Time", nScale + "x Ratio", "Ex. " + nScale+"x Ratio");
        n = nStart;
        System.out.format(firstTableFormatString, n, method1Times[0], "N/A", "N/A", method2Times[0], "N/A", "N/A", method3Times[0], "N/A", "N/A");
        n *= nScale;
        for (int i = 1; i < nSteps; i++) {
            System.out.format(tableFormatString, n,
                    method1Times[i], (double) method1Times[i] / method1Times[i-1], 2.0,
                    method2Times[i], (double) method2Times[i] / method2Times[i-1], 2.0,
                    method3Times[i], (double) method3Times[i] / method3Times[i-1], 2.0);
            n *= nScale;
        }
    }

    //3sum problem: are there three numbers in this list that sum to 0?
    //3sum brute force (N^3)
    private static boolean threeSumBrute(int[] array) {
        int len = array.length;

        for (int i = 0; i < len-2; i++)
            for (int k = i+1; k < len-1; k++)
                for (int m = k+1; m < len; m++)
                    if ((array[i] + array[k] + array[m]) == 0)
                        return true;
        return false;
    }

    //3sum faster (N^2 * log(N))
    private static boolean threeSumFast(int[] array) {
        int len = array.length;
        Arrays.sort(array);
        for (int i = 0; i < len-2; i++)
            for (int k = i+1; k < len-1; k++) {
                int result = Arrays.binarySearch(array, -array[i] - array[k]);
                if (result >= 0) //check if key found
                    return true;
            }
        return false;
    }

    //3sum fastest (N^2)
    private static boolean threeSumFastest(int[] array) {
        int len = array.length;
        Arrays.sort(array);
        for (int i = 0; i < len - 2; i++) {
            int leftIndex = i + 1;
            int rightIndex = len - 1;
            while (leftIndex < rightIndex) {
                int sum = array[i] + array[leftIndex] + array[rightIndex];
                if (sum == 0)
                    return true;
                else if (sum > 0) //if the sum is > 0, reduce the size of the largest element
                    rightIndex--;
                else             //otherwise increase the size of the smallest element
                    leftIndex++;
            }
        }
        return false;
    }

    private static int[] generateUniqueArray(int count, int min, int max) {
        Set<Integer> set = new HashSet<>();
        Random r = new Random();
        while (set.size() < count)
            set.add(r.nextInt(max - min + 1) + min);
        return set.stream().mapToInt(i -> i).toArray();
    }

}
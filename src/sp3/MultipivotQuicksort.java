package sp3;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by tejas on 2/23/2016.
 */
//b. Multipivot Quicksort
public class MultipivotQuicksort {

    public static void main(String args[]) {

        int[] A = {1, 5, 2, 2, 4, 9, 2, 2, 7, 2, 4, 4, 6, 1, 0, -10};
//        quickSort(A, 0, A.length - 1);
        dualPivotSort(A, 0, A.length - 1);

        System.out.println(Arrays.toString(A));
    }

    private static void dualPivotSort(int[] A, int start, int end) {
        int[] portion = dualPivotPartition(A, start, end);
        if (portion != null) {
            dualPivotSort(A, start, portion[0] - 1);
            if (portion[2] != 1) dualPivotSort(A, portion[0] + 1, portion[1]);
            dualPivotSort(A, portion[1], end);
        }
    }

    public static void quickSort(int[] A, int start, int end) {
        if (start < end) {
            int q = partition1(A, start, end);
            quickSort(A, start, q - 1);
            quickSort(A, q + 1, end);
        }
    }

    public static void swap(int[] A, int a, int b) {
        int temp = A[a];
        A[a] = A[b];
        A[b] = temp;
    }


    public static int[] dualPivotPartition(int[] A, int start, int end) {
        if (start >= end) return null;
        int p = getRandom(start, end) + start;
        int q = getRandom(start, end) + start;
//        do{
//            q=getRandom(start,end)+start;
//        }while(p==q);
        swap(A, p, start);
        swap(A, q, end);
//        p=2;
//        q=3;
        if (A[start] > A[end]) {
            swap(A, start, end);
        }
        int x1 = A[start];
        int x2 = A[end];
        int i = start + 1;
        int l = start + 1;
        int j = end - 1;
        while (i <= j) {
            //case 1
            // x1<= A[i] <=x2
            if (x1 <= A[i] && A[i] <= x2) {
                i++;
            }
            //case 2
            //A[i] < x1
            if (A[i] < x1) {
                swap(A, l, i);
                l++;
                i++;
            }
            //case 3
            //A[j]> x2
            //j--
            if (A[j] > x2) {
                j--;
            }

            //case 4
            //A[i] >x2 and A[j]<x1
            // 3 way swap
            if (A[i] > x2 && A[j] < x1) {
                swap(A, i, j);
                j--;
                swap(A, i, l);
                l++;
                i++;
            }
            //case 5
            //A[i]>x2 and x1<=A[j]<=x2
            //swap i and j
            if (A[i] > x2 && A[j] <= x2 && A[j] >= x1) {
                swap(A, i, j);
                i++;
                j--;
            }
        }
        swap(A, start, l - 1);
        swap(A, end, j + 1);
        int flag = 0;
        if (x1 == x2) flag = 1;
        int[] portions = {l - 1, i, flag};
        return portions;
    }
    public static int partition1(int[] A, int start, int end) {
        int q = getRandom(start, end) + start;
        swap(A, q, end);
        int pivot = A[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (A[j] <= pivot) {
                i++;
                swap(A, i, j);
            }
        }
        swap(A, i + 1, end);
        ;
        return i + 1;
    }
    public static int partition(int[] A, int start, int end) {
        int pivot = getRandom(start, end) + start;
        int i = start;
        int j = end;
        while (true) {
            while (A[i] < A[pivot]) {
                i++;
            }
            while (A[j] > A[pivot]) {
                j--;
            }
            if (i >= j) {
                return j;
            } else {
                swap(A, i, j);
            }

        }
    }

    public static int getRandom(int start, int end) {
        Random random = new Random();
        return random.nextInt(end - start + 1);

    }
}

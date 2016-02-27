package sp3;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by txb140830 on 2/23/2016.
 */
//b. Multipivot Quicksort
public class MultipivotQuicksort {

    /**
     * quick sort using dual pivot
     *
     * @param A
     * @param start
     * @param end
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void dualPivotSort(T[] A, int start, int end) {
        int[] portion = dualPivotPartition(A, start, end);
        if (portion != null) {
            dualPivotSort(A, start, portion[0] - 1);
            if (portion[2] != 1) dualPivotSort(A, portion[0] + 1, portion[1]);
            dualPivotSort(A, portion[1], end);
        }
    }

    /**
     * traditional quick sort
     *
     * @param A
     * @param start
     * @param end
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void quickSort(T[] A, int start, int end) {
        if (start < end) {
            int q = partition1(A, start, end);
            quickSort(A, start, q - 1);
            quickSort(A, q + 1, end);
        }
    }

    /**
     * swap position of the variables
     *
     * @param A
     * @param a
     * @param b
     * @param <T>
     */
    public static <T> void swap(T[] A, int a, int b) {
        T temp = A[a];
        A[a] = A[b];
        A[b] = temp;
    }

    /**
     * partition algorithm for dual pivot quick sort
     *
     * @param A
     * @param start
     * @param end
     * @param <T>
     * @return array of indices for next iteration
     */
    public static <T extends Comparable<? super T>> int[] dualPivotPartition(T[] A, int start, int end) {
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
        if (A[start].compareTo(A[end]) > 0) {
            swap(A, start, end);
        }
        T x1 = A[start];
        T x2 = A[end];
        int i = start + 1;
        int l = start + 1;
        int j = end - 1;
        while (i <= j) {
            //case 1
            // x1<= A[i] <=x2
            if (x1.compareTo(A[i]) <= 0 && A[i].compareTo(x2) <= 0) {
                i++;
            }
            //case 2
            //A[i] < x1
            if (A[i].compareTo(x1) < 0) {
                swap(A, l, i);
                l++;
                i++;
            }
            //case 3
            //A[j]> x2
            //j--
            if (A[j].compareTo(x2) > 0) {
                j--;
            }
            //case 4
            //A[i] >x2 and A[j]<x1
            // 3 way swap
            if (A[i].compareTo(x2) > 0 && A[j].compareTo(x1) < 0) {
                swap(A, i, j);
                j--;
                swap(A, i, l);
                l++;
                i++;
            }
            //case 5
            //A[i]>x2 and x1<=A[j]<=x2
            //swap i and j
            if (A[i].compareTo(x2) > 0 && A[j].compareTo(x2) <= 0 && A[j].compareTo(x1) >= 0) {
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

    /**
     * partition 1 algorithm for traditional quick sort
     *
     * @param A
     * @param start
     * @param end
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> int partition1(T[] A, int start, int end) {
        int q = getRandom(start, end) + start;
        swap(A, q, end);
        T pivot = A[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (A[j].compareTo(pivot) <= 0) {
                i++;
                swap(A, i, j);
            }
        }
        swap(A, i + 1, end);
        return i + 1;
    }

    /**
     * partition algorithm for traditional quick sort
     *
     * @param A
     * @param start
     * @param end
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> int partition(T[] A, int start, int end) {
        int pivot = getRandom(start, end) + start;
        int i = start;
        int j = end;
        while (true) {
            while (A[i].compareTo(A[pivot]) < 0) {
                i++;
            }
            while (A[j].compareTo(A[pivot]) > 0) {
                j--;
            }
            if (i >= j) {
                return j;
            } else {
                swap(A, i, j);
            }

        }
    }

    /**
     * generates random number for each iteration of quick sort
     *
     * @param start
     * @param end
     * @return
     */
    public static int getRandom(int start, int end) {
        Random random = new Random();
        return random.nextInt(end - start + 1);

    }
}

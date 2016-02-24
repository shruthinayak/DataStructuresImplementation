package sp3;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by tejas on 2/23/2016.
 */
//b. Multipivot Quicksort
public class MultipivotQuicksort {

    public static void main(String args[]) {

        int[] A = {1, 5, 2, 2, 4, 9, 2, 2, 7, 2};
        quickSort(A, 0, A.length - 1);
        System.out.println(Arrays.toString(A));
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
        int pivot = getRandom(start, end);
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

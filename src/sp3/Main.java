package sp3;

import common.Timer;

import java.util.Arrays;

/**
 * Created by txb140830 on 2/23/2016.
 */
public class Main {
    public static void main(String args []){
        int p=999953;
//        a. Fibonacci numbers computing f_n % p
        long n = 883;
        Timer time = new Timer();

        System.out.println(Fibonacci.linearFibonacci(883728292, p));

        System.out.println(Fibonacci.logFibonacci(883728292, p));

//        b. Multipivot Quicksort
        Integer[] A = new Integer[3000000];
        for (int i = 0; i < A.length; i++) {
            A[i] = MultipivotQuicksort.getRandom(0, 500);
        }
        Integer[] B = Arrays.copyOf(A, A.length);

//        Integer[] A = {1, 5, 2, 2, 4, 9, 2, 2, 7, 2, 4, 4, 6, 1, 0, -10};
//        MultipivotQuicksort.quickSort(A, 0, A.length - 1);
//        System.out.println(Arrays.toString(A));

//        MultipivotQuicksort.dualPivotSort(B, 0, A.length - 1);
//        System.out.println(Arrays.toString(B));

    }


}

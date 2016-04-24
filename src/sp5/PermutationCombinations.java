package sp5;

/**
 * G21:
 * Shruthi Ramesh, Nayak: sxn145130
 * Tejasvee Bolisetty: txb140830
 */

public class PermutationCombinations {
    static int c[];
    static int count = 0;

    public static void main(String args[]) {
        int n = 11;
        int k = 2;
        int[] B = new int[n + 1];
        count = 0;
        for (int i = 1; i < n + 1; i++) {
            B[i] = i;
        }
        int[] A = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        combination(B, n, k); // find combinations of nCk

        Timer time = new Timer();
        permuteHeap(A, A.length);
        time.end();
        System.out.println("number of permutations " + count);
        System.out.println("Time taken to permute using Heap's algo " + (time.endTime - time.startTime) + "ms");

        time = new Timer();
        count = 0;
        permute2(B, B.length - 1); // find permutation using take 2
        System.out.println("number of permutations " + count);
        time.end();
        System.out.println("Time taken to permute using take2 " + (time.endTime - time.startTime) + "ms");
        time = new Timer();
        KnuthsPermute(B); // find permutation of length n using knuths algorithm
        time.end();
        System.out.println("Time taken to permute using knuths algo " + (time.endTime - time.startTime) + "ms");

        System.out.println("Generating permutations in lexicographic order");
        B = new int[]{0, 1, 2, 2, 3};
        KnuthsPermute(B);

    }

    /**
     * Finds all the permutations of Array A of size n using Heap's Algorithm
     *
     * @param A
     * @param n
     */
    public static void permuteHeap(int[] A, int n) {
        if (n == 1) {
            count++;
            //System.out.println(Arrays.toString(A));
        } else {
            for (int i = 0; i < n - 1; i++) {
                permuteHeap(A, n - 1);
                if (n % 2 == 0) {
                    swap(A, i, n - 1);
                } else {
                    swap(A, 0, n - 1);
                }
            }
            permuteHeap(A, n - 1);
        }

    }

    /**
     * Prints elements of array A except element at 0 index
     *
     * @param A
     */
    public static void print(int[] A) {
        for (int i = 1; i < A.length; i++) {
            System.out.print(A[i]);
        }
        System.out.println();
    }

    /**
     * finds Permutation of array A with length i using two swaps
     *
     * @param A
     * @param i
     */
    public static void permute2(int[] A, int i) {
        if (i == 0) {
            count++;
            //print(A);
        } else {
            for (int j = 1; j <= i; j++) {
                swap(A, j, i);
                permute2(A, i - 1);
                swap(A, i, j);
            }
        }
    }

    /**
     * swaps the index i with index j in array A
     *
     * @param A
     * @param i
     * @param j
     */
    public static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    /**
     * finds permutation using take 1 algorithm
     *
     * @param A
     * @param i
     */
    private static void permute1(int[] A, int i) {
        if (i == 0) {
            visit(A, false);
        } else {
            for (int k = 0; k < A.length; k++) {
                if (A[k] == 0) {
                    A[k] = i;
                    permute1(A, i - 1);
                    A[k] = 0;
                }
            }
        }
    }

    /**
     * Finds permutation of array A in lexicographic order using Knuths algorithm
     *
     * @param A
     */
    public static void KnuthsPermute(int[] A) {
        int count = 1;
        while (true) {
            print(A);
            int j = A.length - 2;
            while (A[j] >= A[j + 1]) {
                j--;
            }
            if (j == 0) {
                System.out.println("number of permutations " + count);
                break;
            }
            //find max value of l such that a[j]<a[l]
            int l = A.length - 1;
            while (A[l] <= A[j]) {
                l--;
            }
            swap(A, j, l);
            //reverse the array from a[j+1] to a[n]
            reverse(A, j + 1, A.length - 1);
            count++;
        }
    }

    /**
     * Reverses the array A from start index to end index in A
     *
     * @param A
     * @param start
     * @param end
     */
    public static void reverse(int[] A, int start, int end) {
        while (start < end) {
            int temp = A[start];
            A[start] = A[end];
            A[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * Finds all possible Combinations of length k from array A length n i.e. nCk
     *
     * @param A
     * @param n
     * @param k
     */
    public static void combination(int[] A, int n, int k) {
        if (k > n) {
            return;
        } else if (k == 0) {
            visit(A, true);
        } else {
            //decide to choose A[n]
            //case 1: n is not selected
            combination(A, n - 1, k);
            //case 2: n is selected
            A[n] = 1;
            combination(A, n - 1, k - 1);
            A[n] = 0;
        }
    }

    /**
     * Prints the one possible combination
     *
     * @param A
     * @param combination
     */
    public static void visit(int[] A, boolean combination) {
        int[] d = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            if (A[i] != 0) {
                if (combination) {
                    System.out.print(i);
                } else {
                    d[A[i]] = i + 1;
                }
            }
        }
        for (int i = 0; i < d.length; i++) {
            if (d[i] != 0) {
                System.out.print(d[i]);
            }
        }
        System.out.println();
        count++;
    }
}

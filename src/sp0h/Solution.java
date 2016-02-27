package sp0h;

import java.util.Random;

public class Solution<T> {
    static Solution s = new Solution();

    public static void main(String[] args) {
        s.removingDuplicates();

    }

    public void removingDuplicates() {

        Object[] x;
        boolean object = false;
        if (object) {
            x = new Object[]{new Support(3, 4, "S"),
                    new Support(3, 5, "P"),
                    new Support(3, 4, "S"),
                    new Support(3, 6, "S"),
                    new Support(3, 4, "M"),
                    new Support(6, 4, "N"),
                    new Support(8, 4, "Q"),
                    new Support(8, 4, "Q"),
                    new Support(3, 5, "P")
            };
        } else {
            int len = 1000000;
            x = new Object[len];
            Random rand = new Random();
            int i = 0;
            while (i != len) {
                x[i] = rand.nextInt(10);
                i++;
            }
        }
        long start = System.currentTimeMillis();
        int k = s.findDistinct(x);
        long end = System.currentTimeMillis();
        System.out.println("The number of distinct elements is: " + k);
        System.out.println("To compute distinct elements in an array of length " + x.length + " is " + (end - start) + "ms");
        for (int j = 0; j < k; j++) {
            System.out.println(x[j]);
        }
    }

    public int findDistinct(T[] arr) {
        HashList<T> g = new HashList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            g.insert(arr, arr[i], i);
        }

        return g.k;
    }
}

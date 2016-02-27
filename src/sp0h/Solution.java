package sp0h;

import com.intellij.util.ArrayUtil;

import java.util.Arrays;
import java.util.Random;

public class Solution<T> {
    static Solution s = new Solution();

    public static void main(String[] args) {
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
            //Generating random numbers
            int len = 10000000;
            x = new Object[len];
            Random rand = new Random();
            int i = 0;
            while (i != len) {
                x[i] = rand.nextInt(10);
                i++;
            }
        }
        s.removingDuplicates(x);
        s.mostFrequent(x);


    }

    public void removingDuplicates(T[] x) {
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

    public int mostFrequent(T[] arr) {
        mostFreqWithSort(arr);
        return mostFreqWithHashMap(arr);
    }

    private int mostFreqWithHashMap(T[] arr) {
        long start;
        int max;
        long end;

        Integer[] x = ArrayUtil.toObjectArray(Integer.class, arr);
        start = System.currentTimeMillis();
        HashList<Integer> g = new HashList<>(arr.length);
        max = -1;
        int e = 0;
        for (int i = 0; i < arr.length; i++) {
            g.insert(x, x[i], i);
            if (g.elementFreq(x[i]) > max) {
                max = g.elementFreq(x[i]);
                e = x[i];
            }
        }
        end = System.currentTimeMillis();
        System.out.println("HashMap impl  - " + (end - start) + "ms");
        System.out.println(e + ":" + max);
        return max;
    }

    private void mostFreqWithSort(T[] arr) {
        T[] cpy = arr.clone();
        long start = System.currentTimeMillis();

        Arrays.sort(cpy);

        T previous = cpy[0];
        T popular = cpy[0];
        int count = 1;
        int maxCount = 1;

        for (int i = 1; i < cpy.length; i++) {
            if (cpy[i].equals(previous))
                count++;
            else {
                if (count > maxCount) {
                    popular = cpy[i - 1];
                    maxCount = count;
                }
                previous = cpy[i];
                count = 1;
            }
        }

        popular = count > maxCount ? cpy[cpy.length - 1] : popular;
        maxCount = count > maxCount ? count : maxCount;
        long end = System.currentTimeMillis();
        System.out.println("Arrays.sort()  - " + (end - start) + "ms");
        System.out.println(popular + ":" + maxCount);
    }
}

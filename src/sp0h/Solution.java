package sp0h;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;

/**
 * G21
 *
 * @param <K> Key type
 * @param <V> Value type
 * @author Shruthi(sxn145130) Tejasvee(txb140830)
 */
public class Solution<K, V> {
    static Solution s = new Solution();


    public static void main(String[] args) {
        Integer[] x;

        //Generating random numbers
        int len = 1000000;
        x = new Integer[len];
        Random rand = new Random();
        int i = 0;
        while (i != len) {
            x[i] = rand.nextInt(10);
            i++;
        }

        System.out.println("Generated random number array");
        s.removingDuplicates(x);
        System.out.println("For " + len + " elements:");
        s.mostFrequent(x);


    }


    public void removingDuplicates(K[] x) {
        long start = System.currentTimeMillis();
        int k = s.findDistinct(x);
        long end = System.currentTimeMillis();
        System.out.println("The number of distinct elements is: " + k);
        System.out.println("To compute distinct elements in an array of length " + x.length + " is " + (end - start) + "ms");
        /*for (int j = 0; j < k; j++) {
            System.out.println(x[j]);
        }*/
    }

    /**
     *
     * @param arr An array of objects
     * @return an integer representing the number of unique elements in the array.
     */
    public int findDistinct(K[] arr) {
        HashMapIfc<K, Integer> g = new SeparateChaining<>();
//        HashMapIfc<K, Integer> g = new TwoChoice<>();
//        HashMapIfc<K, Integer> g = new CuckooHashing<>();

        int distinct = 0;
        for (int i = 0; i < arr.length; i++) {
            if (!g.hasKey(arr[i])) {
                g.put(arr[i], 1);
                swap(arr, distinct, i);
                distinct++;
            } else {
                Integer count = g.get(arr[i]);
                g.put(arr[i], ++count);
            }
        }
        return distinct;
    }

    private void swap(K[] a, int i, int index) {
        K x = a[i];
        a[i] = a[index];
        a[index] = x;
    }

    public int mostFrequent(Integer[] arr) {
        mostFreqWithSort(arr);
        return mostFreqWithHashMap(arr);
    }

    /**
     * @param arr Integer array
     * @return The most frequent element in the array.
     */
    private int mostFreqWithHashMap(Integer[] arr) {
        long start = System.currentTimeMillis();
        HashMapIfc<Integer, Integer> count = new SeparateChaining<>();
//        HashMapIfc<Integer, Integer> count = new TwoChoice<>();
//        HashMapIfc<Integer, Integer> count = new CuckooHashing<>();
        for (Integer i : arr) {
            if (!count.hasKey(i)) {
                count.put(i, 1);
            } else {
                int c = count.get(i);
                count.put(i, ++c);
            }
        }
        Set<Integer> keySet = count.keySet();
        int max = Integer.MIN_VALUE;
        Integer ele = null;
        for (Integer key : keySet) {
            if (max < count.get(key)) {
                max = count.get(key);
                ele = key;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("HashMap Impl - " + (end - start) + "ms");
        System.out.println(ele + ":" + max);
        return ele;
    }

    private void mostFreqWithSort(Integer[] arr) {
        Integer[] cpy = arr.clone();
        long start = System.currentTimeMillis();

        Arrays.sort(cpy);

        Integer previous = cpy[0];
        Integer popular = cpy[0];
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

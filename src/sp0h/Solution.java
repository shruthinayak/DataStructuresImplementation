package sp0h;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Solution<K, V> {
    static Solution s = new Solution();
    HashMapIfc<K, Integer> g = new TwoChoice<>();

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
            int len = 9000000;
            x = new Object[len];
            Random rand = new Random();
            int i = 0;
            while (i != len) {
                x[i] = rand.nextInt(10);
                i++;
            }
            System.out.println("For " + len + " elements:");
            s.mostFrequent(x);
        }
        System.out.println("Generated random number array");
        s.removingDuplicates(x);


    }

    public void removingDuplicates(K[] x) {
        long start = System.currentTimeMillis();
        int k = s.findDistinct(x);
        long end = System.currentTimeMillis();
        System.out.println("The number of distinct elements is: " + k);
        System.out.println("To compute distinct elements in an array of length " + x.length + " is " + (end - start) + "ms");
       /* for (int j = 0; j < k; j++) {
            System.out.println(x[j]);
        }*/
    }

    public int findDistinct(K[] arr) {

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

    public int mostFrequent(K[] arr) {
        mostFreqWithSort(arr);
        return mostFreqWithHashMap(arr);
    }

    private int mostFreqWithHashMap(K[] arr) {
        long start = System.currentTimeMillis();
        HashMapIfc<K, Integer> count = new TwoChoice<>();
        for (K i : arr) {
            if (!count.hasKey(i)) {
                count.put(i, 1);
            } else {
                int c = count.get(i);
                count.put(i, ++c);
            }
        }
        List<K> keySet = count.keySet();
        int max = Integer.MIN_VALUE;
        K ele = null;
        for (K key : keySet) {
            if (max < count.get(key)) {
                max = count.get(key);
                ele = key;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("HashMap Impl - " + (end - start) + "ms");
        System.out.println(ele + ":" + max);
        return max;
    }

    private void mostFreqWithSort(K[] arr) {
        K[] cpy = arr.clone();
        long start = System.currentTimeMillis();

        Arrays.sort(cpy);

        K previous = cpy[0];
        K popular = cpy[0];
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

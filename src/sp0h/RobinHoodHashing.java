package sp0h;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Group 21
 * Authors:
 * Shruthi Ramesh Nayak - sxn145130
 * Tejasvee Bolisetty - txb140830
 */

public class RobinHoodHashing<K, V> implements HashMapIfc<K, V> {
    float loadFactor = 0.75f;
    K[] keys;
    V[] values;
    int probe[];
    int tableSize = 2;
    int maxProbe;
    double k;
    Set<K> keySet = new HashSet<>();

    RobinHoodHashing() {
        init(tableSize);
    }

    RobinHoodHashing(int tableSize) {
        this.tableSize = tableSize;
        init(tableSize);
    }

    protected void init(int tableSize) {
        this.k = 0;
        this.keys = (K[]) new Object[tableSize];
        this.values = (V[]) new Object[tableSize];
        this.probe = new int[tableSize];
        this.maxProbe = 0;
        for (int i = 0; i < tableSize; i++) {
            this.keys[i] = null;
            this.values[i] = null;
            probe[i] = -2; // -2 for empty -1 for deleted
        }

    }

    @Override
    public void put(K key, V value) {
        if ((k / tableSize) > loadFactor) {
            resize();
        }
        int index = hashFunction(key);
        int i = 0;
        int tableIndex = getIndex(key, index);
        if (tableIndex != -1) {
            while (i < tableSize) {
                if (keys[index + i] == null) { // if location is free for that index
                    values[index + i] = value;
                    probe[index + i] = i;
                } else { // if it is occupied with other element, compare probe length with existing key
                    int probeExist = probe[index + i];
                    if (i > probeExist) {
                        K existKey = keys[index + i];
                        V existVal = values[index + i];
                        keys[index + i] = key;
                        values[index + i] = value;
                        probe[index + i] = i;
                        if (i > maxProbe) {
                            maxProbe = i;
                        }
                        reinsert(existKey, existVal, probeExist);
                    } else {
                        i++;
                    }
                }
            }
        } else {
            values[index] = value;
        }

    }

    private void reinsert(K existKey, V existVal, int probeExist) {
        //reinsert the value in to table
        put(existKey, existVal);


    }

    @Override
    public V get(K key) {
        int index = hashFunction(key);
        index = getIndex(key, index);
        if (index != -1) {
            return values[index];
        }
        return null;
    }

    /**
     * finds whether the element is already present or not.
     *
     * @param key
     * @param index
     * @return If present in hash table, it returns its index, else -1
     */
    public int getIndex(K key, int index) {
        int i = 0;
        while (i <= maxProbe && probe[index + i] != -2) { // compares with probe whether it has to move forward to check with other position if the position was deleted
            if (keys[index + i] != null && keys[index + 1].equals(key)) {
                return index + i;
            }
            i++;
        }
        return -1;

    }

    @Override
    public void delete(K key) {
        int hashcode = hashFunction(key);
        int index = getIndex(key, hashcode);
        if (index != -1) {
            keys[index] = null;
            probe[index] = -1; // for marking the index as deleted
            values[index] = null;
        }
    }

    @Override
    public int hashFunction(K key) {
        int hc = key.hashCode();
        return hc % tableSize;
    }

    @Override
    public boolean hasKey(K key) {
        return keySet.contains(key);
    }

    @Override
    public Set keySet() {
        return null;
    }

    public void resize() {
        RobinHoodHashing<K, V> newTable = new RobinHoodHashing<>(tableSize * 2);
        Set<K> keyS = keySet();
        for (K key : keyS) {
            newTable.put(key, get(key));
        }
        tableSize = tableSize * 2;
        keys = newTable.keys;
        values = newTable.values;
        keySet = keySet();

    }
}

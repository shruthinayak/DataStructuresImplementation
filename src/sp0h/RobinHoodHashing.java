package sp0h;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * G21
 *
 * @param <K> Key type
 * @param <V> Value type
 * @author Shruthi(sxn145130) Tejasvee(txb140830)
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
            values[tableIndex] = value;
        } else {
            while (i < tableSize) {
                int keyIndex = (index + i) % tableSize;
                if (keys[keyIndex] == null) { // if location is free for that index
                    values[keyIndex] = value;
                    probe[keyIndex] = i;
                    keys[keyIndex] = key;
                    keySet.add(key);
                    k++;
                    return;
                } else { // if it is occupied with other element, compare probe length with existing key
                    int probeExist = probe[keyIndex];
                    if (i > probeExist) {
                        K existKey = keys[keyIndex];
                        V existVal = values[keyIndex];
                        keys[keyIndex] = key;
                        values[keyIndex] = value;
                        probe[keyIndex] = i;
                        keySet.add(key);
                        k++;
                        if (i > maxProbe) {
                            maxProbe = i;
                        }
                        reinsert(existKey, existVal, probeExist);
                        return;
                    } else {
                        i++;
                    }
                }
            }
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
        int keyIndex = (index + i) % tableSize;
        while (i <= maxProbe && probe[keyIndex] != -2) { // compares with probe whether it has to move forward to check with other position if the position was deleted
            if (keys[keyIndex] != null && keys[keyIndex].equals(key)) {
                return keyIndex;
            }
            i++;
            keyIndex = (index + i) % tableSize;
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
    public Set<K> keySet() {
        for (K key : keys) {
            if (key != null)
                keySet.add(key);
        }
        return keySet;
    }
//    public Set keySet() {
//        return null;
//    }

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
        this.probe = newTable.probe;

    }
}

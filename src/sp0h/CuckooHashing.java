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
public class CuckooHashing<K, V> implements HashMapIfc<K, V>/*extends SeparateChaining<K, V>*/ {
    final int retryLimit = 10;
    float loadFactor = 0.75f;
    K[] keys;
    V[] values;
    int tableSize = 2;
    double k;
    Set<K> keySet = new HashSet<>();
    List<K> overflowKey = new ArrayList<>();
    List<V> overflowValue = new ArrayList<>();

    CuckooHashing() {
        init(tableSize);
    }

    CuckooHashing(int tableSize) {
        this.tableSize = tableSize;
        init(tableSize);
    }

    protected void init(int tableSize) {
        this.k = 0;
        this.keys = (K[]) new Object[tableSize];
        this.values = (V[]) new Object[tableSize];
        for (int i = 0; i < tableSize; i++) {
            this.keys[i] = null;
            this.values[i] = null;
        }
    }

    @Override
    public void put(K key, V value) {
        if ((k / tableSize) > loadFactor) {
            resize();
        }
        int index = getIndex(key);
        if (index != -1) {
            values[index] = value;
            return;
        }

        index = getVacantSpot(key);
        K y = keys[index] != null ? keys[index] : null;
        keys[index] = key;
        V v = values[index];
        values[index] = value;
        k++;
        keySet.add(key);
        if (y != null)
            reinsert(y, 1, 1, v);
    }

    public void reinsert(K y, int i, int retry, V v) {
        int index = i == 1 ? hashFunction(y) : hashFunction2(y);
        if (keys[index] == null) {
            keys[index] = y;
            values[index] = v;
            k++;
            return;
        } else if ((retry + 1) < retryLimit) {
            int j = ++i > 2 ? 1 : i; //next function
            K z = keys[index];
            keys[index] = y;
            V zv = values[index];
            values[index] = v;
            reinsert(z, j, retry + 1, zv);
            return;

        } else {

            if (overflowKey.contains(y))
                overflowValue.set(overflowKey.indexOf(y), v);
            else {
                overflowKey.add(y);
                overflowValue.add(v);
                keySet.add(y);
            }

            return;
        }

    }

    public void resize() {
        CuckooHashing<K, V> newTable = new CuckooHashing<>(tableSize * 2);
        Set<K> keyS = keySet();
        for (K key : keyS) {
            newTable.put(key, get(key));
        }
        tableSize = tableSize * 2;
        keys = newTable.keys;
        values = newTable.values;
        keySet = keySet();

    }

    @Override
    public V get(K key) {

        int index = getIndex(key);
        if (index != -1) {
            return values[index];
        } else if (overflowKey.contains(key))
            return overflowValue.get(overflowKey.indexOf(key));
        return null;

    }

    public int getIndex(K key) {
        int hc1 = hashFunction(key);
        int hc2 = hashFunction2(key);

        if (keys[hc1] != null && keys[hc1].equals(key))
            return hc1;
        else if (keys[hc2] != null && keys[hc2].equals(key))
            return hc2;
        else return -1;
    }

    public int getVacantSpot(K key) {
        int hc1 = hashFunction(key);
        int hc2 = hashFunction2(key);
        return keys[hc1] == null ? hc1 : hc2;
    }

    @Override
    public void delete(K key) {
        int index = getIndex(key);
        if (index != -1) {
            keys[index] = null;
            values[index] = null;
        }
    }

    @Override
    public int hashFunction(K key) {
        int hc = key.hashCode();
        return hc % tableSize;
    }

    public int hashFunction2(K key) {
        int hc = key.hashCode() + 31;
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

}

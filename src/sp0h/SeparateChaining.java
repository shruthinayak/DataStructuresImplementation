package sp0h;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by shruthi on 26/2/16.
 */


public class SeparateChaining<K, V> implements HashMapIfc<K, V> {
    float loadFactor = 0.75f;
    int tableSize = 10;
    double k;
    LinkedList<K>[] keys;
    LinkedList<V>[] values;
    Set<K> keySet;


    SeparateChaining() {
        init(tableSize);
    }

    SeparateChaining(int tableSize) {
        this.tableSize = tableSize;
        init(tableSize);
    }

    protected void init(int tableSize) {
        this.k = 0;
        this.keys = new LinkedList[tableSize];
        this.values = new LinkedList[tableSize];
        for (int i = 0; i < tableSize; i++) {
            this.keys[i] = new LinkedList<K>();
            this.values[i] = new LinkedList<V>();
        }
        keySet = keySet();
    }

    protected void resize() {
        SeparateChaining<K, V> newTable = new SeparateChaining<>(tableSize * 2);
        Set<K> keyS = keySet();
        for (K key : keyS) {
            newTable.put(key, get(key));
        }
        tableSize = tableSize * 2;
        keys = newTable.keys;
        values = newTable.values;
        keySet = keySet();
    }

    void clear() {
        init(tableSize);
    }

    public int hashFunction(K obj) {
        int key = obj.hashCode();
        return key % tableSize;
    }

    /**
     * Put key and value in the hashtable.
     * Replace key with the new value if value already present.
     *
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        int index = hashFunction(key);
        insert(key, value, index);
    }

    protected void insert(K key, V value, int index) {

        if (!find(key)) {
            if (isResizeNecessary()) {
                resize();
            }
            keys[index].add(key);
            values[index].add(keys[index].size() - 1, value);
            k++;
            keySet.add(key);
        } else {
            int indexList = keys[index].indexOf(key);
            values[index].set(indexList, value);
        }
    }

    @Override
    public V get(K key) {
        if (find(key)) {
            int index = hashFunction(key);
            int indexInList = keys[index].indexOf(key);
            return values[index].get(indexInList);
        } else return null;

    }

    @Override
    public void delete(K key) {
        int i = keys[hashFunction(key)].indexOf(key);
        keys[i] = null;
        values[hashFunction(key)].set(i, null);
    }

    @Override
    public Set<K> keySet() {
        if (keySet == null) {
            keySet = new HashSet<>();
            for (LinkedList<K> keyList : keys) {
                keySet.addAll(keyList);
            }
        }
        return keySet;
    }

    boolean find(K obj) {
        int i = hashFunction(obj);
        return keys[i] != null && keys[i].contains(obj);
    }


    public boolean hasKey(K obj) {
        return find(obj);
    }

    public boolean isResizeNecessary() {
        double v = k / tableSize;
        return v > loadFactor;
    }

}

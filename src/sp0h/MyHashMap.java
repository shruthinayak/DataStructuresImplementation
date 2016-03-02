package sp0h;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shruthi on 26/2/16.
 */


public class MyHashMap<K, V> {
    float loadFactor = 0.75f;
    int tableSize = 5;
    double k;
    LinkedList<K>[] keys;
    LinkedList<V>[] values;
    List<K> keySet;


    MyHashMap() {
        init(tableSize);
    }

    MyHashMap(int tableSize) {
        this.tableSize = tableSize;
        init(tableSize);
    }

    private void init(int tableSize) {
        this.k = 0;
        this.keys = new LinkedList[tableSize];
        this.values = new LinkedList[tableSize];
        for (int i = 0; i < tableSize; i++) {
            this.keys[i] = new LinkedList<K>();
            this.values[i] = new LinkedList<V>();
        }
        keySet = null;
    }

    private void resize() {
        MyHashMap<K, V> newTable = new MyHashMap<>(tableSize * 2);
        List<K> keyS = keySet();
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

    int hashFunction(K obj) {
        int key = obj.hashCode();
        int i = key % tableSize;
        return i;
    }

    /**
     * Put key and value in the hashtable.
     * Replace key with the new value if value already present.
     *
     * @param key
     * @param value
     */
    void put(K key, V value) {
        if (isResizeNecessary()) {
            resize();
        }
        int index = hashFunction(key);
        if (!find(key)) {
            keys[index].add(key);
            values[index].add(keys[index].size() - 1, value);
            k++;
        } else {
            int indexList = keys[index].indexOf(key);
            values[index].set(indexList, value);
        }
    }

    V get(K key) {
        if (find(key)) {
            int index = hashFunction(key);
            int indexInList = keys[index].indexOf(key);
            return values[index].get(indexInList);
        } else return null;

    }

    List<K> keySet() {
        keySet = new ArrayList<>();
        for (LinkedList<K> keyList : keys) {
            keySet.addAll(keyList);
        }
        return keySet;
    }


    boolean find(K obj) {
        int i = hashFunction(obj);
        return keys[i] != null && keys[i].contains(obj);
    }

    void delete(K[] a, K obj) {
        if (find(obj)) {
            a[hashFunction(obj)] = null;
        }
    }

    boolean hasKey(K obj) {
        return find(obj);
    }

    public boolean isResizeNecessary() {
        double v = k / tableSize;
        return v > loadFactor;
    }

}

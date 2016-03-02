package sp0h;

import java.util.Set;

/**
 * Created by shruthi on 1/3/16.
 */
public class TwoChoice<K, V> extends SeparateChaining<K, V> implements HashMapIfc<K, V> {


    public TwoChoice(int tableSize) {
        this.tableSize = tableSize;
        init(tableSize);
    }

    TwoChoice() {
        init(tableSize);
    }

    @Override
    protected void resize() {
        TwoChoice<K, V> newTable = new TwoChoice<>(tableSize * 2);
        Set<K> keyS = keySet();
        for (K key : keyS) {
            newTable.put(key, get(key));
        }
        tableSize = tableSize * 2;
        keys = newTable.keys;
        values = newTable.values;
        keySet = newTable.keySet();
    }

    @Override
    public void put(K key, V value) {
        int index;
        if (!find(key)) {
            int hc1 = hashFunction(key);
            int hc2 = hashFunction2(key);
            index = keys[hc1].size() < keys[hc2].size() ? hc1 : hc2;
        } else {
            index = getIndex(key);
        }
        insert(key, value, index);
    }

    @Override
    public V get(K key) {
        int index = getIndex(key);
        if (index != -1) {
            int indexInList = keys[index].indexOf(key);
            return values[index].get(indexInList);
        } else return null;

    }

    @Override
    public void delete(K key) {
        int index = getIndex(key);
        if (index != -1) {
            int indexInList = keys[index].indexOf(key);
            values[index].set(indexInList, null);
        }
    }

    public boolean find(K key) {
        int hc1 = hashFunction(key);
        int hc2 = hashFunction2(key);
        return (keys[hc1] != null && keys[hc1].contains(key)) || (keys[hc2] != null && keys[hc2].contains(key));
    }

    public int getIndex(K key) {
        int hc1 = hashFunction(key);
        int hc2 = hashFunction2(key);

        if (keys[hc1] != null && keys[hc1].contains(key))
            return hc1;
        else if (keys[hc2] != null && keys[hc2].contains(key))
            return hc2;
        else return -1;
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


}

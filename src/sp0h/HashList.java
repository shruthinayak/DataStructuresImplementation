package sp0h;

/**
 * Created by shruthi on 26/2/16.
 */
public class HashList<T> {
    int tableSize;
    int k;
    T[] hashTable;

    HashList(int tableSize) {
        this.tableSize = tableSize;
        k = 0;
        hashTable = (T[]) new Object[tableSize];
        for (int i = 0; i < tableSize; i++) {
            hashTable[i] = null;
        }
    }

    int hashFunction(T obj) {
        int key = obj.hashCode();
        int i = key % tableSize;
        return i;
    }

    void insert(T[] a, T obj, int i) {
        if (!find(obj)) {
            hashTable[hashFunction(obj)] = obj;
            swap(a, k, i);
            k++;
        }

    }

    private void swap(T[] a, int i, int index) {
        T x = a[i];
        a[i] = a[index];
        a[index] = x;
    }

    boolean find(T obj) {
        if (hashTable[hashFunction(obj)] != null)
            return hashTable[hashFunction(obj)].equals(obj);
        return false;
    }

    void delete(T[] a, T obj) {
        if (find(obj)) {
            a[hashFunction(obj)] = null;
        }
    }

}

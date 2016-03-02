package sp0h;

import java.util.List;

/**
 * Created by shruthi on 1/3/16.
 */
public interface HashMapIfc<K, V> {

    void put(K key, V value);

    V get(K key);

    void delete(K key);

    int hashFunction(K key);

    boolean hasKey(K key);

    List<K> keySet();
}

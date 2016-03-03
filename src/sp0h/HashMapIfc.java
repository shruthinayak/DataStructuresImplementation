package sp0h;

import java.util.Set;

/**
 * G21
 *
 * @param <K> Key type
 * @param <V> Value type
 * @author Shruthi(sxn145130) Tejasvee(txb140830)
 */
public interface HashMapIfc<K, V> {

    void put(K key, V value);

    V get(K key);

    void delete(K key);

    int hashFunction(K key);

    boolean hasKey(K key);

    Set<K> keySet();
}

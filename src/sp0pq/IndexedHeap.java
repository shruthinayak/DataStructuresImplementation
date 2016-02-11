package sp0pq;// Ver 1.0:  Wec, Feb 3.  Initial description.

import java.util.Comparator;

public class IndexedHeap<T, I extends Index<T>> extends BinaryHeap<T> {
    I index;

    /**
     * Build a priority queue with a given array q
     */
    IndexedHeap(T[] q, Comparator<T> comp, I index) {
        super(q, comp);
        this.index = index;
    }

    /**
     * Create an empty priority queue of given maximum size
     */
    IndexedHeap(int n, Comparator<T> comp, I index) {
        super(n, comp);
        this.index = index;
    }

    /**
     * restore heap order property after the priority of x has decreased
     */
    void decreaseKey(T x) {
        percolateUp(index.getIndex(x));
    }

    @Override
    protected void swap(int i, int j) {
        T temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
        index.putIndex(pq[i], i);
        index.putIndex(pq[j], j);
    }
}

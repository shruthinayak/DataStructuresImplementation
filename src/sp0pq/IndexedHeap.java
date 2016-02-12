package sp0pq;// Ver 1.0:  Wec, Feb 3.  Initial description.

import java.util.Comparator;
import java.util.List;

public class IndexedHeap<T extends Index<T>> extends BinaryHeap<T> {


    /**
     * Build a priority queue with a given array q
     */
    public IndexedHeap(List<T> q, Comparator<T> comp) {
        this((T[]) q.toArray(new Object[q.size()]), comp);
    }

    public IndexedHeap(T[] q, Comparator<T> comp) {
        super(q, comp);
        int j = 0;
        for (T i : q) {
            i.putIndex(j++);
        }
    }


    /**
     * Create an empty priority queue of given maximum size
     */
    IndexedHeap(int n, Comparator<T> comp) {
        super(n, comp);
    }

    /**
     * restore heap order property after the priority of x has decreased
     */
    void decreaseKey(T x) {
        percolateUp(x.getIndex());
    }

    @Override
    protected void swap(int i, int j) {
        T temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
        pq[i].putIndex(i);
        pq[j].putIndex(j);
    }
}

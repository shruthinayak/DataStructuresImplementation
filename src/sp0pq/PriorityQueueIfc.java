package sp0pq;// Ver 1.0:  Wec, Feb 3.  Initial description.

public interface PriorityQueueIfc<T> {
    void insert(T x);

    T deleteMin();

    T min();

    void add(T x);

    T remove();

    T peek();
}
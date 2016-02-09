package sp0pq;// Ver 1.0:  Wec, Feb 3.  Initial description.

public interface PriorityQueueIfc {
    void insert(int x);

    int deleteMin();

    int min();

    void add(int x);

    int remove();

    int peek();
}
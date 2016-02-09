package sp0pq;// Ver 1.0:  Wec, Feb 3.  Initial description.

import java.util.Comparator;

public class BinaryHeap<T> implements PriorityQueueIfc<T> {
    T[] pq;
    Comparator c;
    int size;

    /**
     * Build a priority queue with a given array q
     */
    BinaryHeap(T[] q, Comparator comp) {
        pq = q;
        c = comp;
        buildHeap(true);
    }

    /**
     * Create an empty priority queue of given maximum size
     */
   /* BinaryHeap(T n, Comparator comp) { *//* to be implemented *//*
    }*/
    public void heapSort(T[] A, Comparator comp) { /* to be implemented */

    }

    public void insert(T x) {
        add(x);
    }

    public T deleteMin() {
        return remove();
    }

    public T min() {
        return peek();
    }

    public void add(T x) { /* to be implemented */
    }

    public T remove() { /* to be implemented */
        return null;
    }

    public T peek() { /* to be implemented */
        return null;
    }

    /**
     * @param i index of the element that needs to be fixed into its position in the heap.
     *          pq[i] may violate heap order with parent
     */
    void percolateUp(int i) { /* to be implemented */
        pq[0] = pq[i]; //first copy the element to be percolated into another space thereby making pq[i] as the hole.
        while (c.compare(pq[i / 2], pq[0]) == -1) { // compare the element to its parent
            pq[i] = pq[i / 2]; //bring the parent down in case if the element is smaller than its parent.
            i = i / 2;
        }
        pq[i] = pq[0];
    }

    /**
     * pq[i] may violate heap order with children
     */
    void percolateDown(int i) { /* to be implemented */

        T x = pq[i];
        while (2 * i < pq.length) {
            T lchild = pq[2 * i];
            T rchild = null;
            if ((2 * i + 1) < pq.length) {
                rchild = pq[2 * i + 1];
            }
            int index;
            if (rchild != null) {
                index = c.compare(lchild, rchild) == 1 ? 2 * i : 2 * i + 1;
            } else {
                index = 2 * i;
            }
            if (c.compare(x, pq[index]) == -1) {
                swap(i, index);
                i = index;
            } else {
                break;
            }

        }
        pq[i] = x;
    }

    private void swap(int i, int j) {
        T temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    /**
     * Create a heap.  Precondition: none.
     */
    void buildHeap(boolean flag) {

        if (flag) {
            for (int i = 1; i < pq.length; i++) {
                size++;
                percolateUp(i);
            }
        } else {
            size = pq.length / 2;
            for (int i = pq.length / 2 - 1; i >= 1; i--) {
                size++;
                percolateDown(i);
            }
        }


    }

    /* sort array A[1..n].  A[0] is not used.
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
     */
    public void printParentWise() {
        for (int i = 1; i <= size; i++) {
            int x = 2 * i;
            boolean flag = x <= size;
            if (flag)
                System.out.println(pq[i] + ":" + pq[x]);
            flag = x + 1 <= size;
            if (flag)
                System.out.println(pq[i] + ":" + pq[x + 1]);

        }
    }
}
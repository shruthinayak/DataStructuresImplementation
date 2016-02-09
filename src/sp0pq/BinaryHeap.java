package sp0pq;// Ver 1.0:  Wec, Feb 3.  Initial description.

import java.util.Comparator;

public class BinaryHeap implements PriorityQueueIfc {
    int[] pq;
    Comparator c;
    int size;

    /**
     * Build a priority queue with a given array q
     */
    BinaryHeap(int[] q, Comparator comp) {
        pq = q;
        c = comp;
        buildHeap();
    }

    /**
     * Create an empty priority queue of given maximum size
     */
    BinaryHeap(int n, Comparator comp) { /* to be implemented */
    }

    public static void heapSort(int[] A, Comparator comp) { /* to be implemented */

    }

    public void insert(int x) {
        add(x);
    }

    public int deleteMin() {
        return remove();
    }

    public int min() {
        return peek();
    }

    public void add(int x) { /* to be implemented */
    }

    public int remove() { /* to be implemented */
        return 0;
    }

    public int peek() { /* to be implemented */
        return 0;
    }

    /**
     * @param i index of the element that needs to be fixed into its position in the heap.
     *          pq[i] may violate heap order with parent
     */
    void percolateUp(int i) { /* to be implemented */
        pq[0] = pq[i]; //first copy the element to be percolated into another space thereby making pq[i] as the hole.
        while (pq[i / 2] > pq[0]) { // compare the element to its parent
            pq[i] = pq[i / 2]; //bring the parent down in case if the element is smaller than its parent.
            i = i / 2;
        }
        pq[i] = pq[0];
    }

    /**
     * pq[i] may violate heap order with children
     */
    void percolateDown(int i) { /* to be implemented */

        int x = pq[i];
        while (2 * i < pq.length) {
            int lchild = pq[2 * i];
            int rchild = (2 * i + 1) < pq.length ? pq[2 * i + 1] : Integer.MAX_VALUE;
            int index = Math.min(lchild, rchild) == lchild ? 2 * i : 2 * i + 1;
            if (x < pq[index]) {
                swap(i, index);
                i = index;
            } else {
                swap(i, pq.length - 1);
                i = pq.length - 1;
            }
        }
        pq[i] = x;
    }

    private void swap(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    /**
     * Create a heap.  Precondition: none.
     */
    void buildHeap() {

        for (int i = 1; i < pq.length; i++) {
            size++;
            percolateUp(size);
        }

    }

    /* sort array A[1..n].  A[0] is not used.
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
     */
    public void printParentWise() {
        for (int i = 1; i < size; i++) {
            int x = 2 * i;
            boolean flag = x < size;
            if (flag)
                System.out.println(i + ":" + pq[x]);
            flag = x + 1 < size;
            if (flag)
                System.out.println(i + ":" + pq[x + 1]);

        }
    }
}
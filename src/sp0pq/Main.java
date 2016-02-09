package sp0pq;

import java.util.Comparator;

/**
 * Created by shruthi on 4/2/16.
 */
public class Main {
    public static void main(String[] args) {
        //int[] q = {0,2,7,9,8,17,12};
        int[] q = {0, 12, 17, 8, 9, 7, 2, 1, 4, 5, 6, 13};
        Comparator comp = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                int a = (int) o1;
                int b = (int) o2;
                if (a > b)
                    return -1;
                else if (a < b)
                    return 1;
                else return 0;
            }
        };
        BinaryHeap heap = new BinaryHeap(q, comp);
        heap.printParentWise();
    }

}

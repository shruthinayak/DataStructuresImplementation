package sp0pq;

import sp2.Graph;
import sp2.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by shruthi on 4/2/16.
 */
public class Main {
    public static void main(String[] args) {
        //int[] q = {0,2,7,9,8,17,12};
//        Integer[] q = {0, 12, 17, 8, 9, 7, 2, 1, 4, 5, 6, 13};
        Integer[] q = {0, 150, 80, 40, 10, 70, 110, 30, 120, 140, 60, 50, 130, 100, 20, 90};

        BinaryHeap<Integer> heap = new BinaryHeap<>(q, Utility.getComparator(true));
        heap.add(6);
        heap.printParentWise();
        System.out.println(heap.peek());
        heap.remove();

        heap.printParentWise();
        heap.heapSort(q, Utility.getComparator(false));
        System.out.println(Arrays.toString(q));

        Graph graph = null;
        Vertex[] verts = null;
        try {
            graph = Graph.createGraphFromUserInput(new Scanner(new File(
                    "edges.txt")), true);
            verts = graph.verts.toArray(new Vertex[graph.verts.size()]);

        } catch (FileNotFoundException e) {
            System.out.println("edges.txt file not found.");
        }

        IndexedHeap<Vertex, Vertex> iHeap = new IndexedHeap<>(verts, Utility.getComparator(true));

    }

}

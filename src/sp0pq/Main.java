package sp0pq;

import sp2.Graph;
import sp2.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        binaryHeapExample();
        indexedHeapExample();

    }

    private static void indexedHeapExample() {
        Graph graph;
        Vertex[] verts = null;
        try {
            graph = Graph.createGraphFromUserInput(new Scanner(new File(
                    "edges.txt")), true);
            verts = graph.verts.toArray(new Vertex[graph.verts.size()]);

        } catch (FileNotFoundException e) {
            System.out.println("edges.txt file not found.");
        }

        VertexIndex index = new VertexIndex();
        IndexedHeap<Vertex, VertexIndex> iHeap = new IndexedHeap<>(verts, index, index);
        iHeap.printParentWise();
    }

    private static void binaryHeapExample() {
        Integer[] q = {0, 150, 80, 40, 10, 70, 110, 30, 120, 140, 60, 50, 130, 100, 20, 90};

        BinaryHeap<Integer> heap = new BinaryHeap<>(q, Utility.getComparator(false));
        heap.add(6);
        heap.printParentWise();
        System.out.println(heap.peek());
        heap.remove();

        heap.printParentWise();
        heap.heapSort(q, Utility.getComparator(false));
        System.out.println(Arrays.toString(q));
    }

}

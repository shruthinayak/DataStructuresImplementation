package sp0pq;

import sp2.Graph;
import sp2.GraphAlgorithms;
import sp2.Vertex;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        binaryHeapExample();
//        indexedHeapExample();
        Graph graph = Utility.getGraph();
        GraphAlgorithms.MSTUsingPrims(graph, graph.verts.get(1));
        GraphAlgorithms.MSTPrimsIndexedPQ(graph, graph.verts.get(1));
    }

    private static void indexedHeapExample() {
        List<Vertex> verts = Utility.getGraph().verts;
        Vertex[] q = new Vertex[verts.size()];
        int i = 0;
        for (Vertex v : verts) {
            q[i++] = v;
        }
        IndexedHeap iHeap = new IndexedHeap(q, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o2.name - o1.name;
            }
        });
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

package sp0pq;

import common.Utility;
import sp2.Edge;
import sp2.Graph;
import sp2.Vertex;

import java.util.*;

public class Solution {
    Graph g;

    public Solution(Graph graph) {
        this.g = graph;
    }

    public static void main(String[] args) {

        Solution main = new Solution(Utility.getGraph("/home/shruthi/AllFiles/OneDrive/Sem4/Impl/sp0pq-big.txt"));
        System.out.println("Graph read!");
        /*System.out.println("Indexed Heap example");
        main.indexedHeapExample();
        System.out.println("Binary Heap example");
        main.binaryHeapExample();*/

        long start = System.currentTimeMillis();
        System.out.println("MST Weight: " + main.MSTUsingPrims(main.g.verts.get(1)));
        long end = System.currentTimeMillis();
        System.out.println("PRIMS 1: " + (end - start) + "ms");
        start = System.currentTimeMillis();
        System.out.println("MST Weight: " + main.MSTPrimsIndexedPQ(main.g.verts.get(1)));
        end = System.currentTimeMillis();
        System.out.println("PRIMS 2: " + (end - start) + "ms");
    }

    private void indexedHeapExample() {
        List<Vertex> verts = g.verts;
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

    private void binaryHeapExample() {
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

    public int MSTUsingPrims(Vertex src) {
        int wmst = 0;
        resetSeen();
        resetParentNull();
        src.seen = true;
        src.distance = 0;
        int size = src.Adj.size();
        Edge[] edges = new Edge[size + 1];
        for (int i = 1; i <= size; i++) {
            edges[i] = src.Adj.get(i - 1);
        }
        BinaryHeap<Edge> heapEdges = new BinaryHeap<>(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {

                return e2.weight - e1.weight;
            }
        });

        ArrayList<Edge> mstE = new ArrayList<>();

        while (!heapEdges.isEmpty() && mstE.size() != (g.verts.size() - 2)) {
            Edge e = heapEdges.remove();
            if (e != null) {
                Vertex u = e.From.seen ? e.To : e.From;
                if (!u.seen) {
                    mstE.add(e);
                    u.seen = true;
                    u.parent = e.otherEnd(u);
                    wmst = wmst + e.weight;

                    for (Edge ed : u.Adj) {
                        if (!ed.otherEnd(u).seen)
                            heapEdges.add(ed);
                    }
                }
            }
        }

        return wmst;
    }

    public int MSTPrimsIndexedPQ(Vertex src) {
        resetSeen();
        resetParentNull();
        Vertex[] q = new Vertex[g.verts.size()];
        int i = 0;
        for (Vertex v : g.verts) {
            v.distance = Integer.MAX_VALUE;

            q[i++] = v;
        }
        src.seen = true;
        src.distance = 0;
        IndexedHeap heap = new IndexedHeap<>(q, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o2.distance - o1.distance;
            }
        });

        HashMap<Vertex, Edge> mstE = new HashMap<>();
        int wmst = 0;
        while (!heap.isEmpty()) {
            Vertex u = (Vertex) heap.remove();
            u.seen = true;
            wmst = wmst + u.distance;

            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                if (!v.seen && e.weight < v.distance) {
                    mstE.putIfAbsent(v, e);
                    mstE.put(v, e);
                    v.distance = e.weight;
                    heap.percolateUp(v.getIndex());
                }
            }
        }
        return wmst;
    }

    private void resetParentNull() {
        for (Vertex v : g.verts) {
            v.parent = null;
        }
    }

    public void resetSeen() {
        for (Vertex v : g.verts) {
            v.seen = false;
        }
    }

}

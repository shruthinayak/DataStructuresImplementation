/**
 * Class to represent a vertex of a graph
 */
package lp2;

import sp0pq.Index;

import java.util.*;

public class Vertex implements Index {
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public boolean strong; //flag to keep track of
    public Vertex parent; // parent of the vertex
    public int distance; // distance to the vertex from the source vertex
    public List<Edge> Adj, revAdj; // adjacency list; use MyLinkedList or
    // ArrayList
    public int degree; // indegree in case of directed graph.
    public int index;
    public HashSet<Edge> AdjHashSet;
    public int countEdge;
    public int component;
    public Queue<Edge> pq;

    /**
     * Constructor for the vertex
     *
     * @param n : int - name of the vertex
     */
    Vertex(int n) {
        name = n;
        seen = false;
        parent = null;
        Adj = new ArrayList<>();
        revAdj = new ArrayList<>(); /* only for directed graphs */
        degree = n == 0 ? -1 : 0;
        AdjHashSet = new HashSet<>();
        countEdge = 0;
        strong = false;
        component = name;
        pq = new PriorityQueue<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Edge e1 = (Edge) o1;
                Edge e2 = (Edge) o2;
                if (e1.weight < e2.weight) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

    }


    public Edge getNextEdge() {
        while (Adj.get(countEdge).seen) {
            countEdge = (countEdge + 1);//%Adj.size();
        }
        return Adj.get(countEdge++);
    }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
        return Integer.toString(name);
    }

    @Override
    public void putIndex(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }
}

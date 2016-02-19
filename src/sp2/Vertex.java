/**
 * Class to represent a vertex of a graph
 */
package sp2;

import sp0pq.Index;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Vertex implements Index {
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public Vertex parent; // parent of the vertex
    public int distance; // distance to the vertex from the source vertex
    public List<Edge> Adj, revAdj; // adjacency list; use MyLinkedList or
    // ArrayList
    public int degree; // indegree in case of directed graph.
    public int index;
    public HashSet<Edge> AdjHashSet;

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

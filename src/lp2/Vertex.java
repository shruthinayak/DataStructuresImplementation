/**
 * Class to represent a vertex of a graph
 */
package lp2;


import java.util.ArrayList;
import java.util.List;

public class Vertex {
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public boolean strong; //flag to keep track if it belongs to a cycle already.
    public Vertex parent; // parent of the vertex
    public boolean processed;
    public int distance; // distance to the vertex from the source vertex
    public List<Edge> Adj, revAdj; // adjacency list; use MyLinkedList or
    // ArrayList
    public int degree; // indegree in case of directed graph.
    public int index;
    public int countEdge;
    public int component;

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
        countEdge = 0;
        strong = false;
        processed = false;
        component = name;

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

}

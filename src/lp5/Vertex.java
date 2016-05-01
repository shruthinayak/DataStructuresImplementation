/**
 * Class to represent a vertex of a graph
 */
package lp5;

import sp0pq.Index;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Index {
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public boolean strong; //flag to keep track of
    public Vertex parent; // parent of the vertex
    public int distance; // distance to the vertex from the source vertex
    public List<Edge> Adj, revAdj; // adjacency processedVertices; use MyLinkedList or
    // ArrayList
    public int degree; // indegree in case of directed graph.
    public int index;
    public int countEdge;
    public int component;
    public long pathLength;
    public int count;
    public boolean isOuter;
    public int tsp; //total shortest paths to this vertex
    public Vertex mate; // matching vertex
    public boolean processed; //whether vertex is already processed for finding new matching


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
        component = name;
        pathLength = Integer.MAX_VALUE;
        count = 0;
        mate = null;
        processed = false;
//        isOuter=false;

    }

    public Edge getNextEdge() {
        while (Adj.get(countEdge).seen && countEdge < Adj.size()) {
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

/**
 * Class to represent a graph
 */
package lp2;

import lp0.Solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Graph implements Iterable<Vertex>, Cloneable {

    public List<Vertex> verts; // array of vertices
    public int numNodes; // number of verices in the graph

    /**
     * Constructor for Graph
     *
     * @param size : int - number of vertices
     */
    Graph(int size) {
        numNodes = size;
        verts = new ArrayList<>(size + 1);
        verts.add(0, new Vertex(0));
        // create an array of Vertex objects
        for (int i = 1; i <= size; i++)
            verts.add(i, new Vertex(i));
    }

    public static Graph createGraphFromUserInput(Scanner in, boolean directed) {
        // read the graph related parameters
        int n = in.nextInt(); // number of vertices in the graph
        int m = in.nextInt(); // number of edges in the graph
        Solution.numEdges = m;
        // create a graph instance
        Graph g = new Graph(n);
        for (int i = 0; i < m; i++) {
            int u = in.nextInt(); // tail
            int v = in.nextInt(); // head
            int w = in.nextInt();
            if (directed) {
                g.addDirectedEdge(u, v, w); // draw a directed edge from u to v.
            } else {
                g.addEdge(u, v, w);
            }
        }
        in.close();
        return g;
    }

    /**
     * Method to add an edge to the graph
     *
     * @param a
     *            : int - one end of edge
     * @param b
     *            : int - other end of edge
     * @param weight
     *            : int - the weight of the edge
     */
    void addEdge(int a, int b, int weight) {
        Vertex u = verts.get(a);
        Vertex v = verts.get(b);
        Edge e = new Edge(u, v, weight);
        u.Adj.add(e);
        v.Adj.add(e);
        u.AdjHashSet.add(e);
        v.AdjHashSet.add(e);
        u.degree++;
        v.degree++;
    }

    /**
     * Method to add an arc (directed edge) to the graph
     *
     * @param a
     *            : int - the head of the arc
     * @param b
     *            : int - the tail of the arc
     * @param weight
     *            : int - the weight of the arc
     */
    void addDirectedEdge(int a, int b, int weight) {
        Vertex tail = verts.get(a);
        Vertex head = verts.get(b);
        Edge e = new Edge(tail, head, weight);
        tail.Adj.add(e);
        head.revAdj.add(e);
        head.pq.add(e);
        head.degree++;
    }

    /**
     * Method to create an instance of VertexIterator
     */
    public Iterator<Vertex> iterator() {
        return new VertexIterator();
    }

    public List<Vertex> getRoots() {
        ArrayList<Vertex> roots = new ArrayList<>();
        for (Vertex v : verts) {
            if (v != null && v.degree == 0) {
                roots.add(v);
            }
        }
        return roots;
    }

    /**
     * A Custom Iterator Class for iterating through the vertices in a graph
     *
     */
    private class VertexIterator implements Iterator<Vertex> {
        private Iterator<Vertex> it;

        /**
         * Constructor for VertexIterator
         *
         */
        private VertexIterator() {
            it = verts.iterator();
            it.next(); // Index 0 is not used. Skip it.
        }

        /**
         * Method to check if there is any vertex left in the iteration
         * Overrides the default hasNext() method of Iterator Class
         */
        public boolean hasNext() {
            return it.hasNext();
        }

        /**
         * Method to return the next Vertex object in the iteration Overrides
         * the default next() method of Iterator Class
         */
        public Vertex next() {
            return it.next();
        }

        /**
         * Throws an error if a vertex is attempted to be removed
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }


    }
}

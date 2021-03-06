/**
 * Class to represent a graph
 */
package lp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph implements Iterable<Vertex>, Cloneable {

    public List<Vertex> verts; // array of vertices
    public int numNodes; // number of verices in the graph
    public boolean positive = true;
    public boolean uniform = true;
    public int uniformWt;
    public HashMap<Vertex, Integer> vertexToMap;
    public List<Edge> edges;
    private List<Vertex> topologicalOrder;

    /**
     * Constructor for Graph
     *
     * @param size : int - number of vertices
     */
    Graph(int size) {
        numNodes = size;
        verts = new ArrayList<>(size + 1);
        vertexToMap = new HashMap<>();
        verts.add(0, new Vertex(0));
        // create an array of Vertex objects
        for (int i = 1; i <= size; i++)
            verts.add(i, new Vertex(i));
        edges = new ArrayList<>();
    }

    public static Graph createGraphFromUserInput(Scanner in, boolean directed) {
        // read the graph related parameters
        int n = in.nextInt(); // number of vertices in the graph
        int m = in.nextInt(); // number of edges in the graph
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

    public static Graph getGraph(Scanner in, boolean directed) {
        Graph graph;
        graph = Graph.createGraphFromUserInput(in, directed);

        return graph;
    }

    public static Graph getGraph(String path, boolean directed) {
        Graph graph;

        try {
            graph = getGraph(new Scanner(new File(path)), directed);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return null;
        }
        return graph;
    }

    /**
     * Method uses DFS on the directed graph to find the topological order.
     *
     * @param g Directed graph
     * @return Stack that contains the topological order in the reverse order
     */
    public static List<Vertex> topologicalOrderUsingDFS(Graph g) {
        /*
         * Algorithm 2. Run DFS on g and push nodes to a stack in the order in
		 * which they finish. Write code without using global variables.
		 */
        resetSeen(g);
        List<Vertex> o = new LinkedList<>();
        List<Vertex> vs = g.verts;
        for (Vertex v : vs) {
            if (!v.seen && !v.strong && v.name != 0) {
                DFS(v, o, false, v.name, false);
            }
        }

        return o;
    }

    public static void resetSeen(Graph g) {
        for (Vertex v : g.verts) {
            v.seen = false;
        }
    }

    static void DFS(Vertex v, List<Vertex> output,
                    boolean useReverse, int component, boolean print) {
        List<Vertex> nodesToVisit = new LinkedList<>();
        nodesToVisit.add(v);
        boolean completed = false;
        while (!nodesToVisit.isEmpty()) {
            Vertex currentNode = nodesToVisit.get(0);
            currentNode.seen = true;
            currentNode.component = component;
            List<Edge> edgeSet = useReverse ? currentNode.revAdj : currentNode.Adj;
            Vertex u = null;
            completed = true;
            for (Edge e : edgeSet) {
                if (e.weight == 0) {
                    e.component = component;
                    u = e.otherEnd(currentNode);
                    if (!u.seen && !u.strong) {
                        nodesToVisit.add(0, u);
                        completed = false;
                        break;
                    }
                }
            }

            if (completed) {
                output.add(0, currentNode);
                nodesToVisit.remove(0);
            }

        }
    }

    /**
     * Method to add an edge to the graph
     *
     * @param a      : int - one end of edge
     * @param b      : int - other end of edge
     * @param weight : int - the weight of the edge
     */
    void addEdge(int a, int b, int weight) {
        Vertex u = verts.get(a);
        Vertex v = verts.get(b);
        Edge e = new Edge(u, v, weight);
        u.Adj.add(e);
        v.Adj.add(e);
        u.degree++;
        v.degree++;
    }

    /**
     * Method to add an arc (directed edge) to the graph
     *
     * @param a      : int - the head of the arc
     * @param b      : int - the tail of the arc
     * @param weight : int - the weight of the arc
     */
    void addDirectedEdge(int a, int b, int weight) {
        Vertex tail = verts.get(a);
        Vertex head = verts.get(b);
        Edge e = new Edge(tail, head, weight);
        tail.Adj.add(e);
        head.revAdj.add(e);
        head.degree++;
        int count = 1;
        if (vertexToMap.containsKey(head)) {
            count = vertexToMap.get(head);
        }
        if (uniformWt == 0)
            uniformWt = weight;
        if (uniform && uniformWt != weight)
            uniform = false;
        if (positive && weight < 0)
            positive = false;
        vertexToMap.put(head, count);
        edges.add(e);

    }

    /**
     * Method returns the topological order of the vertices in the directed
     * graph.
     *
     * @return List that contains the topological order
     */
    public void computeTopologicalOrder() {

        resetSeen();
        topologicalOrder = new ArrayList<>();
        Queue<Vertex> queue = new LinkedList<>();

        for (Vertex v : verts) {
            if (v.degree == 0) {
                queue.add(v);
            }
        }
        while (queue.size() != 0) {
            Vertex u = queue.remove();
            topologicalOrder.add(u);
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                v.degree--;
                if (v.degree == 0) {
                    queue.add(v);
                }
            }
        }
        if (topologicalOrder.size() != numNodes) {
            topologicalOrder = null;
        }

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
     * Returns the root vertex given a name.
     *
     * @param name int: name of the root vertex
     * @return Vertex: Returns the root vertex.
     */

    public Vertex getRoot(int name) {
        for (Vertex v : verts) {
            if (v.name == name)
                return v;

        }
        return null;
    }

    public void resetSeen() {
        for (Vertex v : verts) {
            v.seen = false;
        }
    }

    public List<Vertex> getTopologicalOrder() {
        if (topologicalOrder == null)
            computeTopologicalOrder();
        return topologicalOrder;
    }

    /**
     * A Custom Iterator Class for iterating through the vertices in a graph
     */
    private class VertexIterator implements Iterator<Vertex> {
        private Iterator<Vertex> it;

        /**
         * Constructor for VertexIterator
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

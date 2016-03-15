package lp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by shruthi on 5/3/16.
 */
public class GraphUtility {
    static HashSet<Edge> edges = new HashSet<>();

    /**
     * Reads the user input from the console.
     *
     * @param in       input from console
     * @param directed Is input graph directed?
     * @return Graph object
     */
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
     * Non recursive DFS.
     *
     * @param v          - Vertex: Node at which the DFS should start
     * @param output     - List<Vertex>: Containing the DFS ordering of the vertices
     * @param useReverse - boolean: to indicate whether to use the reverse edges.
     * @param component  - int: Super vertex represented as a component number.
     * @param print      - boolean: if true, edges are printed.
     */
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
                        if (print) {
                            edges.add(e);
                        }
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
     * Method returns the number of strongly connected components for a directed
     * graph.
     *
     * @param g Directed graph
     * @return number of strongly connected components
     */
    public static List<List<Vertex>> stronglyConnected(Graph g) {
        resetSeen(g);
        List<List<Vertex>> cycles = new LinkedList<>();
        List<Vertex> output = topologicalOrderUsingDFS(g);
        resetSeen(g);
        for (Vertex v : output) {
            List<Vertex> o = new LinkedList<>();
            if (!v.seen) {
                DFS(v, o, true, v.name, false);
                if (o.size() > 1) {
                    cycles.add(o);
                }
            }
        }

        return cycles;
    }

    public static void resetSeen(Graph g) {
        for (Vertex v : g.verts) {
            v.seen = false;
        }
    }

    public static void resetStrong(Graph g) {
        for (Vertex v : g.verts) {
            v.strong = false;
        }
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

    /**
     * Returns the root vertex given a name.
     * @param g Graph: Input graph.
     * @param name int: name of the root vertex
     * @return Vertex: Returns the root vertex.
     */
    public static Vertex getRoot(Graph g, int name) {
        for (Vertex v : g.verts) {
            if (v.name == name)
                return v;

        }
        return null;
    }

}

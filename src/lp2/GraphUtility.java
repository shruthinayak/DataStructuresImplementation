package lp2;

import sp2.Edge;
import sp2.Graph;
import sp2.Vertex;

import java.util.*;

/**
 * Created by shruthi on 5/3/16.
 */
public class GraphUtility {
    static HashSet<Edge> edges = new HashSet<>();

    static void DFS(Vertex v, List<Vertex> s, List<Vertex> output,
                    boolean useReverse, int component, boolean print) {

        s.add(0, v);
        v.seen = true;
        v.component = component;
        List<Edge> edgeSet = useReverse ? v.revAdj : v.Adj;

        for (Edge e : edgeSet) {
            if (e.weight == 0) {
                e.component = component;
                Vertex u = e.otherEnd(v);
                if (!u.seen && !u.strong) {
                    if (print) {
                        edges.add(e);

                        //System.out.println(e.toString());
                    }
                    DFS(u, s, output, useReverse, component, print);

                }
            }
        }

        Vertex u = s.remove(0);
        u.seen = true;
        if (u.name != 0) {
            output.add(0, u);
        }

    }


    static void DFS2(Vertex v, List<Vertex> s, List<Vertex> output,
                     boolean useReverse, int component, boolean print) {
        List<Vertex> nodesToVisit = new LinkedList<>();
        nodesToVisit.add(v);
        boolean flag = false;
        while (!nodesToVisit.isEmpty()) {
            Vertex currentNode = nodesToVisit.get(0);
            currentNode.seen = true;
            currentNode.component = component;
            List<Edge> edgeSet = useReverse ? currentNode.revAdj : currentNode.Adj;
            Vertex u = null;
            for (Edge e : edgeSet) {
                if (e.weight == 0) {
                    e.component = component;
                    u = e.otherEnd(currentNode);
                    if (!u.seen && !u.strong) {
                        if (print) {
                            edges.add(e);
                            //System.out.println(e.toString());
                        }
                        nodesToVisit.add(0, u);
                        flag = true;
                        break;
//                    DFS2(u, s, output, useReverse, component, print);

                    }
                }
            }
            if (flag) {
                flag = false;
            } else {
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
        List<Vertex> s = new LinkedList<>();
        List<Vertex> o = new LinkedList<>();
        resetSeen(g);
        for (Vertex v : output) {
            o = new LinkedList<>();
            if (!v.seen) {
                DFS2(v, s, o, true, v.name, false);
                if (o.size() > 1) {
                    cycles.add(o);
                }
//                System.out.println("Component " + conn + ":" + o.toString());
            }
        }

        return cycles;
    }

    public static void resetSeen(Graph g) {
        for (Vertex v : g.verts) {
            v.seen = false;
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
        List<Vertex> s = new LinkedList<>();
        List<Vertex> o = new LinkedList<>();
        List<Vertex> vs = g.verts;
        for (Vertex v : vs) {
            if (!v.seen && !v.strong && v.name != 0) {
                DFS2(v, s, o, false, v.name, false);
            }
        }

        return o;
    }

    public static Vertex getRoot(Graph g, int name) {
        for (Vertex v : g.verts) {
            if (v.name == name)
                return v;

        }
        return null;
    }

}

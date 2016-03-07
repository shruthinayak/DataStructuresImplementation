package lp2;

import common.Utility;
import sp2.Edge;
import sp2.Graph;
import sp2.Vertex;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shruthi on 4/3/16.
 */

public class Solution {
    static int mstWeight = 0;

    public static void main(String[] args) {
        Graph g = Utility.getGraph("edges.txt", true);
        Vertex root = GraphUtility.getRoot(g, 1);
        reduceToZero(g, root);
        List<Vertex> mst = new LinkedList<>();
        boolean isMst = isMST(g, root, mst, false);
        if (!isMst) {//if false, there exists a cycle

            Vertex r;
            for (Vertex v : g.verts) {
                if (!v.seen && !v.strong && v.name != 0) { //For multiple disconnected graphs, find zero weight cycle in each of them.
                    r = v;
                    findZeroWeightCycle(g, r, root);
                }

            }
            mst.clear();
        }
        System.out.println(mstWeight);
        isMst = isMST(g, root, mst, true);

    }

    private static void findZeroWeightCycle(Graph g, Vertex root, Vertex rootNode) {
        //Node root+ component id set to the first node in the cycle
        // nodes which are reachable from node 1 is having component id set to 1
        List<List<Vertex>> cycles = GraphUtility.stronglyConnected(g);
        // for each cycle {
        // create a vertex. Store the cycle.
        //Get revAdj for all vertices in the cycle. Find the minimum.
// }
        for (List<Vertex> cycle : cycles) {
            reduceToZeroComponent(cycle, rootNode);
        }


    }

    private static boolean isMST(Graph g, Vertex root, List<Vertex> o, boolean print) {
        List<Vertex> s = new LinkedList<>();
        resetStrong(g);
        GraphUtility.resetSeen(g);
        GraphUtility.DFS(root, s, o, false, root.name, print);

        for (Vertex v : o) {
            v.strong = true;
        }
        if (o.size() == g.numNodes) {
            return true;
        }
        return false;
    }

    private static void resetStrong(Graph g) {
        for (Vertex v : g.verts) {
            v.strong = false;
        }
    }


    private static void reduceToZero(Graph g, Vertex root) {
        for (Vertex v : g.verts) {
            if (v.name != root.name && v.name != 0) {
                int weight = Integer.MAX_VALUE;
                for (Edge e : v.revAdj) {
                    if (e.weight < weight) {
                        weight = e.weight;

                    }
                }
                mstWeight = mstWeight + weight;
                for (Edge e : v.revAdj) {
                    e.weight = e.weight - weight;

                }

            }
        }

    }


    private static void reduceToZeroComponent(List<Vertex> vertices, Vertex root) {
        int weight = Integer.MAX_VALUE;
        for (Vertex v : vertices) {
            if (v.name != root.name && v.name != 0) {
                for (Edge e : v.revAdj) {
                    if (v.component != e.component) { // if it is of same cycle it will be least as cycle is of 0 weight edges
                        if (e.weight < weight) {
                            weight = e.weight;
                        }

                    }
                }

            }
        }
        mstWeight += weight;
        for (Vertex v : vertices) {
            if (v.name != root.name && v.name != 0) {
                for (Edge e : v.revAdj) {
                    if (v.component != e.component)
                        e.weight = e.weight - weight;
                }
            }

        }


    }


}

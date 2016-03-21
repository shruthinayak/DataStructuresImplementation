package lp2;
/*

import lp2.Utility;
import lp2.Edge;
import lp2.Graph;
import lp2.Vertex;
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class SolutionOld {
    static int mstWeight = 0;
    static HashSet<String> edgeSet = new HashSet<>();

    public static void main(String[] args) throws FileNotFoundException {
        Graph g = Utility.getGraph("lp2-data/2-lp2.txt", true);
//        Graph g = Utility.getGraph("edges.txt", true);
        Scanner sc = new Scanner(new FileInputStream("lp2-data/2-lp2.txt"));
//        Scanner sc= new Scanner(new FileInputStream("edges.txt"));
        String line = sc.nextLine();
        int num = Integer.parseInt(line.split(" ")[1]);
        while (num + 2 > 0) {
            line = sc.nextLine();
            num--;
        }
        line = sc.nextLine();
        int mstw = Integer.parseInt(line);

        while (sc.hasNext()) {
            edgeSet.add(sc.nextLine());
        }


        Vertex root = GraphUtility.getRoot(g, 1);
        reduceToZero(g, root);
        List<Vertex> mst = new LinkedList<>();
        boolean isMst = isMST(g, root, mst, false);
        while (!isMst) {//if false, there exists a cycle

            Vertex r;
            for (Vertex v : g.verts) {
                if (!v.seen && !v.strong && v.name != 0) { //For multiple disconnected graphs, find zero weight cycle in each of them.
                    r = v;
                    findZeroWeightCycle(g, r, root);
//                    findCycle(g,r);
                }

            }
            mst.clear();
            System.out.println(mstWeight);
            isMst = isMST(g, root, mst, true);

        }
        long actmst = 0;
        for (Edge e : GraphUtility.edges) {
            actmst += e.actualWeight;

        }

        System.out.println(mstWeight);
        System.out.println(actmst);


    }


    public static void findCycle(Graph g, Vertex v) {
        while (!v.seen) {
            v.seen = true;
            for (Edge edge : v.revAdj) {
                if (edge.weight == 0) {
                    v = edge.otherEnd(v);
                    break;
                }
            }
        }
        Vertex startVertex = v;
        List<Vertex> cycle = new LinkedList<>();
        while (true) {
            cycle.add(startVertex);
            for (Edge e : startVertex.revAdj) {
                if (e.weight == 0) {
                    startVertex.component = v.name;
                    e.component = v.name;
                    startVertex = e.otherEnd(startVertex);
                    break;
                }
            }
            if (startVertex == v) {
                break;
            }
        }
        reduceToZeroComponent(cycle, g.verts.get(1));

    }

    ///commit
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
//        GraphUtility.DFS(root, s, o, false, root.name, print);

        for (Vertex v : o) {
            v.strong = true;
        }
        if (o.size() == g.numNodes) {

            checkwithans(o);
            return true;
        }
        return false;
    }

    private static void checkwithans(List<Vertex> o) {

        for (Edge e : GraphUtility.edges) {
            if (edgeSet.contains(e.toString())) {
                continue;
            } else {
                System.out.println("false");
            }
        }
        System.out.println("True");
    }

    private static void resetStrong(Graph g) {
        for (Vertex v : g.verts) {
            v.strong = false;
        }
    }


    public static void verifyMST(Graph g, Vertex root, int wmst, List<Edge> mst) {
        int verifyWMst = 0;
        Vertex other = root;
        for (Edge e : mst) {
            if (root.Adj.contains(e) || other.Adj.contains(e)) {
                other = e.otherEnd(root);
                verifyWMst += e.actualWeight;
            } else {
                System.out.println("not proper mst");
                break;
            }
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

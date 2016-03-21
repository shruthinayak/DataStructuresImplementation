package lp2;

//import Utility;

import lp2.Edge;
import lp2.Graph;
import lp2.Vertex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

//old solutions test commit
public class Solution313 {
    static int mstWeight = 0;
    static HashSet<String> edgeSet = new HashSet<>();

    public static void main(String[] args) throws FileNotFoundException {
        Graph g = Utility.getGraph("lp2-data/lp2-ck.txt", true);
//        Graph g = Utility.getGraph("edges.txt", true);
        Scanner sc = new Scanner(new FileInputStream("lp2-data/out-ck.txt"));
//        Scanner sc= new Scanner(new FileInputStream("edges.txt"));
        String line = sc.nextLine();
        /*int num = Integer.parseInt(line.split(" ")[1]);
        while (num + 2 > 0) {
            line = sc.nextLine();
            num--;
        }
        line = sc.nextLine();*/
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
//            System.out.println(mstWeight);
            isMst = isMST(g, root, mst, true);

        }

        long actmst = 0;
        for (Vertex v : g.vertexToMap.keySet()) {
            if (v.revAdj.size() > 1) {
                System.out.println(v);
            }
        }
        for (Edge e : GraphUtility.edges) {
            actmst += e.actualWeight;

        }
        System.out.println("Actual mst" + mstw);

        System.out.println("mst found while reducing" + mstWeight);
        System.out.println("mst found after finding minimum spanning tree" + actmst);

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
//        GraphUtility.DFS2(root, o, false, root.name, print);

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
        Set<String> edgeStringSet = new HashSet<>();
        int count = 0;

        boolean flag = false;
        for (Edge e : GraphUtility.edges) {
            edgeStringSet.add(e.toString());
            if (edgeSet.contains(e.toString())) {
                continue;
            } else {
                count++;
                System.out.println("not found " + e.toString());
                flag = true;
                //break;
            }
        }
        int c = 0;
        System.out.println("-----------------------------------");
        for (String ed : edgeSet) {
            if (edgeStringSet.contains(ed)) {
                continue;
            } else {
                c++;
                System.out.println(ed);
            }
        }

        System.out.println("noof edges not matching " + count + " not found" + c);
//        Sets.SetView<Number> difference = com.google.common.collect.Sets.difference((Set<?>) edgeStringSet, (Set<?>) edgeSet);
        if (flag) System.out.println("false");
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

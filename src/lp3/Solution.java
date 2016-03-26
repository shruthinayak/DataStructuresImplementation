package lp3;

import java.util.List;

/**
 * Created by shruthi on 19/3/16.
 */
public class Solution {
    public static void main(String[] args) {
        String baseS = "lp3-l1/";
        String[] s = {
                "lp3-l1-in1.txt",
                "lp3-l1-in2.txt",
                "lp3-l1-in3.txt",
                "lp3-l1-in4.txt",
                "lp3-l1-in-k1.txt",
                "lp3-l1-in-k2.txt",
                "lp3-l1-in-k3.txt",
                "lp3-l1-in-k4.txt",
        };
        String baseB = "/home/shruthi/AllFiles/OneDrive/Sem4/Impl/lp3-data/lp3-l2/";
        String[] b = {
                /*"1.txt",
                "2.txt",*/
                /*"3.txt",
                "4.txt",
                "101a.txt",
                "101b.txt",
                "101c.txt",
                "101d.txt",
                "101e.txt",
                "channel-20-3.txt",
                "channel-50-2.txt",*/
                "rchannel-100-2-3.txt",
        };

        for (String path : b) {
            Graph g = Graph.getGraph(baseB + path, true);
            System.out.println(path);
            System.out.print("\t");
            ShortestPath sp = null;
            Vertex src = g.getRoot(1);
            if (g.uniform) {
                sp = new BFS(g, src);
                System.out.print("BFS ");
            } else if (g.positive) {
                sp = new Dijkstra(g, src);
                System.out.print("Dij ");
            } else if (g.getTopologicalOrder() != null) {
                sp = new DAGShortestPath(g, src);
                System.out.print("DAG ");
            } else {
                sp = new BellmanFord(g, src);
                //Should go as else part of if(sp!=null)
                if (sp.getTotalOfShortestPaths() == ShortestPath.INF) {
                    System.out.println("OR Unable to solve problem. Graph has a negative cycle");
                } else System.out.print("Bellman-Ford ");

                //Bellman
                //set sp to null if negative cycle encountered
            }


            if (sp != null) {
                long start = System.currentTimeMillis();
                sp.getShortestPath();
                getTotalShortestPaths(g, src);
                System.out.println(sp.getTotalOfShortestPaths());
                System.out.println(System.currentTimeMillis() - start + "ms");
                //sp.print();
            }
        }

    }

    public static void getTotalShortestPaths(Graph g, Vertex src) {
        for (Edge e : g.edges) {
            Vertex u = e.From;
            Vertex v = e.otherEnd(u);
            if (u.distance + e.weight == v.distance)
                e.D = true;
        }
        findTotalShortestPaths(g, src);

    }

    public static int findTotalShortestPaths(Graph g, Vertex src) {
        List<Vertex> reverse = g.getTopologicalOrder();
        System.out.println(reverse.toString());
        for (Vertex v : g) {
            v.tsp = 0;
        }
        src.tsp = 1;
        for (Vertex u : reverse) {
            for (Edge e : u.revAdj) {
                if (e.D)
                    u.tsp = u.tsp + e.otherEnd(u).tsp;
            }
        }
        int total = 0;
        for (Vertex u : g)
            total += u.tsp;

        System.out.println(src.name + " " + src.distance + " " + src.tsp);
        for (Vertex v : g) {
            if (v != src)
                if (v.name != 0 && v.distance < Integer.MAX_VALUE) {
                    System.out.println(v.name + " " + v.distance + " " + v.tsp);
                } else {
                    System.out.println(v.name + " INF -");
                }
        }
        System.out.println(total);
        return total;

    }
}

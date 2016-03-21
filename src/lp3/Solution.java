package lp3;

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
        String baseB = "lp3-big/";
        String[] b = {
                "lp3-l1-in-m1.txt",
                "lp3-l1-in-m2.txt",
                "lp3-l1-in-m3.txt",
                "lp3-l1-in-m4.txt"
        };

        for (String path : b) {
            Graph g = Graph.getGraph(baseB + path, true);
            System.out.println(path);
            System.out.print("\t");
            ShortestPath sp = null;
            if (g.uniform) {
                sp = new BFS(g, g.getRoot(1));
                System.out.print("BFS ");
            } else if (g.positive) {
                sp = new Dijkstra(g, g.getRoot(1));
                System.out.print("Dij ");
            } else if (g.getTopologicalOrder() != null) {
                sp = new DAGShortestPath(g, g.getRoot(1));
                System.out.print("DAG ");
            } else {
                sp = new BellmanFord(g, g.getRoot(1));
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
                System.out.println(sp.getTotalOfShortestPaths());
                System.out.println(System.currentTimeMillis() - start + "ms");
                //sp.print();
            }
        }

    }
}

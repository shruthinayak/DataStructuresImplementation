package lp3;

/**
 * Created by shruthi on 19/3/16.
 */
public class Solution {
    public static void main(String[] args) {
        String baseS = "/home/shruthi/AllFiles/OneDrive/Sem4/Impl/lp3-data/lp3-l1/";
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
        String baseB = "/home/shruthi/AllFiles/OneDrive/Sem4/Impl/lp3-big/";
        String[] b = {
                "lp3-l1-in-m1.txt",
                "lp3-l1-in-m2.txt",
                "lp3-l1-in-m3.txt",
                "lp3-l1-in-m4.txt",
        };

        for (String path : b) {
            Graph g = Graph.getGraph(baseB + path, true);
            System.out.println(path);
            System.out.print("\t");
            ShortestPath sp = null;
            if (g.uniform) {
                //BFS
                System.out.println("BFS");
            } else if (g.positive) {
                System.out.println("Dij");
                sp = new Dijkstra(g, g.getRoot(1));
            } else if (g.getTopologicalOrder() != null) {
                System.out.println("DAG");
                sp = new DAGShortestPath(g, g.getRoot(1));
            } else {
                System.out.println("Bellman-Ford");
                //Should go as else part of if(sp!=null)
                System.out.println("OR Unable to solve problem. Graph has a negative cycle");
                //Bellman
                //set sp to null if negative cycle encountered
            }


            if (sp != null) {
                sp.getShortestPath();
//                sp.print();
            }
        }

    }
}

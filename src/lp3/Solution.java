package lp3;

import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * G21:
 * Shruthi Ramesh, Nayak: sxn145130
 * Tejasvee Bolisetty: txb140830
 */
public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        Graph g = Graph.createGraphFromUserInput(new Scanner(System.in), true);
        ShortestPath sp = null;
        Vertex src = g.getRoot(1);
        if (g.uniform) {
            sp = new BFS(g, src);
        } else if (g.positive) {
            sp = new Dijkstra(g, src);
        } else if (g.getTopologicalOrder() != null) {
            sp = new DAGShortestPath(g, src);

        } else {
            sp = new BellmanFord(g, src);
        }
        if (sp != null) {
            sp.getShortestPath();
            print(src, g, sp);
        }
    }


    public static void print(Vertex src, Graph g, ShortestPath sp) {
        System.out.println(sp.getName() + " " + sp.getTotalOfShortestPaths());

        if (sp.getName().equals("B-F") && sp.getTotalOfShortestPaths() == sp.INF) {
            System.out.println("Unable to solve problem. Graph has a negative cycle");
            return;
        }

        if (!(g.numNodes > 100)) {
            System.out.println(src.name + " " + src.distance + " -");
            for (Vertex v : g) {
                if (v != src)
                    if (v.name != 0 && v.distance < sp.INF) {
                        System.out.println(v.name + " " + v.distance + " " + v.parent.name);
                    } else {
                        System.out.println(v.name + " INF -");
                    }
            }
        }
    }

}

package common;

import sp2.Edge;
import sp2.Graph;
import sp2.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by shruthi on 10/2/16.
 */
public class Utility {

    public static Comparator getComparator(boolean isAscending) {
        return new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                int a = (int) o1;
                int b = (int) o2;

                if (isAscending)
                    return a - b;
                else
                    return b - a;

            }
        };
    }

    public static Graph getGraph(String path) {
        Graph graph;

        try {
            graph = Graph.createGraphFromUserInput(new Scanner(new File(path)), false);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return null;
        }
        return graph;
    }

    public static boolean isBipartite(Graph g) {

        Queue<Vertex> Q = new LinkedList<>();

        for (Vertex u : g) {
            u.seen = false;
            u.parent = null;
            u.distance = Integer.MAX_VALUE;
        }

        // Run BFS on every component
        for (Vertex src : g) {
            if (!src.seen) {
                src.distance = 0;
                Q.add(src);
                src.seen = true;

                while (!Q.isEmpty()) {
                    Vertex u = Q.remove();
                    for (Edge e : u.Adj) {
                        Vertex v = e.otherEnd(u);
                        if (!v.seen) {
                            v.seen = true;
                            v.parent = u;
                            v.distance = u.distance + 1;
                            Q.add(v);
                        } else {
                            if (u.distance == v.distance) {
                                System.out.println("Graph is not bipartite");
                                return false;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Graph is bipartite");
        for (Vertex u : g) {
            if (u.distance % 2 == 0) {
                System.out.println(u + " Outer");
            } else {
                System.out.println(u + " Inner");
            }
        }
        return true;
    }

}



package lp5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * G21:
 * Shruthi Ramesh, Nayak: sxn145130
 * Tejasvee Bolisetty: txb140830
 */


public class LP5Lev1 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        boolean VERBOSE = false;
        String path = "lp5-data";
        args = new String[]{path + "/bip4.txt"};
//        args = new String[]{"edges.txt"};
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        if (args.length > 1) {
            VERBOSE = true;
        }
        Graph g = Graph.readGraph(in, false);   // read undirected graph from stream "in"
        BipartiteMatching b = new BipartiteMatching();
        if (b.checkBipartite(g)) {
            int result = b.maximumMatching(g);
            System.out.println(result);
            if (VERBOSE) {
                b.resetSeen(g);
                for (Vertex v : g.verts) {
                    if (v != null && !v.seen && v.mate != null) {
                        Vertex u = v.mate;
                        v.seen = true;
                        u.seen = true;
                        for (Edge e : v.Adj) {
                            if (e.otherEnd(v) == u) {
                                System.out.println(v + " " + u + " " + e.weight);
                                break;
                            }
                        }
                    }
                }
//                     Output the edges of M.

            }

        } else {
            System.out.println("G is not bipartite");
            return;
        }

    }

}

package lp5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LP5Driver {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        boolean VERBOSE = false;
        String path = "lp5-data";
        args = new String[]{path + "/bip4.txt"};
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
            int size = b.maximumMatching(g);
            System.out.println("max size " + size);
        } else {
            System.out.println("G is not bipartite");
            return;
        }

        // Create your own class and call the function to find a maximum matching.
        // int result = Matching.matching(g);
        // System.out.println(result);
        // if (VERBOSE) {
        //     Output the edges of M.
        // }
    }
}


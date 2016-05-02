package lp5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by tejas on 5/1/2016.
 */
public class LP5Lev3 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        boolean VERBOSE = false;
        String path = "lp5-data";
//        args = new String[]{"edges.txt"};
        args = new String[]{path + "/bip1.txt"};
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
        BipartiteMatching bm = new BipartiteMatching();
        MaximumWeightedMatching b = new MaximumWeightedMatching(g);
        if (bm.checkBipartite(g)) {
            int size = b.computeMaxWeightedMatching();
            System.out.println("max weighted " + size);
        } else {
            System.out.println("G is not bipartite");
            return;
        }

       /* // Create your own class and call the function to find a maximum matching.
        // int result = Matching.matching(g);
        // System.out.println(result);
        // if (VERBOSE) {
        //     Output the edges of M.
        // }*/
    }

}

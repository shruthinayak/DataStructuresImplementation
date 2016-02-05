package sp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author shruthi
 * @author tejasvee
 */
public class Solution {

    public static void main(String[] args) {
        Graph graph = null;
        try {
            graph = Graph.createGraphFromUserInput(new Scanner(new File(
                    "edges.txt")), true);
        } catch (FileNotFoundException e) {
            System.out.println("edges.txt file not found.");
        }
        if (graph != null) {
            GraphAlgorithms.topologicalOrderImpl1(graph);
            GraphAlgorithms.topologicalOrderUsingDFS(graph);
            GraphAlgorithms.stronglyConnected(graph);
            GraphAlgorithms.diameter(graph);
            GraphAlgorithms.oddLengthCycle(graph);
            GraphAlgorithms.testEulerian(graph);
        }

    }


}

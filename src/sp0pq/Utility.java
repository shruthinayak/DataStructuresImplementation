package sp0pq;

import sp2.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
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

    public static Graph getGraph() {
        Graph graph;

        try {
            graph = Graph.createGraphFromUserInput(new Scanner(new File(
                    "edges.txt")), false);
        } catch (FileNotFoundException e) {
            System.out.println("edges.txt file not found.");
            return null;
        }
        return graph;
    }
}



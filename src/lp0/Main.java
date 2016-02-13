package lp0;

import common.Utility;
import sp2.Edge;
import sp2.Graph;
import sp2.GraphAlgorithms;

import java.util.List;

/**
 * Created by shruthi on 12/2/16.
 */
public class Main {
    public static void main(String[] args) {
        //String path = "/home/shruthi/AllFiles/OneDrive/Sem4/Impl/lp0-big.txt";
        String path = "edges.txt";
        Graph g = Utility.getGraph(path);
        if (GraphAlgorithms.testEulerian(g)) {

        }
    }

    static List<Edge> findEulerTour(Graph g) {
//        List<Vertex>
        return null;
    }
}

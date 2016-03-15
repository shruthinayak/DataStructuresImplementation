package lp2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class OldSolution {
    static int mstWeight = 0;
    static HashSet<String> actualAnswer = new HashSet<>();

    public static void main(String[] args) throws FileNotFoundException {
        //Scanner read = new Scanner(System.in);
        String path = "/home/shruthi/AllFiles/OneDrive/Sem4/Impl/lp2-data/0-lp2.txt";
        Graph g = GraphUtility.getGraph(path, true);
        boolean print = g.numNodes <= 50;
        Vertex root = GraphUtility.getRoot(g, 1);
        reduceToZero(g, root);
        boolean isMst; //if false, there exists a cycle
        do {
            isMst = isMST(g, root, true);
            for (Vertex v : g.verts) {
                if (!v.seen && !v.strong && v.name != 0) { //For multiple disconnected graphs, find zero weight cycle in each of them.
                    findZeroWeightCycle(g, root);
                }
            }
        } while (!isMst);

        System.out.println(mstWeight);
        if (print) {
            for (Edge e : GraphUtility.edges)
                System.out.println(e.toString());
        }

    }

    private static void initAnswerFile(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(path));
        String line = sc.nextLine();
        int num = Integer.parseInt(line.split(" ")[1]);
        while (num + 2 > 0) {
            line = sc.nextLine();
            num--;
        }
        line = sc.nextLine();
        int mstw = Integer.parseInt(line);

        while (sc.hasNext()) {
            actualAnswer.add(sc.nextLine());
        }
    }

    private static void findZeroWeightCycle(Graph g, Vertex rootNode) {
        //Node root+ component id set to the first node in the cycle
        // nodes which are reachable from node 1 is having component id set to 1
        List<List<Vertex>> cycles = GraphUtility.stronglyConnected(g);
        for (List<Vertex> cycle : cycles) {
            reduceToZeroComponent(cycle, rootNode);
        }


    }

    private static boolean isMST(Graph g, Vertex root, boolean print) {
        List<Vertex> o = new LinkedList<>();
        GraphUtility.resetStrong(g);
        GraphUtility.resetSeen(g);
        GraphUtility.DFS(root, o, false, root.name, print);

        for (Vertex v : o) {
            v.strong = true;
        }
        if (o.size() == g.numNodes) {
            mstWeight = 0;
            for (Edge e : GraphUtility.edges)
                mstWeight += e.actualWeight;
            return true;
        }
        return false;
    }

    private static void reduceToZero(Graph g, Vertex root) {
        Vertex minVertex = null;
        for (Vertex v : g.verts) {
            if (isValidVertex(root, v)) {
                int weight = getMinimumWeight(Integer.MAX_VALUE, v);
                mstWeight = mstWeight + weight;
                reduceAllEdgesToCheapEdge(weight, v);
            }
        }
    }


    private static void reduceToZeroComponent(List<Vertex> vertices, Vertex root) {
        int weight = Integer.MAX_VALUE;
        //Get minimum weight considering all vertices belonging to a particular component.
        for (Vertex v : vertices) {
            if (isValidVertex(root, v)) {
                weight = getMinimumWeight(weight, v);
            }
        }
        mstWeight += weight;
        for (Vertex v : vertices) {
            if (isValidVertex(root, v)) {
                reduceAllEdgesToCheapEdge(weight, v);
            }
        }
    }

    private static boolean isValidVertex(Vertex root, Vertex v) {
        return v.name != root.name && v.name != 0;
    }

    private static void reduceAllEdgesToCheapEdge(int weight, Vertex v) {
        for (Edge e : v.revAdj) {
            if (v.component != e.component)
                e.weight = e.weight - weight;
        }
    }

    private static int getMinimumWeight(int weight, Vertex v) {

        for (Edge e : v.revAdj) {
            if (v.component != e.component) { // if it is of same cycle it will be least as cycle is of 0 weight edges
                if (e.weight < weight) {
                    weight = e.weight;
                }
            }
        }
        return weight;
    }
}

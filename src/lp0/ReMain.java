package lp0;

import common.Utility;
import sp2.Edge;
import sp2.Graph;
import sp2.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ReMain {

    public static int numEdges;

    public static void main(String[] args) {
//        String path = "/home/shruthi/AllFiles/OneDrive/Sem4/Impl/lp0-big.txt";
//String path = "/home/shruthi/AllFiles/OneDrive/Sem4/Impl/lp0-data/tour/lp0-s1-k5k.txt";
        String path = "edges.txt";
        Graph g = Utility.getGraph(path);
        System.out.println("Graph read");
        //if (Utility.isBipartite(g)) {

        long start = System.currentTimeMillis();
        List<Edge> eulerTour = findEulerTour(g);
        long end = System.currentTimeMillis();
        System.out.println(eulerTour.size() == numEdges);
        System.out.println(end - start + "ms");
        //}
    }

    static List<Edge> findEulerTour(Graph g) {
        List<Vertex> vertices = new ArrayList<>();
        HashMap<Vertex, List<Edge>> hm = new HashMap<>();
        List<Edge> edges = new ArrayList<>();

        Vertex masterSrc = getVertex(g);
        vertices.add(masterSrc);

        while (edges.size() != numEdges) {
//            System.out.println(edges.size());
            Vertex src = pickRandomVertex(vertices);
            Vertex next = src;
            List<Edge> temp = new ArrayList<>();
            do {
                if (next.degree > 0) {
                    Edge edge = next.Adj.get(0);
                    next.Adj.remove(0);
                    next.degree--;
                    edge.now = next;
                    edges.add(edge);
                    temp.add(edge);
                    next = edge.otherEnd(next);
                    next.Adj.remove(edge);
                    next.degree--;
                    vertices.add(next);
                } else {
                    vertices.remove(next);
                }

            } while (next != src);
            if (!temp.isEmpty())
                hm.put(src, temp);
        }
        for (Vertex key : hm.keySet()) {
            System.out.println(key.name + " : " + hm.get(key));
        }
        return readHM(masterSrc, hm, new ArrayList<>());

    }


    public static List<Edge> readHM(Vertex src, HashMap<Vertex, List<Edge>> hm, List<Edge> finall) {

        List<Edge> edges = hm.get(src);
        Edge e1 = edges.get(0);
        finall.add(e1);
        hm.get(src).remove(e1);

        for (Edge e : edges) {

            if (hm.containsKey(e.now)) {
                readHM(e.now, hm, finall);
            }
            finall.add(e);
        }
        if (hm.get(src).isEmpty()) {
            hm.remove(src);
        }
        System.out.println(finall.toString());
        return finall;
    }

    public static Vertex getVertex(Graph g) {
        return pickRandomVertex(g.verts);
    }

    private static Vertex pickRandomVertex(List<Vertex> verts) {
        if (verts.size() != 1) {
            Random random = new Random();
            int num = random.nextInt(verts.size() - 1);
            return verts.get(num + 1);
        }
        return verts.get(0);

    }
}

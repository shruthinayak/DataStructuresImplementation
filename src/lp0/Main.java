package lp0;

import common.Utility;
import sp2.Edge;
import sp2.Graph;
import sp2.Vertex;

import java.util.*;

public class Main {

    public static int numEdges;
    private static Vertex masterSrc;

    public static void main(String[] args) {
        String path = "/home/shruthi/AllFiles/OneDrive/Sem4/Impl/lp0-big.txt"; //took 57758ms
//        String path = "/home/shruthi/AllFiles/OneDrive/Sem4/Impl/lp0-data/tour/lp0-s1-ck.txt";
//        String path = "edges.txt";

        Graph g = Utility.getGraph(path);
        System.out.println("Graph read");

        long start = System.currentTimeMillis();
        Node eulerTour = findEulerTour(g);
        long end = System.currentTimeMillis();

        g = Utility.getGraph(path);
        verifyTour(g, eulerTour.link, masterSrc);

        System.out.println(eulerTour.size == numEdges);
        System.out.println(end - start + "ms");
    }

    static Node findEulerTour(Graph g) {
        HashMap<Vertex, Node> hm = new HashMap<>();
        Node root = new Node();
        Set<Vertex> q = new LinkedHashSet<>();
        masterSrc = pickRandomVertex(g.verts);
        q.add(masterSrc);
        hm.put(masterSrc, root);
        while (!q.isEmpty() && root.size != numEdges) {
            Vertex src = q.iterator().next();
            q.remove(src);
            Vertex next = src;
            Node mergeNode = hm.get(next);
            Node nextLink = mergeNode.link;
            do {
                if (next.degree > 0) {
                    Edge edge = next.Adj.get(0);
                    next.Adj.remove(0);
                    next.degree--;
                    next = edge.otherEnd(next);
                    next.Adj.remove(edge);
                    next.degree--;
                    edge.now = next;
                    mergeNode.link = new Node(edge);
                    mergeNode = mergeNode.link;
                    root.size++;
                    hm.put(next, mergeNode);
                    q.add(next);
                } else {
                    q.remove(next);
                }
            } while (next != src && root.link.size != numEdges);

            if (nextLink != null)
                mergeNode.link = nextLink;
        }
        return root;
    }

    static boolean verifyTour(Graph g, Node tour, Vertex start) {
        return false;
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

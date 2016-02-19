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
        String path = "lp0-big.txt"; //took 57758ms 66197ms // now taking only 5059ms
//        String path = "lp0-s1-ck.txt";
//        String path = "edges.txt";

        Graph g = Utility.getGraph(path);
        System.out.println("Graph read");

        long start = System.currentTimeMillis();
        Node eulerTour = findEulerTour(g);
        long end = System.currentTimeMillis();
        System.out.println(eulerTour.size == numEdges);
        System.out.println(end - start + "ms");

//        g = Utility.getGraph(path);
//        eulerTour.next.print();
        System.out.println(verifyTour(g, eulerTour.next, masterSrc));
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
            Vertex vertex = src;
            Node mergeNode = hm.get(vertex);
            Node nextLink = mergeNode.next;
            do {
                if (vertex.degree > 0) {
//                    Edge edge = vertex.Adj.get(0);
                    Edge edge = vertex.getNextEdge();
                    edge.seen = true;
//                    vertex.Adj.remove(0);
                    vertex.degree--;
                    vertex = edge.otherEnd(vertex);
//                    vertex.Adj.remove(edge);
                    vertex.degree--;
                    mergeNode.next = new Node(edge);
                    mergeNode = mergeNode.next;
                    root.size++;
                    hm.put(vertex, mergeNode);
                    q.add(vertex);
                } else {
                    q.remove(vertex);
                }
            } while (vertex != src && root.size != numEdges);
            mergeNode.next = nextLink;
        }
        return root;
    }

    static boolean verifyTour(Graph g, Node tour, Vertex start) {
        int countEdges = 0;
        while (tour != null) {
            Edge edge = tour.edge;
            if (edge.seen && checkStartVertex(edge, start)) {
                countEdges++;
                edge.seen = false;
                tour = tour.next;
                start = edge.otherEnd(start);
            } else return false;
        }
        if (countEdges == numEdges) return true;
        else return false;
    }

    private static boolean checkStartVertex(Edge edge, Vertex start) {
        if (start == edge.From || start == edge.To) {
            return true;
        } else return false;
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

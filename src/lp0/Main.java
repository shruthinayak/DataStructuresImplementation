package lp0;

import common.Utility;
import sp2.Edge;
import sp2.Graph;
import sp2.Vertex;

import java.util.*;

import static java.lang.System.exit;

public class Main {

    public static int numEdges;
    private static Vertex masterSrc;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide the file path");
            exit(1);
        }

        String path = args[0];
        Graph g = Utility.getGraph(path);

        long start = System.currentTimeMillis();
        Node eulerTour = findEulerTour(g);
        long end = System.currentTimeMillis();

        eulerTour.next.print();
        verifyTour(g, eulerTour.next, masterSrc);
//        System.out.println("Is it a valid Euler tour? " + verifyTour(eulerTour.next, masterSrc));
//        System.out.println("Time taken to find the Euler tour : " + (end - start) + "ms");
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
                    Edge edge = vertex.getNextEdge();
                    edge.seen = true;
                    vertex.degree--;
                    vertex = edge.otherEnd(vertex);
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
        return countEdges == numEdges;
    }

    private static boolean checkStartVertex(Edge edge, Vertex start) {
        return start == edge.From || start == edge.To;
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

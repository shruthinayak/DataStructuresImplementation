package lp0;

import common.Utility;
import sp2.Edge;
import sp2.Graph;
import sp2.GraphAlgorithms;
import sp2.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.System.exit;

public class Solution {

    public static int numEdges;
    private static Vertex masterSrc;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

        if (args.length != 1) {
            System.out.println("Please provide the file path");
            exit(1);
        }

        Graph g = Utility.getGraph(in, false);
        if (GraphAlgorithms.testEulerian(g) == -1) {
            System.out.println("Graph is not Eulerian");
            return;
        }

        System.out.println("Entered");
        List<Edge> eulerTour = findEulerTour(g);
        for (Edge e : eulerTour) {
            System.out.println(e);
        }

    }

    static List<Edge> findEulerTour(Graph g) {
        long start = System.currentTimeMillis();
        Node root = getRoot(g);
        long end = System.currentTimeMillis();
        System.out.println("Total time taken: " + (end - start) + "ms");
        List<Edge> tour = new ArrayList<>();
        Node s = root.next;
        while (s != null) {
            tour.add(s.edge);
            s = s.next;
        }
        return tour;
    }

    private static Node getRoot(Graph g) {
        HashMap<Vertex, Node> hm = new HashMap<>();
        Node root = new Node();
        Set<Vertex> q = new LinkedHashSet<>();
        List<Vertex> oddVertices = getOddVertices(g);
        masterSrc = g.verts.get(1);
        if (oddVertices.size() == 2) {
            root = findEulerPath(oddVertices);
        } else {
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
                } while ((vertex != src) && root.size != numEdges);
                mergeNode.next = nextLink;
            }
        }
        if (verifyTour(g, root.next, masterSrc)) {
            System.out.println("Euler Tour/ Path is correct");
        }
        return root;
    }

    static Node findEulerPath(List<Vertex> oddVertices) {
        HashMap<Vertex, Node> hm = new HashMap<>();
        Node root = new Node();
        Set<Vertex> q = new LinkedHashSet<>();

        masterSrc = oddVertices.get(1);
        q.add(masterSrc);
        hm.put(masterSrc, root);
        while (!q.isEmpty() && root.size != numEdges) {
            Vertex src = q.iterator().next();
            if (masterSrc.degree != 0) {
                src = masterSrc;
            }
            q.remove(src);
            Vertex vertex = src;
            Node mergeNode = hm.get(vertex);
            Node nextLink = mergeNode.next;
            do {
                Edge edge = null;
                if (vertex.degree > 0) {
                    edge = vertex.getNextEdge();
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
                if (vertex.degree == 0) {
                    q.remove(vertex);
                    break;
                }
            } while ((vertex != src) && root.size != numEdges);
            mergeNode.next = nextLink;
        }
        return root;
    }

    private static List<Vertex> getOddVertices(Graph g) {
        ArrayList<Vertex> oddVertex = new ArrayList();
        for (Vertex v : g) {
            if (v != null) {
                if (v.degree % 2 != 0) {
                    oddVertex.add(v);
                }
            }
        }
        return oddVertex;
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

}

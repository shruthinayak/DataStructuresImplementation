package lp3;

import java.util.HashSet;
import java.util.Set;

public class Dijkstra implements ShortestPath {

    Graph g;
    Vertex src;
    int totalPath = 0;

    Dijkstra(Graph g, Vertex src) {
        this.g = g;
        this.src = src;
    }

    @Override
    public void getShortestPath() {
        Set<Vertex> vertexSet = new HashSet<>();

        for (Vertex v : g) {
            v.distance = INF;
            v.parent = null;
            if (v.name != 0) {
                vertexSet.add(v);
            }
        }


        src.distance = 0;
        src.parent = null;

        while (!vertexSet.isEmpty()) {
            Vertex u = getMinPathVertex(vertexSet);
            vertexSet.remove(u);
            int dist = -1;
            int x = 0;
            if (u != null) {
                for (Edge e : u.Adj) {
                    Vertex v = e.otherEnd(u);
                    x = v.distance;
                    int alt = u.distance + e.weight;
                    if (alt < v.distance) {
                        v.distance = alt;
                        v.parent = u;
                        dist = alt;
                    }
                }
                if (dist == -1)
                    dist = x;
            } else {
                vertexSet.clear();
            }

            System.out.println(u.name + " " + dist);
            totalPath += dist;

        }
    }

    private Vertex getMinPathVertex(Set<Vertex> remainingVertices) {
        long min = INF;
        Vertex minVertex = null;
        for (Vertex v : remainingVertices) {
            if (v.name != 0 && v.distance < min) {
                min = v.distance;
                minVertex = v;
            }
        }
        return minVertex;
    }


    @Override
    public void print() {
        System.out.println("Dij " + totalPath);
        System.out.println(src.name + " " + src.distance + " -");
        for (Vertex v : g) {
            if (v != src)
                if (v.name != 0 && v.distance < INF) {
                    System.out.println(v.name + " " + v.distance + " " + v.parent.name);
                } else {
                    System.out.println(v.name + " INF -");
                }
        }
    }
}

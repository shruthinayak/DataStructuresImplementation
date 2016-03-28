package lp3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by tejas on 3/19/2016.
 */
public class BFS implements ShortestPath {

    Graph g;
    Vertex src;
    int totalPath = 0;
    String name = "BFS";

    BFS(Graph g, Vertex src) {
        this.g = g;
        this.src = src;
    }


    @Override
    public void getShortestPath() {
        for (Vertex v : g) {
            v.distance = INF;
            v.parent = null;
        }
        Queue<Vertex> q = new LinkedList<>();
        q.add(src);
        src.parent = src;
        src.distance = 0;
        src.seen = true;
        while (!q.isEmpty()) {
            Vertex u = q.remove();
            for (Edge e : u.Adj) {
                if (!e.otherEnd(u).seen) {
                    Vertex v = e.otherEnd(u);
                    v.parent = u;
                    v.seen = true;
                    v.distance = u.distance + e.weight;
                    totalPath += v.distance;
                    q.add(v);
                }
            }
        }

    }

    @Override
    public int getTotalOfShortestPaths() {
        return totalPath;
    }

    @Override
    public String getName() {
        return name;
    }

}
package lp3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by tejas on 3/20/2016.
 */
public class BellmanFord implements ShortestPath {


    public boolean cycle = false;
    Graph g;
    int totalPath = 0;
    Vertex src;

    BellmanFord(Graph g, Vertex src) {
        this.g = g;
        this.src = src;
    }

    public boolean isCycle() {
        return cycle;
    }

    //    int INF=Integer.MAX_VALUE;
    @Override
    public void getShortestPath() {

//        Create a queue q to hold vertices waiting to be processed
        Queue<Vertex> q = new LinkedList<>();
        for (Vertex u : g.verts) {
            if (u != null) {
                u.distance = INF;
                u.parent = null;
                u.seen = false;
            }
        }
//            u.distance ← ∞; u.parent ← null; u.count ← 0; u.seen ← false

        src.distance = 0;
        src.seen = true;
        q.add(src);
        while (!q.isEmpty()) {
            Vertex u = q.remove();
            u.seen = false; // no longer in q
            u.count = u.count + 1;
            if (u.count >= g.numNodes) { // Negative cycle
                cycle = true;
                System.out.println("Unable to solve problem. Graph has a negative cycle");
                break;
            }
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                if (v.distance > u.distance + e.weight) {
                    if (v.distance != INF)
                        totalPath = totalPath - v.distance;
                    v.distance = u.distance + e.weight;
                    totalPath = totalPath + v.distance;
                    v.parent = u;
                    if (!v.seen) {
                        q.add(v);
                        v.seen = true;
                    }
                }
            }
        }
    }

    @Override
    public void print() {
        if (cycle) {
            System.out.println("Unable to solve problem. Graph has a negative cycle");
        }
        System.out.println("B-F " + totalPath);
        for (Vertex s : g.verts) {
            if (s.name != 0) {
                if (s != src) {
                    if (s.parent != null) {
                        System.out.println(s + " " + s.distance + " " + s.parent);
                    } else {
                        System.out.println(s + " INF -");
                    }
                } else
                    System.out.println(s + " " + s.distance + " -");
            }
        }
    }

    @Override
    public int getTotalOfShortestPaths() {
        if (cycle) return INF;
        return totalPath;
    }


}

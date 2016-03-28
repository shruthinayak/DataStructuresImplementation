package lp3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by tejas on 3/20/2016.
 */
public class BellmanFord implements ShortestPath {

    public boolean cycle = false;
    public int totalPath = 0;
    public String name = "B-F";
    Graph g;
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
    public int getTotalOfShortestPaths() {
        if (cycle) return INF;
        return totalPath;
    }

    @Override
    public String getName() {
        return name;
    }


}

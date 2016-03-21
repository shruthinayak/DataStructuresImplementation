package lp3;

import lp2.*;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by tejas on 3/19/2016.
 */
public class BFS implements ShortestPath {

    Graph g;
    Vertex src;
    int totalPath = 0;

    BFS(Graph g, Vertex src) {
        this.g = g;
        this.src = src;
    }


    @Override
    public void getShortestPath() {
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
    public void print() {
        System.out.println("DAG " + totalPath);
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

    @Override
    public int getTotalOfShortestPaths() {
        return totalPath;
    }
/*

    @Override
    public void print(Graph g, Vertex src) {
        System.out.println("BFS " + totalPath);
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

*/

}

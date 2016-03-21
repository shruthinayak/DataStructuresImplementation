package lp3;


import java.util.List;

public class DAGShortestPath implements ShortestPath {

    Graph g;
    Vertex src;
    int totalPath = 0;

    DAGShortestPath(Graph g, Vertex src) {
        this.g = g;
        this.src = src;
    }

    @Override
    public void getShortestPath() {
        /*for each vertex y in a topological ordering of G
        choose edge (x,y) minimizing d(s,x)+length(x,y)
        path(s,y) = path(s,x) + edge (x,y)
        d(s,y) = d(s,x) + length(x,y)*/
        List<Vertex> top = g.getTopologicalOrder();
        for (Vertex v : g) {
            v.distance = INF;
        }
        src.distance = 0;
        for (Vertex u : top) {
            if (u.distance != INF)
                for (Edge e : u.Adj) {
                    relax(u, e.otherEnd(u), e);
                }
        }
    }

    public void relax(Vertex u, Vertex v, Edge e) {
        if (v.distance > u.distance + e.weight) {
            if (v.distance != INF)
                totalPath -= v.distance;
            v.distance = u.distance + e.weight;
            v.parent = u;
            totalPath += v.distance;
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


}

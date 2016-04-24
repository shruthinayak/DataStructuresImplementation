package lp5;

import java.util.LinkedList;
import java.util.Queue;

public class BipartiteMatching {
    //bfs declare outer node inner nodes
    int msize = 0;

    public boolean checkBipartite(Graph g) {
        Vertex u = g.verts.get(1);
        Queue<Vertex> q = new LinkedList<>();
        u.isOuter = true;
        u.seen = true;
        q.add(u);
        while (!q.isEmpty()) {
            u = q.poll();
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                if (!v.seen) {
                    v.seen = true;
                    v.isOuter = !u.isOuter;
                    q.add(v);
                } else if (v.isOuter != u.isOuter) {
                    continue;
                } else {
                    return false;
                }
            }

        }
        return true;
    }

    public int maximumMatching(Graph g) {
        msize = initializeGraph(g);
        /*for (Vertex v : g.verts) {
            if (v != null && v.mate == null) {
                for (Edge e : v.Adj) {
                    if (e.otherEnd(v).mate == null) {
                        v.mate = e.otherEnd(v);
                        e.otherEnd(v).mate = v;
                        msize++;
                        break;

                    }
                }
            }

        }*/
        for (Edge e : g.edges) {
            Vertex v = e.To;
            Vertex u = e.From;
            if (v.mate == null && u.mate == null) {
                v.mate = u;
                u.mate = v;
                msize++;
            }
        }
        // Repeat: find augmenting path, increase size of matching
        while (true) {
            boolean flag = false;
            Queue<Vertex> Q = new LinkedList<>();//Create an empty queue Q of vertices // for outer nodes only
            for (Vertex u : g.verts) {
                if (u != null) {
                    u.seen = false;
                    u.parent = null;
                    if (u.mate == null && u.isOuter) {
                        u.seen = true;
                        Q.add(u);
                    }
                }
            }
            outerloop:
            while (!Q.isEmpty()) {
                Vertex u = Q.poll();
                for (Edge edge : u.Adj) {
                    Vertex v = edge.otherEnd(u);
                    if (!v.seen) {
                        v.parent = u;
                        v.seen = true;
                        if (v.mate == null) {//then // augmenting path has been found
//                            flag=true;
                            processAugPath(v);
                            break outerloop;//go to the beginning of Step 4
//                            break ;//go to the beginning of Step 4
                        } else {
                            Vertex x = v.mate;
                            x.seen = true;
                            x.parent = v;
                            Q.add(x);
                        }
                    }
                }
                // Q got empty, but no augmenting path was found
//                    Break out of the outer while loop of Step 4 and go to Step 5

            }
            if (Q.isEmpty() /*&& !flag*/) {
//                break;
                return msize;
            }
        }
    }

    private void processAugPath(Vertex u) {
        // u is a free inner node with an augmenting path to the root of the tree
        Vertex p = u.parent;
        Vertex x = p.parent;
        u.mate = p;
        p.mate = u;
        while (x != null) {// go up two steps from outer node x
            Vertex nmx = x.parent;
            Vertex y = nmx.parent;
            x.mate = nmx;
            nmx.mate = x;
            x = y;
        }
        msize++; // Matching size increases by 1
        return;
    }

    private int initializeGraph(Graph g) {
        int msize = 0;
        for (Vertex v : g.verts) {
            if (v != null) v.mate = null;
        }
        return msize;
    }
}


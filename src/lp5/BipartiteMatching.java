package lp5;

/**
 * G21:
 * Shruthi Ramesh, Nayak: sxn145130
 * Tejasvee Bolisetty: txb140830
 */

import java.util.*;


public class BipartiteMatching {
    //bfs declare outer node inner nodes
    int msize = 0;

    ArrayList<Vertex> processedVertices = new ArrayList<>(); //consists of all the processed vertices while Q is not empty

    /**
     * checks whether graph is bipartite
     *
     * @param g Graph
     * @return true if bipartite otherwise false
     */
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

    /**
     * finds the maximum matching if the graph is bipartite
     *
     * @param g graph
     * @return matching size
     */
    public int maximumMatching(Graph g) {
        msize = initializeGraph(g);
        for (Vertex v : g.verts) {
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
        }

        int i = 0;
        // Repeat: find augmenting path, increase size of matching
        while (true) {
            processedVertices.clear();
            boolean flag = false;
            Queue<Vertex> Q = new LinkedList<>();//Create an empty queue Q of vertices // for outer nodes only
            for (Vertex u : g.verts) {
                if (u != null) {
                    u.seen = false;
                    u.parent = null;
                    u.processed = false;
                    if (u.mate == null && u.isOuter) {
                        u.seen = true;
                        Q.add(u);
                    }
                }
            }
//            System.out.println("iteration " + i++);
            outerloop:
            while (!Q.isEmpty()) {
                Vertex u = Q.poll();
                for (Edge edge : u.Adj) {
                    Vertex v = edge.otherEnd(u);
                    if (!v.seen) {
                        v.parent = u;
                        v.seen = true;
                        if (v.mate == null) {//then // augmenting path has been found
                            if (v.processed) { //if the vertex is already utilized for finding matching in this loop then break
                                break;
                            }
                            flag = true;
                            v.processed = true;
                            processAugPath(v);
                            break;//go to the beginning of Step 4
                        } else {
                            Vertex x = v.mate;
                            x.seen = true;
                            x.parent = v;
                            Q.add(x);
                        }
                    }
                }
                // Q got empty, but no augmenting path was found Break out of the outer while loop of Step 4 and go to Step 5
            }
            if (Q.isEmpty() && !flag) {
                return msize;
            }
        }
    }

    /**
     * checks and marks if new augmented path is valid
     *
     * @param u
     */
    private void processAugPath(Vertex u) {
        // u is a free inner node with an augmenting path to the root of the tree
        Vertex temp = u;
        while (temp != null) {
            if (processedVertices.contains(temp)) {
                return;
            }
            temp = temp.parent;
        }
        processedVertices.add(u);
        Vertex p = u.parent;
        Vertex x = p.parent;
        processedVertices.add(p);
        processedVertices.add(x);
        u.mate = p;
        p.mate = u;
        while (x != null) {// go up two steps from outer node x
            Vertex nmx = x.parent;
            Vertex y = nmx.parent;
            x.mate = nmx;
            nmx.mate = x;
            x = y;
            processedVertices.add(nmx);
            processedVertices.add(y);
        }
        msize++; // Matching size increases by 1
    }

    /**
     * initial setup for the graph
     *
     * @param g
     * @return
     */
    private int initializeGraph(Graph g) {
        int msize = 0;
        for (Vertex v : g.verts) {
            if (v != null) v.mate = null;
        }
        return msize;
    }

    public void resetSeen(Graph g) {
        for (Vertex v : g.verts) {
            if (v != null) v.seen = false;
        }
    }
}


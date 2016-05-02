package lp5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MaximumWeightedMatching {
    Graph g;
    ArrayList<Vertex> processedVertices = new ArrayList<>(); //consists of all the processed vertices while Q is not empty
    BipartiteMatching bm;
    int msize = 0;
    int minSlack = Integer.MAX_VALUE;
    List<Vertex> outerNode = new ArrayList<>();
    List<Vertex> innerNode = new ArrayList<>();

    /**
     * 1. compute outer and inner nodes
     * 2. set labels for outer(u) and inner (v) nodes as :
     * L(u) = max(w(u,v)) over (u,v) in u.Adj
     * L(v) = 0
     * 3. compute zero graph as :
     * for each edge (u,v) in G, if L(u) + L(v) = w(u,v), add (u,v) to Z
     * 4. find max matching M in Z
     * 5.  if M not perfect i.e. sum(W(e)) != L(u) where e <= M and u <=V
     * take a Tree T from Hungarian forest
     * for (u,v) in E with u=outer node in T, v=inner node not in T:
     * slack(u,v) = L(u) + L(v) − w(u,v)
     * Let Delta = min(slack(e)), over all such edges.
     * for each outer node u in T do L(u) −= Delta
     * for each inner node v in T do L(v) += Delta
     * 6. go to step 3.
     * 7. stop
     */

    MaximumWeightedMatching(Graph g) {
        this.g = g;
    }

    public int computeMaxWeightedMatching() {
        bm = new BipartiteMatching();
        //check whether graph is bipartite, if yes set outer and inner nodes
        if (bm.checkBipartite(g)) {
            //compute labels for outer and inner
            computeLabels(g);
            while (true) {
                computeZeroGraph(g);
                //find max cardinality matching in M
                resetMate(g);
                int maxMatching = maximumMatching(g);
                //check if sum(W(e)) == L(u) where e <= M and u <=V
                int maxWeigth = checkValidMatching(g);
                if ((maxWeigth == -1) && !(zeroLabel(g))) {
                    //find Tree T from Hungarian forest
                    calculateSlackForHungarian(g);
                    resetEdgeForZ(g);
                    continue;
                } else {
                    System.out.println("max matching is " + maxMatching);
                    return maxWeigth;
                }
            }
        }
        return 0;
    }

    public boolean zeroLabel(Graph g) {
        int count = 0;
        int totalOuter = 0;
        for (Vertex u : g.verts) {
            if (u != null && u.isOuter) {
                totalOuter++;
                if (u.label == 0) count++;

            }
        }
        if (totalOuter == count) return true;
        return false;
    }

    public void calculateSlackForHungarian(Graph g) {
        resetSeen(g);
        for (Vertex v : g.verts) {
            if (v != null && v.D && !v.seen) {
                // do dfs to find slack for this tree
                minSlack = Integer.MAX_VALUE;
                outerNode.clear();
                innerNode.clear();
                DFS(v);
                int minLabel = Integer.MAX_VALUE;
                if (outerNode.size() > innerNode.size()) {
                    for (Vertex u : outerNode) {
                        //check for min outer node value
                        if (u.label < minLabel) {
                            minLabel = u.label;
                        }
                        computeMinSlack(u);
                    }
                    // check for min slack
                    minSlack = minSlack > minLabel ? minLabel : minSlack;
                    for (Vertex u : outerNode) {
                        computeSlack(u);
                    }
                    for (Vertex u : innerNode) {
                        computeSlack(u);
                    }
                }
            }
        }
    }

    public void DFS(Vertex v) {
        v.seen = true;
        if (v.isOuter) {
            outerNode.add(v);
        } else {
            innerNode.add(v);
        }
        for (Edge e : v.Adj) {
            if (e.D) {
                Vertex u = e.otherEnd(v);
                if (!u.seen) {
                    DFS(u);
                }
            }
        }
    }

    public void computeMinSlack(Vertex u) {
        for (Edge e : u.Adj) {
            Vertex v = e.otherEnd(u);
            if (u.isOuter && !e.D) {
                int slack = v.label + u.label - e.weight;
                if (minSlack > slack) {
                    minSlack = slack;
                }
            }
        }
    }

    public void computeSlack(Vertex u) {
        if (u.isOuter) {
            u.label = u.label - minSlack;
        } else {
            u.label += minSlack;
        }
    }

    private int checkValidMatching(Graph g) {
        //sum(W(e)) != L(u) where e = M and u =V
        int sumEdge = 0;
        int sumVertex = 0;
//        resetSeen(g);
        resetSeenEdges(g);
        for (Vertex v : g.verts) {
            /*if(v.seen){
                continue;
            }*/
            if (v == null) continue;
            if (v.mate != null) {
                for (Edge e : v.Adj) {
                    if ((e.To == v.mate || e.From == v.mate) && !e.seen) {
                        System.out.println(e.toString());
                        e.seen = true;
                        sumEdge += e.weight;
                    }
                /*if (e.D && !e.seen) {
                    System.out.println(e.toString());
                    e.seen=true;
                    sumEdge += e.weight;
                }*/
                }
            }
            sumVertex += v.label;
        }
        if (sumEdge != sumVertex) return -1;
        else return sumEdge;


    }

    public void resetSeenEdges(Graph g) {
        for (Vertex v : g.verts) {
            if (v != null) {
                for (Edge e : v.Adj) {
                    e.seen = false;
                }
            }
        }
    }

    private void computeZeroGraph(Graph g) {
        // for each edge (u,v) in G, if L(u) + L(v) = w(u,v), add (u,v) to Z
        //resetSeenEdges(g);
        for (Vertex v : g.verts) {
            if (v != null) {
                for (Edge e : v.Adj) {
                    if (v.label + e.otherEnd(v).label == e.weight) {
                        e.D = true;
                        v.D = true;
                        e.otherEnd(v).D = true;
                    }
                }
            }
        }
    }

    public void resetEdgeForZ(Graph g) {
        for (Vertex v : g.verts) {
            if (v != null) {
                for (Edge e : v.Adj) {
                    e.D = false;
                }
            }
        }
    }

    public void computeLabels(Graph g) {
        for (Vertex v : g.verts) {
            int maxW = 0;
            if (v != null) {
                if (v.isOuter) {
                    for (Edge e : v.Adj) {
                        if (maxW < e.weight) {
                            maxW = e.weight;
                        }
                    }
                }
                v.label = maxW;
            }
        }
    }


    public int maximumMatching(Graph g) {
        msize = bm.initializeGraph(g);
        for (Vertex v : g.verts) {
            if (v != null && v.mate == null) {
                for (Edge e : v.Adj) {
                    if (e.D && e.otherEnd(v).mate == null) {
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
                    if (u.mate == null && u.isOuter && u.label > 0) {
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
                    if (!edge.D) {
                        continue;
                    } else {
                        Vertex v = edge.otherEnd(u);
                        if (!v.seen || u.label == 0) {
                            if (u.label != 0) {
                                v.parent = u;
                                v.seen = true;
                            }
                            if ((v.mate == null) || (u.label == 0)) {//then // augmenting path has been found
                                if (v.processed || u.processed) { //if the vertex is already utilized for finding matching in this loop then break
                                    break;
                                }
                                flag = true;
                                if (u.label == 0) {
                                    u.processed = true;
                                    //TODO: exchange the paths instead of calling the process Aug path
                                    exchangeAugPath(u);
                                } else {
                                    v.processed = true;
                                    bm.processAugPath(v);
                                }
                                break;//go to the beginning of Step 4
                            } else {
                                Vertex x = v.mate;
                                x.seen = true;
                                x.parent = v;
                                Q.add(x);
                            }
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

    //TODO: have to complete
    private void exchangeAugPath(Vertex f) {
        Vertex s = f.parent;
        Vertex t = s.parent;
        f.mate = null;
        s.mate = t;
        t.mate = s;
        f = t;
        while (f != null && f.parent != null) {
            s = f.parent;
            t = s.parent;
            s.mate = t;
            f = t;
        }
    }

    public void resetMate(Graph g) {
        for (Vertex v : g.verts) {
            if (v != null) v.mate = null;
        }
    }

    public void resetSeen(Graph g) {
        for (Vertex v : g.verts) {
            if (v != null) v.seen = false;
        }
    }


}

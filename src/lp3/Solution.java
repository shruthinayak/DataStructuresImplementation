package lp3;

/**
 * Created by shruthi on 19/3/16.
 */
public class Solution {
    public static void main(String[] args) {
        Graph g = Graph.getGraph("/home/shruthi/AllFiles/OneDrive/Sem4/Impl/lp3-data/lp3-l1/lp3-l1-in2.txt", false);
        ShortestPath sp = new Dijkstra(g, g.getRoot(1));
        sp.getShortestPath();
        sp.print();
    }
}

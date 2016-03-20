package lp3;

/**
 * Created by shruthi on 19/3/16.
 */
public interface ShortestPath {

    void getShortestPath(Graph g, Vertex src);

    void print();

    class ShortestPathObj {
        Vertex n;
        Vertex predecessor;
        long pathLength;

        public String toString() {
            return n.name + " " + predecessor.name + " " + pathLength;
        }
    }
}

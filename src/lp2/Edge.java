package lp2;

/**
 * Class that represents an arc in a Graph
 */
public class Edge {
    public boolean seen;
    public Vertex From; // tail vertex
    public Vertex To; // head vertex
    public int weight;// weight of the arc
    public int component; // which component the edge belongs to of the vertex
    public int actualWeight; //unmodified original weight

    /**
     * Constructor for Edge
     *
     * @param u : Vertex - The tail of the arc
     * @param v : Vertex - The head of the arc
     * @param w : int - The weight associated with the arc
     */
    Edge(Vertex u, Vertex v, int w) {
        seen = false;
        From = u;
        To = v;
        weight = w;
        component = -1;
        actualWeight = w;
    }

    /**
     * Method to find the other end end of the arc given a vertex reference
     *
     * @param u : Vertex
     * @return : Vertex at the other end of the edge.
     */
    public Vertex otherEnd(Vertex u) {
        // if the vertex u is the head of the arc, then return the tail else
        // return the head
        if (From == u) {
            return To;
        } else {
            return From;
        }
    }

    /**
     * Method to represent the edge in the form (x,y) where x is the head of the
     * arc and y is the tail of the arc
     */
    public String toString() {
        return "(" + From + "," + To + ")";
    }
}

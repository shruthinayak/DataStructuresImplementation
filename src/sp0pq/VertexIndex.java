package sp0pq;

import sp2.Vertex;

import java.util.Comparator;

public class VertexIndex implements Index<Vertex>, Comparator<Vertex> {

    public void putIndex(Vertex e, int index) {
        e.index = index;
    }

    public int getIndex(Vertex e) {
        return e.index;
    }

    @Override
    public int compare(Vertex o1, Vertex o2) {
        return o1.distance - o2.distance;
    }
}

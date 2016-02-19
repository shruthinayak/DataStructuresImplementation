package lp0;

import sp2.Edge;

public class Node {
    public Edge edge;
    public Node next;
    public int size;

    Node(Edge edge) {
        this.edge = edge;
    }

    Node() {

    }

    void print() {
        Node t = this;
        while (t != null) {
            System.out.println(t.edge.toString());
            t = t.next;
        }
    }
}

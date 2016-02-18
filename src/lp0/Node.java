package lp0;

import sp2.Edge;

/**
 * Created by shruthi on 17/2/16.
 */
public class Node {
    Edge edge;
    Node next;
    int size;

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

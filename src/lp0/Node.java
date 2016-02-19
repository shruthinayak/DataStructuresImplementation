package lp0;

import sp2.Edge;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shruthi on 17/2/16.
 */
public class Node extends LinkedList {
    public Edge edge;
    public Node next;
    public int size;

    Node(Edge edge) {
        this.edge = edge;
    }

    Node() {
        size = 0;

    }


    //    @Override
    public String toString() {
        return edge.toString();
    }
    void print() {
        Node t = this;
        while (t != null) {
            System.out.println(t.edge.toString());
            t = t.next;
        }
    }
}

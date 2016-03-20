package lp3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tejas on 3/19/2016.
 */
public class BFS implements ShortestPath {

    List<ShortestPathObj> output = new ArrayList<>();
    Vertex src;

    @Override
    public void getShortestPath(Graph g, Vertex src) {

    }

    @Override
    public void print() {
        System.out.println("BFS");
        for (ShortestPathObj s : output) {
            System.out.println(s.toString());
        }
    }


}

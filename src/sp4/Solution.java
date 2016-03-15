package sp4;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shruthi on 15/3/16.
 */
public class Solution<T> {
    public static void main(String[] args) {
        Comparable[] a = new Comparable[]{68, 94, 47, 52, 23, 64, 87, 97, 12, 93,
                32, 44, 34, 53, 58, 95, 70};
        //BST tree = new BST();
        Arrays.sort(a);
        List<Comparable> l = new LinkedList<>();
        for (Comparable i : a) {
            l.add(i);
        }
        BST tree = new BST(a);

        tree.printTree();
        System.out.println(tree.root.element);
        Comparable[] levelOrder = tree.levelOrderArray();
        for (Comparable i : levelOrder)
            System.out.print(i + " ");
    }
}

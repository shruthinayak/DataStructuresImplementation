package sp4;

import common.Timer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Tree {
    static final long p = 999959;
    TreeNode root;

    Tree() {
        root = new TreeNode(0);
    }

    // If there is a command line argument, it is used as the depth of the tree generated
    public static void main(String[] args) {
        long depth = 100;
        Scanner read = new Scanner(System.in);
        if (args.length > 0) depth = Long.parseLong(args[0]);
        else depth = read.nextInt();
        Tree x = new Tree();

        // Create a tree composed of 2 long paths
        TreeNode last = x.root;
        for (long i = 1; i <= depth; i++) {
            last = x.new TreeNode(i, last, true);
        }

        last = x.root;
        for (long i = 1; i <= depth; i++) {
            last = x.new TreeNode(depth + i, last, false);
        }

        Timer timer = new Timer();
        // The tree is visited in level order, using a Queue.  Depth and height of each node is computed recursively
        long minSum = Long.MAX_VALUE;
        Queue<TreeNode> Q = new LinkedList<>();
        Q.add(x.root);
        while (!Q.isEmpty()) {
            TreeNode cur = Q.remove();
            if (cur != null) {
                minSum = Math.min(minSum, someFunction(cur));
                Q.add(cur.left);
                Q.add(cur.right);
            }
        }
        System.out.println("Answer: " + minSum + " ");
    }

    static long someFunction(TreeNode cur) {
        return rotater(rotater(depth(cur) * depth(cur)) % p + rotater(height(cur) * height(cur)) % p);
    }

    static long rotater(long h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        h = (h ^ (h >>> 7) ^ (h >>> 4));
        return h;
    }

    // find the depth of a node
    static long depth(TreeNode t) {
        return t.parent == null ? 0 : 1 + depth(t.parent);
    }

    // height of a node
    static long height(TreeNode t) {
        return t == null ? -1 : 1 + Math.max(height(t.left), height(t.right));
    }

    /**
     * binary tree node
     */
    public class TreeNode {
        long data;
        TreeNode left, right, parent;

        TreeNode(long x) {
            data = x;
            left = null;
            right = null;
            parent = null;
        }

        /**
         * create a new node that is attached to par node as left child if goLeft is true;
         * otherwise, the new node is attached to par as right child
         */
        TreeNode(long x, TreeNode par, boolean goLeft) {
            data = x;
            left = null;
            right = null;
            parent = par;
            if (goLeft) {
                par.left = this;
            } else {
                par.right = this;
            }
        }
    } // end of TreeNode class

}
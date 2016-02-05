package sp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author shruthi
 * @author tejasvee
 * 
 */
public class Solution {

	public static void main(String[] args) {
		Graph graph = null;
		try {
			graph = Graph.createGraphFromUserInput(new Scanner(new File(
					"edges.txt")), true);
		} catch (FileNotFoundException e) {
			System.out.println("edges.txt file not found.");
		}
		if (graph != null) {
			topologicalOrder(graph);
			stronglyConnected(graph);
			diameter(graph);
			oddLengthCycle(graph);
			testEulerian(graph);
		}

	}

	private static void topologicalOrder(Graph graph) {
		List<Vertex> topOrder = toplogicalOrder1(graph);
		if (topOrder.size() != graph.verts.size() - 1) {
			System.out.println("Topo 1: Not a DAG!");
		} else {
			System.out.println("Topo 1: " + topOrder.toString());
		}

		Stack<Vertex> output = toplogicalOrder2(graph);
		if (output.size() != graph.verts.size() - 1) {
			System.out.println("Topo 2: Not a DAG!");
		} else {
			System.out.println("Topo 2: ");
			for (int i = output.size() - 1; i >= 0; i--) {
				System.out.print(output.get(i) + " ");
			}
		}

	}

	/**
	 * Method returns the topological order of the vertices in the directed
	 * graph.
	 * 
	 * @param g
	 *            Directed Graph
	 * @return List that contains the topological order
	 */
	public static List<Vertex> toplogicalOrder1(Graph g) {

		resetSeen(g);
		List<Vertex> topoOrder = new ArrayList<>();
		Queue<Vertex> queue = new LinkedList<>();

		for (Vertex v : g.verts) {
			if (v.degree == 0) {
				queue.add(v);
				break;
			}
		}
		while (queue.size() != 0) {
			Vertex u = queue.remove();
			topoOrder.add(u);
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				v.degree--;
				if (v.degree == 0) {
					queue.add(v);
				}
			}
		}
		return topoOrder;
	}

	/**
	 * Method uses DFS on the directed graph to find the topological order.
	 * 
	 * @param g
	 *            Directed graph
	 * @return Stack that contains the topological order in the reverse order
	 */
	public static Stack<Vertex> toplogicalOrder2(Graph g) {
		/*
		 * Algorithm 2. Run DFS on g and push nodes to a stack in the order in
		 * which they finish. Write code without using global variables.
		 */
		resetSeen(g);
		Stack<Vertex> s = new Stack<>();
		Stack<Vertex> o = new Stack<>();
		List<Vertex> vs = g.verts;
		for (Vertex v : vs) {
			if (!v.seen) {
				DFS(v, s, o, false);
			}
		}
		return o;
	}

	/**
	 * Method returns the number of strongly connected components for a directed
	 * graph.
	 * 
	 * @param g
	 *            Directed graph
	 * @return number of strongly connected components
	 */
	public static int stronglyConnected(Graph g) {
		resetSeen(g);
		Stack<Vertex> output = toplogicalOrder2(g);
		System.out.println();
		Collections.reverse(output);
		Stack<Vertex> s = new Stack<>();
		Stack<Vertex> o = new Stack<>();
		int conn = 0;
		resetSeen(g);

		for (Vertex v : output) {
			o.clear();
			if (!v.seen) {
				DFS(v, s, o, true);
				conn++;
				System.out.println("Component " + conn + ":" + o.toString());
			}
		}
		return conn;
	}

	/**
	 * @param v
	 *            : Vertex - DFS root vertex
	 * @param s
	 *            : Stack<Vertex> - stack containing the vertices yet to be processed
	 * @param output
	 *            : Stack<Vertex> - stack containing the vertices in the order as they finish
	 * @param useReverse
	 *            : boolean - that determines if the reverse edges has to be used.
	 */
	private static void DFS(Vertex v, Stack<Vertex> s, Stack<Vertex> output,
			boolean useReverse) {
		s.push(v);
		v.seen = true;

		List<Edge> edgeSet = useReverse ? v.revAdj : v.Adj;

		for (Edge e : edgeSet) {
			Vertex u = e.otherEnd(v);
			if (!u.seen) {
				DFS(u, s, output, useReverse);
			}
		}
		Vertex u = s.pop();
		u.seen = true;
		if (u.name != 0)
			output.push(u);

	}

	public static int diameter(Graph t) {
		resetSeen(t);
		Queue<Vertex> verQueue = new LinkedList<Vertex>();
		if (t.verts.isEmpty()) {
			return -1;
		}
		Random random = new Random();
		int num = random.nextInt(t.numNodes - 1);
		Vertex randRoot = t.verts.get(num + 1);
		System.out.println("Random Root is " + randRoot);
		randRoot.distance = 0;
		randRoot.seen = true;
		verQueue.add(randRoot);
		Vertex newRoot = null;
		int result = BFS(t, verQueue);
		if (result == -1) {
			System.out.println("Not a tree.");
			return -1;
		}

		int dia = 0;
		for (int i = 1; i <= t.numNodes; i++) {
			Vertex v = t.verts.get(i);
			if (dia <= v.distance) {
				newRoot = v;
				dia = v.distance;
			}
		}
		System.out.println("New Root is " + newRoot + ": Distance is "
				+ newRoot.distance);

		verQueue.clear();
		resetSeen(t);

		newRoot.distance = 0;
		newRoot.seen = true;
		verQueue.add(newRoot);
		dia = 0;
		result = BFS(t, verQueue);

		for (int i = 1; i <= t.numNodes; i++) {
			Vertex v = t.verts.get(i);
			if (dia <= v.distance) {
				dia = v.distance;
			}
		}
		System.out.println("The diameter of the tree is: " + dia);
		return dia;
	}

	public static List<Vertex> oddLengthCycle(Graph g) {
		ArrayList<Vertex> cycle = new ArrayList<Vertex>();
		List<Vertex> roots = g.getRoots();
		Queue<Vertex> vertQueue = new LinkedList<Vertex>();
		for (Vertex root : roots) {
			vertQueue.add(root);
			while (!vertQueue.isEmpty()) {
				Vertex u = vertQueue.remove();

				u.seen = true;
				for (Edge e : u.Adj) {
					Vertex v = e.otherEnd(u);
					if (!v.seen) {
						v.distance = u.distance + 1;
						v.parent = u;
						v.seen = true;
						vertQueue.add(v);

					} else if (u.distance == v.distance) {
						if (!cycle.contains(u)) {
							cycle.add(u);
						}
						if (!cycle.contains(v)) {
							cycle.add(v);
						}
						while (!u.equals(v)) {
							if (!cycle.contains(u)) {
								cycle.add(u);
							}
							if (!cycle.contains(v)) {
								cycle.add(v);
							}

							if (u.parent != null) {
								u = u.parent;
							}
							if (v.parent != null) {
								v = v.parent;
							}
						}
						if (!cycle.contains(u)) {
							cycle.add(u);
						}

					}
				}
			}
		}
		System.out.println(cycle.toString());
		return cycle;

	}

	public static int BFS(Graph g, Queue<Vertex> verQueue) {
		while (!verQueue.isEmpty()) {
			Vertex u = verQueue.remove();
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				if (!v.seen) {
					v.parent = u;
					v.distance = u.distance + 1;
					v.seen = true;
					verQueue.add(v);
				} else if (v != u.parent) {
					return -1;
				}

			}
		}
		return 0;
	}

	public static void testEulerian(Graph g) {

		ArrayList<Vertex> evenVertex = new ArrayList<Vertex>();
		ArrayList<Vertex> oddVertex = new ArrayList<Vertex>();
		for (Vertex v : g.verts) {
			if (v != null) {
				if (checkDegree(v)) {
					evenVertex.add(v);
				} else {
					oddVertex.add(v);
				}
			}
		}
		if (checkConnected(g)) {
			System.out.println("Graph is connected");
			if (evenVertex.size() == g.numNodes) {
				System.out.println("Graph is Eulerian");
			} else if (oddVertex.size() == 2) {
				// eulerian path exits
				System.out
						.println("Graph has an Eulerian Path between vertices "
								+ oddVertex.get(0) + " and " + oddVertex.get(1));
			} else {
				// not eulerian
				System.out.println("Graph is not Eulerian. It has "
						+ oddVertex.size() + " vertices of odd degree");
			}

		} else {
			System.out.println("Graph is not connected");
		}

	}

	public static void resetSeen(Graph g) {
		for (Vertex v : g.verts) {
			v.seen = false;
		}
	}

	public static boolean checkConnected(Graph g) {
		return toplogicalOrder2(g).size() == g.numNodes;
	}

	public static boolean checkDegree(Vertex v) {
		return v.degree % 2 == 0;
	}
}

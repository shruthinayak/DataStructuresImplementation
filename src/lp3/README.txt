G21: 
Shruthi Ramesh, Nayak: sxn145130
Tejasvee Bolisetty: txb140830

Running time:

Design of data structures:
	ShortestPath - interface: 
		- getShortestPath() : Function that returns shortest path. 
		- getTotalOfShortestPaths(): Function that returns the sum of all the shortest paths from src to each vertex in the graph.
		- print() : Algorithm specific printing.
	Vertex  
		- distance - stores the shortest path distance from source to itself.
		- parent - the predecessor in its shortest path
		- tsp - stores the total number of shortest paths from a src to itself.
	Graph 
		- boolean positive: set to false if any one of the edge is negative
		- boolean uniform: set to false if the weight of an edge is not uniform.
		- List<Vertex> topologicalOrder: Singleton instance to keep track of topological order of the graph as it is required multiple times during computation of shortest path.
	Edge
		- boolean D: set to True if it is sub-path of any shortest path. (For Level 2)
	
		
		
ABSTRACT: 

PROBLEM STATEMENT: 
LEVEL 1: Implementation of shortest path algorithms: BFS, Dijkstra's algorithm, DAG shortest paths, and Bellman-Ford algorithm. Write a program that behaves as follows: given a directed graph as input, if the graph has uniform weights (i.e., same positive weights for all edges), then it runs BFS to find shorest paths. Otherwise, if the graph is a directed, acyclic graph (DAG), then it runs DAG shortest paths. Otherwise, if the graph has only nonnegative weights, then it runs Dijkstra's algorithm. If all these test fail, then it runs the Bellman-Ford algorithm. If the graph has negative cycles, then it prints the message "Unable to solve problem. Graph has a negative cycle".

METHODOLOGY:
Pseudocode 

LEVEL 1: 
	Let G be the graph. 
	if G.uniform then Run BFS and return shortest path. 
	else if G.positive then Run Dijkstra
	else if G has a topological ordering then Run DAG Shortest Path.
	else Run Bellman-Ford. If the totalofShortestPaths equals INF, then output "Negative cycle encountered"

LEVEL 2:
	For each edge e=(u,v)
		if v.distance == u.distance+e.weight
			set e.D to True
	src.tsp = 1
	for each Vertex v in Topological ordering taken in that order
		for each e=(u,v) in revAdj
			if e.D is True
				u.tsp = u.tsp + v.tsp
		
	
DEVELOPMENT PLATFORM

  version: Intel(R) Core(TM) i3-2310M CPU @ 2.10GHz
  slot: CPU 1
  size: 800MHz
  capacity: 2100MHz
  width: 64 bits
  clock: 100MHz

TEST RESULTS
LEVEL 1: 

java Solution < lp3-l1-in2.txt

Dij 384
1 0 -
2 12 6
3 10 28
4 2 1
5 17 25
6 10 28
7 21 14
8 14 13
9 13 10
10 11 4
11 3 4
12 9 1
13 11 3
14 19 12
15 10 1
16 18 15
17 24 16
18 22 20
19 25 16
20 12 12
21 18 9
22 14 3
23 14 24
24 10 25
25 7 11
26 19 30
27 12 25
28 9 11
29 INF -
30 18 22


LEVEL 2: 
java Solution < lp3-l2-in2.txt

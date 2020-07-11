package io.nms.central.microservice.topology.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PathComputation {

	public static Map<Integer, Stack<Integer>> dijkstra(int src, Map<Integer, List<NextHop>> network) {
		
		
		// map node IDs from 0
		List<Integer> nodesMap = new ArrayList<Integer>();
		for (HashMap.Entry<Integer, List<NextHop>> entry : network.entrySet()) {
			nodesMap.add(entry.getKey());
		}
		int mapSrc = nodesMap.indexOf(src);
		
		Map<Integer, Stack<Integer>> sptMap = new HashMap<Integer, Stack<Integer>>();

		int NO_PARENT = -1;
		// build the adjacency matrix  
		int N = network.size();
		int[][] adj = new int[N][N];
		// init zeros
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				adj[i][j] = 0;
			}
		}
		int i = 0;
		for (HashMap.Entry<Integer, List<NextHop>> entry : network.entrySet()) {
			List<NextHop> successors = entry.getValue();
			for (NextHop succ : successors) {
				int j = succ.getId();
				adj[i][nodesMap.indexOf(j)] = 1;
			}
			i++;
		}

		int numVertices = adj[0].length;
		int[] sd = new int[numVertices];

		boolean[] added = new boolean[numVertices];
		for (int vi = 0; vi < numVertices; vi++) {
			sd[vi] = Integer.MAX_VALUE;
			added[vi] = false;
		}
		sd[mapSrc] = 0;
		int[] parents = new int[numVertices];
		parents[mapSrc] = NO_PARENT;
		for (int ii = 1; ii < numVertices; ii++) {
			int nearestVertex = -1;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vi = 0; vi < numVertices; vi++) {
				if (!added[vi]
						&& sd[vi] < shortestDistance) {
					nearestVertex = vi;
					shortestDistance = sd[vi];
				}
			}
			added[nearestVertex] = true;
			for (int vi = 0; vi < numVertices; vi++) {
				int edgeDistance = adj[nearestVertex][vi];
				if (edgeDistance > 0
						&& ((shortestDistance + edgeDistance)
								< sd[vi])) {
					parents[vi] = nearestVertex;
					sd[vi] = shortestDistance + edgeDistance;
				}
			}
		}

		for (int vi = 0; vi < sd.length; vi++) {
			if (vi != mapSrc) {
				Stack<Integer> ps = new Stack<>();
				computePath(vi, parents, ps);
				sptMap.put(vi, ps);
			}
		}
		
		// put correct IDs
		Map<Integer, Stack<Integer>> vShPaths = new HashMap<Integer, Stack<Integer>>();
		for (HashMap.Entry<Integer, Stack<Integer>> entry : sptMap.entrySet()) {
			Stack<Integer> vNextHops = new Stack<Integer>();
			 
			Stack<Integer> nextHops = entry.getValue();
			for (Integer e : nextHops) {
				vNextHops.push(nodesMap.get(e));
			}
			vShPaths.put(nodesMap.get(entry.getKey()), vNextHops);
		}

		return vShPaths;
	}

	public static void computePath(int currentVertex, int[] parents, Stack<Integer> ps) { 
		if (currentVertex == -1) {
			return;
		}
		computePath(parents[currentVertex], parents, ps);
		ps.push(currentVertex);
	}

	public static String printIntegerPaths(Map<Integer, Stack<Integer>> paths) {
		String result = "";
		for (HashMap.Entry<Integer, Stack<Integer>> entry : paths.entrySet()) {
			Integer dst = entry.getKey();
			result+=" From " + dst + ": ";
			Stack<Integer> successors = entry.getValue(); 
			while(!successors.isEmpty()) {
				result+="  "+successors.pop();
			}
			result+="\n";
		}
		return result;
	}

}

package io.nms.central.microservice.topology.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBuilder {

	private Map<Integer, List<NextHop>> graph;

	public GraphBuilder() {
		graph = new HashMap<Integer, List<NextHop>>();
	}
	
	public Map<Integer, List<NextHop>> getGraph() {
		return graph;
	}

	public void setGraph(Map<Integer, List<NextHop>> graph) {
		this.graph = graph;
	}

	public void addSuccessor(int node, int successor, int tp, int cost) {
		List<NextHop> succ = graph.get(node);
		if(succ == null){
			succ  = new ArrayList<NextHop>();
			graph.put(node, succ);
		}
		succ.add(new NextHop(successor, tp, cost));
	}
	
	public int getFace(int nodeId, int nextHopId) {
		List<NextHop> nhs = graph.get(nodeId);
		for (NextHop h : nhs) {
			if (h.getId() == nextHopId) {
				return h.getFace();
			}
		}
		return 0;
	}

	public String printGraph() {
		String result = "";
		for (HashMap.Entry<Integer, List<NextHop>> entry : graph.entrySet()) {
			Integer nodeIndex = entry.getKey();			
			result+=String.valueOf(nodeIndex) + " next-hops are: ";
			List<NextHop> successors = entry.getValue(); 
			for (NextHop s: successors){
				Integer id = s.getId();
				Integer face = s.getFace();
				Integer cost = s.getCost();
				result+=" [" +  id + " , " + face + " , " + cost + "]";
			}
			result+="\n";  
		}
		return result;
	}

}

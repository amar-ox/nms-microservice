package io.nms.central.microservice.topology.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import io.nms.central.microservice.topology.model.Edge;
import io.nms.central.microservice.topology.model.Node;
import io.nms.central.microservice.topology.model.PrefixAnn;
import io.nms.central.microservice.topology.model.Route;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class Routing {
	
	// private static final Logger logger = LoggerFactory.getLogger(Routing.class);

	public Routing () {}

	public Future<List<Route>> computeRoutes(List<Node> nodes, List<Edge> edges, List<PrefixAnn> pas) {
		Promise<List<Route>> promise = Promise.promise();
		
		GraphBuilder gb = new GraphBuilder();
		edges.forEach(edge -> {
			gb.addSuccessor(edge.getSrcNodeId(), edge.getDestNodeId(), edge.getSrcFaceId(), 0);
			gb.addSuccessor(edge.getDestNodeId(), edge.getSrcNodeId(), edge.getDestFaceId(), 0);
		});

		// generate correct Integer IDs for prefixAnns:
		List<Integer> nodeIds = new ArrayList<Integer>(gb.getGraph().keySet());
		int paId = Collections.max(nodeIds) + 1; 
		Map<Integer, PrefixAnn> paMap = new HashMap<Integer, PrefixAnn>();

		for(int i=0; i < pas.size(); i++) {
			PrefixAnn pa = pas.get(i);
			paMap.put(paId, pa);			
			gb.addSuccessor(pa.getOriginId(), paId, -1, 0);
			gb.addSuccessor(paId, pa.getOriginId(), -1, 0);
			paId+=1;
		}

		List<Route> routes = new ArrayList<Route>(); 
		// STEP 3: Compute shortest path from each node to each prefix
		for (HashMap.Entry<Integer, PrefixAnn> pa : paMap.entrySet()) {
			Integer target = pa.getKey();
			Map<Integer, Stack<Integer>> paths = PathComputation.dijkstra(target, gb.getGraph());
			Set<Integer> processedNodes = new HashSet<Integer>();
			
			for (HashMap.Entry<Integer, Stack<Integer>> entry : paths.entrySet()) {
				Stack<Integer> path = entry.getValue();
				if (!paMap.containsKey(path.peek())) {
					while(path.size() > 2) {						
						// STEP 4: set Routing Table Entry for each node on the path
						Integer thisNode = path.pop();
						if (!processedNodes.contains(thisNode)) {
							int nh = path.peek();
							Route r = new Route();
							r.setNodeId(thisNode);
							r.setPrefix(pa.getValue().getName());
							r.setPaId(pa.getValue().getId());
							r.setNextHopId(nh);
							r.setFaceId(gb.getFace(thisNode, nh));
							r.setCost(path.size()-1);
							r.setOrigin("autogen");
							routes.add(r);
							processedNodes.add(thisNode);
						}
					}
				}
			}
		}
		promise.complete(routes);
		return promise.future();
	}

}

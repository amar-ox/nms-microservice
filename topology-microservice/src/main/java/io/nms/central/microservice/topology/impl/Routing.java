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
	private static final int ROUTE_ORIGIN_AUTOGEN = 10; 

	public Routing () {}

	public Future<List<Route>> computeRoutes(List<Node> nodes, List<Edge> edges, List<PrefixAnn> pas) {
		Promise<List<Route>> promise = Promise.promise();
		
		if (edges.isEmpty()) {
			promise.complete(new ArrayList<Route>());
			return promise.future();
		}
		
		GraphBuilder gb = new GraphBuilder();
		edges.forEach(edge -> {
			gb.addSuccessor(edge.getSrcNodeId(), edge.getDestNodeId(), edge.getSrcFaceId(), 0);
			gb.addSuccessor(edge.getDestNodeId(), edge.getSrcNodeId(), edge.getDestFaceId(), 0);
		});

		// generate correct Integer IDs for prefixAnns:
		List<Integer> nodeIds = new ArrayList<Integer>(gb.getGraph().keySet());
		Map<Integer, PrefixAnn> paMap = new HashMap<Integer, PrefixAnn>();
		int paId = Collections.max(nodeIds) + 1;
		for(PrefixAnn pa : pas) {
			if (nodeIds.contains(pa.getOriginId())) {
				paMap.put(paId, pa);		
				gb.addSuccessor(pa.getOriginId(), paId, -1, 0);
				gb.addSuccessor(paId, pa.getOriginId(), -1, 0);
				paId+=1;
			}
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
							r.setPrefix(pa.getValue().getName());
							r.setNodeId(thisNode);
							r.setPaId(pa.getValue().getId());
							r.setNextHopId(nh);
							r.setFaceId(gb.getFace(thisNode, nh));
							r.setCost(path.size()-1);
							r.setOrigin(ROUTE_ORIGIN_AUTOGEN);
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
	
	/* public Future<List<Face>> computeFaces(JsonObject info) {
		Promise<List<Face>> promise = Promise.promise();		
		String sLtpPort = info.getString("sLtpPort");
		String dLtpPort = info.getString("dLtpPort");
		int sVctpId = info.getInteger("sVctpId", 0);
		int dVctpId = info.getInteger("dVctpId", 0);
		int vlcId = info.getInteger("vlinkConnId", 0);		
					
		if (sLtpPort == null || dLtpPort == null || sVctpId == 0 || dVctpId == 0 || vlcId == 0) {
			promise.fail("Missing info");
		} else {
			List<Face> faces = new ArrayList<Face>(2);
			Face face1 = new Face();
			face1.setLabel("autogen face");
			face1.setScheme(SchemeEnum.ether);
			face1.setVlinkConnId(vlcId);
			face1.setVctpId(sVctpId);						
			face1.setLocal(sLtpPort);
			face1.setRemote(dLtpPort);
			faces.add(face1);
					
			Face face2 = new Face();
			face2.setLabel("autogen face");
			face2.setScheme(SchemeEnum.ether);
			face2.setVlinkConnId(vlcId);
			face2.setVctpId(dVctpId);						
			face2.setLocal(dLtpPort);
			face2.setRemote(sLtpPort);
			faces.add(face2);
						
			promise.complete(faces);
		}
		return promise.future();
	} */
	
	/* public Future<List<Vctp>> computeCtps(String lcName, JsonObject info) {
		Promise<List<Vctp>> promise = Promise.promise();
		
		Integer sLtpId = info.getInteger("sLtpId", 0);
		Integer dLtpId = info.getInteger("dLtpId", 0);		
		String sLtpName = info.getString("sLtpName");
		String dLtpName = info.getString("dLtpName");
					
		if (sLtpName == null || dLtpName == null || sLtpId == 0 || dLtpId == 0) {
			promise.fail("Missing info");
		} else {
			List<Vctp> ctps = new ArrayList<Vctp>(2);
			
			String ctpNum = lcName.split("\\|")[1];
				
			Vctp ctp1 = new Vctp();
			ctp1.setName(sLtpName + "|" + ctpNum);
			ctp1.setLabel("autogen ctp");
			ctp1.setDescription("");
			ctp1.setVltpId(sLtpId);
			ctps.add(ctp1);
			
			Vctp ctp2 = new Vctp();
			ctp2.setName(dLtpName + "|" + ctpNum);
			ctp2.setLabel("autogen ctp");
			ctp2.setDescription("");
			ctp2.setVltpId(dLtpId);
			ctps.add(ctp2);
						
			promise.complete(ctps);
		}
		return promise.future();
	} */
}



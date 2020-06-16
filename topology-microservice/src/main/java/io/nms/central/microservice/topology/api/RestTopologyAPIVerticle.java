package io.nms.central.microservice.topology.api;

import io.nms.central.microservice.common.BaseMicroserviceVerticle;
import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.topology.TopologyService;
import io.nms.central.microservice.topology.model.ModelHelper;
import io.nms.central.microservice.topology.model.Vctp;
import io.nms.central.microservice.topology.model.Vlink;
import io.nms.central.microservice.topology.model.VlinkConn;
import io.nms.central.microservice.topology.model.Vltp;
import io.nms.central.microservice.topology.model.Vnode;
import io.nms.central.microservice.topology.model.Vsubnet;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * This verticle exposes a HTTP endpoint to process shopping products management with REST APIs.
 *
 * @author Eric Zhao
 */
public class RestTopologyAPIVerticle extends RestAPIVerticle {
	
	private static final Logger logger = LoggerFactory.getLogger(RestTopologyAPIVerticle.class);

	public static final String SERVICE_NAME = "topology-rest-api";

	private static final String API_VERSION = "/v";

	private static final String API_ADD_SUBNET = "/subnet";  
	private static final String API_GET_SUBNET = "/subnet/:subnetId";
	private static final String API_DELETE_SUBNET = "/subnet/:subnetId";
	private static final String API_GET_ALL_SUBNETS = "/subnets/all";

	private static final String API_ADD_NODE = "/node";
	private static final String API_GET_NODE = "/node/:nodeId";
	private static final String API_DELETE_NODE = "/node/:nodeId";
	private static final String API_GET_ALL_NODES = "/nodes/all";

	private static final String API_ADD_LTP = "/ltp";  
	private static final String API_GET_LTP = "/ltp/:ltpId";
	private static final String API_DELETE_LTP = "/ltp/:ltpId";
	// private static final String API_GET_ALL_LTPS = "/ltps/all";
	private static final String API_GET_LTPS_BY_NODE = "/ltps/node/:nodeId";

	private static final String API_ADD_CTP = "/ctp";  
	private static final String API_GET_CTP = "/ctp/:ctpId";
	private static final String API_DELETE_CTP = "/ctp/:ctpId";
	// private static final String API_GET_CTPS_BY_LTP = "/ctps/all";
	private static final String API_GET_CTPS_BY_LTP = "/ctps/ltp/:ltpId";
	// private static final String API_GET_CTPS_BY_LTP = "/ctps/node/:nodeId";
	

	private static final String API_ADD_LINK = "/link";  
	private static final String API_GET_LINK = "/link/:linkId";
	private static final String API_DELETE_LINK = "/link/:linkId";
	private static final String API_GET_ALL_LINKS = "/links/all";

	private static final String API_ADD_LINKCONN = "/linkConn";
	private static final String API_GET_LINKCONN = "/linkConn/:linkConnId";
	private static final String API_DELETE_LINKCONN = "/linkConn/:linkConnId";
	private static final String API_GET_ALL_LINKCONNS = "/linkConns/all";
	// private static final String API_GET_ALL_LINKCONNS = "/linkConns/link/:linkId";

	private final TopologyService service;

	public RestTopologyAPIVerticle(TopologyService service) {
		this.service = service;
	}

	@Override
	public void start(Future<Void> future) throws Exception {
		super.start();
		final Router router = Router.router(vertx);
		// body handler
		router.route().handler(BodyHandler.create());
		// API route handler
		router.get(API_VERSION).handler(this::apiVersion);

		router.post(API_ADD_SUBNET).handler(this::apiAddSubnet);
		router.get(API_GET_ALL_SUBNETS).handler(this::apiGetAllSubnets);
		router.get(API_GET_SUBNET).handler(this::apiGetSubnet);
		router.delete(API_DELETE_SUBNET).handler(this::apiDeleteSubnet);
		

		router.post(API_ADD_NODE).handler(this::apiAddNode);
		router.get(API_GET_ALL_NODES).handler(this::apiGetAllNodes);
		router.get(API_GET_NODE).handler(this::apiGetNode);
		router.delete(API_DELETE_NODE).handler(this::apiDeleteNode);		

		router.post(API_ADD_LTP).handler(this::apiAddLtp);
		router.get(API_GET_LTP).handler(this::apiGetLtp);
		router.delete(API_DELETE_LTP).handler(this::apiDeleteLtp);
		router.get(API_GET_LTPS_BY_NODE).handler(this::apiGetLtpsByNode);

		router.post(API_ADD_CTP).handler(this::apiAddCtp);
		router.get(API_GET_CTP).handler(this::apiGetCtp);
		router.delete(API_DELETE_CTP).handler(this::apiDeleteCtp);
		router.get(API_GET_CTPS_BY_LTP).handler(this::apiGetCtpsByLtp);			

		router.post(API_ADD_LINKCONN).handler(this::apiAddLinkConn);
		router.get(API_GET_ALL_LINKCONNS).handler(this::apiGetAllLinkConns);
		router.get(API_GET_LINKCONN).handler(this::apiGetLinkConn);
		router.delete(API_DELETE_LINKCONN).handler(this::apiDeleteLinkConn);
		
		router.post(API_ADD_LINK).handler(this::apiAddLink);
		router.get(API_GET_ALL_LINKS).handler(this::apiGetAllLinks);
		router.get(API_GET_LINK).handler(this::apiGetLink);
		router.delete(API_DELETE_LINK).handler(this::apiDeleteLink);

		// get HTTP host and port from configuration, or use default value
		String host = config().getString("topology.http.address", "0.0.0.0");
		int port = config().getInteger("topology.http.port", 8085);

		// create HTTP server and publish REST service
		createHttpServer(router, host, port)
		.compose(serverCreated -> publishHttpEndpoint(SERVICE_NAME, host, port))
		.onComplete(future);
	}

	private void apiVersion(RoutingContext context) { 
		context.response()
		.end(new JsonObject()
				.put("name", SERVICE_NAME)
				.put("version", "v1").encodePrettily());
	}

	// Node
	private void apiAddSubnet(RoutingContext context) {
		final Vsubnet vsubnet = Json.decodeValue(context.getBodyAsString(), Vsubnet.class);
		logger.debug("apiAddSubnet: "+vsubnet.toString());
		String error = ModelHelper.check(vsubnet);
		if (!error.isEmpty()) {
			badRequest(context, new IllegalStateException(error));
		} else {
			JsonObject result = new JsonObject().put("message", "subnet_added");
			service.addVsubnet(vsubnet, resultVoidHandler(context, result));
		}
	}

	private void apiGetSubnet(RoutingContext context) {
		String subnetId = context.request().getParam("subnetId");
		logger.debug("apiGetSubnet, subnetId: "+subnetId);
		if (subnetId == null) {
			badRequest(context, new IllegalStateException("missing subnetId"));
		} else {
			service.getVsubnet(subnetId, resultHandlerNonEmpty(context));
		}
	}

	private void apiDeleteSubnet(RoutingContext context) {
		String subnetId = context.request().getParam("subnetId");
		logger.debug("apiDeleteSubnet, subnetId: "+subnetId);
		if (subnetId == null) {
			badRequest(context, new IllegalStateException("missing subnetId"));
		} else {			
			service.deleteVsubnet(subnetId, deleteResultHandler(context));
		}
	}

	private void apiGetAllSubnets(RoutingContext context) {
		logger.debug("apiGetAllSubnets");
		service.getAllVsubnets(resultHandler(context, Json::encodePrettily));
	}

	// Node
	private void apiAddNode(RoutingContext context) {
		final Vnode vnode = Json.decodeValue(context.getBodyAsString(), Vnode.class);
		logger.debug("apiAddNode: "+vnode.toString());
		String error = ModelHelper.check(vnode);
		if (!error.isEmpty()) {
			badRequest(context, new IllegalStateException(error));
		} else {
			JsonObject result = new JsonObject().put("message", "node_added");
			service.addVnode(vnode, resultVoidHandler(context, result));
		}
	}

	private void apiGetNode(RoutingContext context) {
		String nodeId = context.request().getParam("nodeId");
		logger.debug("apiGetNode, nodeId: "+nodeId);
		if (nodeId == null) {
			badRequest(context, new IllegalStateException("nodeId not found"));
		} else {
			service.getVnode(nodeId, resultHandlerNonEmpty(context));
		}
	}

	private void apiDeleteNode(RoutingContext context) {
		String nodeId = context.request().getParam("nodeId");
		if (nodeId == null) {
			badRequest(context, new IllegalStateException("nodeId not found"));
		} else {			
			service.deleteVnode(nodeId, deleteResultHandler(context));
		}
	}

	private void apiGetAllNodes(RoutingContext context) {
		service.getAllVnodes(resultHandler(context, Json::encodePrettily));
	}

	// Ltp
	private void apiAddLtp(RoutingContext context) {
		final Vltp ltp = Json.decodeValue(context.getBodyAsString(), Vltp.class);
		String error = ModelHelper.check(ltp);
		if (!error.isEmpty()) {
			badRequest(context, new IllegalStateException(error));
		} else {			
			JsonObject result = new JsonObject().put("message", "ltp_added");
			service.addVltp(ltp, resultVoidHandler(context, result));		
		}
	}

	private void apiGetLtp(RoutingContext context) {
		String ltpId = context.request().getParam("ltpId");
		if (ltpId == null) {
			badRequest(context, new IllegalStateException("ltpId not found"));
		} else {
			service.getVltp(ltpId, resultHandlerNonEmpty(context));
		}
	}

	private void apiDeleteLtp(RoutingContext context) {
		String ltpId = context.request().getParam("ltpId");
		if (ltpId == null) {
			badRequest(context, new IllegalStateException("ltpId not found"));
		} else {
			service.deleteVltp(ltpId, deleteResultHandler(context));
		}
	}

	private void apiGetLtpsByNode(RoutingContext context) {	
		String nodeId = context.request().getParam("nodeId");
		if (nodeId == null) {
			badRequest(context, new IllegalStateException("nodeId not found"));
		} else {
			service.getVltpsByVnode(nodeId, resultHandler(context, Json::encodePrettily));
		}
	}

	// Ctp
	private void apiAddCtp(RoutingContext context) {
		final Vctp ctp = Json.decodeValue(context.getBodyAsString(), Vctp.class);
		String error = ModelHelper.check(ctp);
		if (!error.isEmpty()) {
			badRequest(context, new IllegalStateException(error));
		} else {			
			JsonObject result = new JsonObject().put("message", "ctp_added");
			service.addVctp(ctp, resultVoidHandler(context, result));
		}
	}

	private void apiGetCtp(RoutingContext context) {
		String ctpId = context.request().getParam("ctpId");
		if (ctpId == null) {
			badRequest(context, new IllegalStateException("ctpId not found"));
		} else {
			service.getVctp(ctpId, resultHandlerNonEmpty(context));
		}
	}

	private void apiDeleteCtp(RoutingContext context) {
		String ctpId = context.request().getParam("ctpId");
		if (ctpId == null) {
			badRequest(context, new IllegalStateException("ctpId not found"));
		} else {
			service.deleteVctp(ctpId, deleteResultHandler(context));
		}
	}

	private void apiGetCtpsByLtp(RoutingContext context) {
		String ltpId = context.request().getParam("ltpId");
		if (ltpId == null) {
			badRequest(context, new IllegalStateException("ltpId not found"));
		} else {
			service.getVctpsByVltp(ltpId, resultHandler(context, Json::encodePrettily));
		}
	}

	// Link
	private void apiAddLink(RoutingContext context) {
		final Vlink link = Json.decodeValue(context.getBodyAsString(), Vlink.class);
		String error = ModelHelper.check(link);
		if (!error.isEmpty()) {
			badRequest(context, new IllegalStateException(error));
		} else {
			JsonObject result = new JsonObject().put("message", "link_added");
			service.addVlink(link, resultVoidHandler(context, result));				
		}
	}

	private void apiGetLink(RoutingContext context) {
		String linkId = context.request().getParam("linkId");
		if (linkId == null) {
			badRequest(context, new IllegalStateException("linkId not found"));
		} else {
			service.getVlink(linkId, resultHandlerNonEmpty(context));
		}
	}

	private void apiDeleteLink(RoutingContext context) {
		String linkId = context.request().getParam("linkId");
		if (linkId == null) {
			badRequest(context, new IllegalStateException("linkId not found"));
		} else {
			service.deleteVlink(linkId, deleteResultHandler(context));
		}
	}

	private void apiGetAllLinks(RoutingContext context) {
		service.getAllVlinks(resultHandler(context, Json::encodePrettily));
	}

	// LinkConn
	private void apiAddLinkConn(RoutingContext context) {
		final VlinkConn vlinkConn = Json.decodeValue(context.getBodyAsString(), VlinkConn.class);
		String error = ModelHelper.check(vlinkConn);
		if (!error.isEmpty()) {
			badRequest(context, new IllegalStateException(error));
		} else {			
			JsonObject result = new JsonObject().put("message", "linkConn_added");
			service.addVlinkConn(vlinkConn, resultVoidHandler(context, result));				
		}
	}

	private void apiGetLinkConn(RoutingContext context) {
		String linkConnId = context.request().getParam("linkConnId");
		if (linkConnId == null) {
			badRequest(context, new IllegalStateException("linkConnId not found"));
		} else {
			service.getVlinkConn(linkConnId, resultHandlerNonEmpty(context));
		}
	}

	private void apiDeleteLinkConn(RoutingContext context) {
		String linkConnId = context.request().getParam("linkConnId");
		if (linkConnId == null) {
			badRequest(context, new IllegalStateException("linkConnId not found"));
		} else {
			service.deleteVlinkConn(linkConnId, deleteResultHandler(context));
		}
	}

	private void apiGetAllLinkConns(RoutingContext context) {
		service.getAllVlinkConns(resultHandler(context, Json::encodePrettily));
	}
}

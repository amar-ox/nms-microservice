package io.nms.central.microservice.topology.api;

import io.nms.central.microservice.common.RestAPIVerticle;
import io.nms.central.microservice.topology.TopologyService;
import io.nms.central.microservice.topology.model.Face;
import io.nms.central.microservice.topology.model.PrefixAnn;
import io.nms.central.microservice.topology.model.Route;
import io.nms.central.microservice.topology.model.Vctp;
import io.nms.central.microservice.topology.model.Vlink;
import io.nms.central.microservice.topology.model.VlinkConn;
import io.nms.central.microservice.topology.model.Vltp;
import io.nms.central.microservice.topology.model.Vnode;
import io.nms.central.microservice.topology.model.Vsubnet;
import io.nms.central.microservice.topology.model.Vtrail;
import io.nms.central.microservice.topology.model.Vxc;
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
	private static final String API_GET_ALL_SUBNETS = "/subnets";
	private static final String API_DELETE_SUBNET = "/subnet/:subnetId";
	// private static final String API_UPDATE_SUBNET = "/subnet";

	private static final String API_ADD_NODE = "/node";
	private static final String API_GET_NODE = "/node/:nodeId";	
	private static final String API_GET_ALL_NODES = "/nodes";
	private static final String API_GET_NODES_BY_SUBNET = "/subnet/:subnetId/nodes";
	private static final String API_DELETE_NODE = "/node/:nodeId";
	// private static final String API_UPDATE_NODE = "/node/:nodeId";

	private static final String API_ADD_LTP = "/ltp";
	private static final String API_GET_LTP = "/ltp/:ltpId";	
	private static final String API_GET_ALL_LTPS = "/ltps";
	private static final String API_GET_LTPS_BY_NODE = "/node/:nodeId/ltps";
	private static final String API_DELETE_LTP = "/ltp/:ltpId";
	// private static final String API_UPDATE_LTP = "/ltp/:ltpId";

	private static final String API_ADD_CTP = "/ctp";
	private static final String API_GET_CTP = "/ctp/:ctpId";	
	private static final String API_GET_ALL_CTPS = "/ctps";
	private static final String API_GET_CTPS_BY_LTP = "/ltp/:ltpId/ctps";
	private static final String API_GET_CTPS_BY_NODE = "/node/:nodeId/ctps";
	private static final String API_GET_CTPS_BY_LINK = "/link/:linkId/ctps";
	private static final String API_DELETE_CTP = "/ctp/:ctpId";
	// private static final String API_UPDATE_CTP = "/ctp/:ctpId";

	private static final String API_ADD_LINK = "/link";
	private static final String API_GET_LINK = "/link/:linkId";	
	private static final String API_GET_ALL_LINKS = "/links";
	private static final String API_GET_LINKS_BY_SUBNET = "/subnet/:subnetId/links";
	private static final String API_DELETE_LINK = "/link/:linkId";
	// private static final String API_UPDATE_LINK = "/link/:linkId";

	private static final String API_ADD_LINKCONN = "/linkConn";
	private static final String API_GET_LINKCONN = "/linkConn/:linkConnId";	
	private static final String API_GET_ALL_LINKCONNS = "/linkConns";
	private static final String API_GET_LINKCONNS_BY_LINK = "/link/:linkId/linkConns";
	private static final String API_GET_LINKCONNS_BY_SUBNET = "/subnet/:subnetId/linkConns";
	private static final String API_DELETE_LINKCONN = "/linkConn/:linkConnId";
	// private static final String API_UPDATE_LINKCONN = "/linkConn/:linkConnId";

	private static final String API_ADD_TRAIL = "/trail";
	private static final String API_GET_TRAIL = "/trail/:trailId";	
	private static final String API_GET_ALL_TRAILS = "/trails";
	private static final String API_GET_TRAILS_BY_SUBNET = "/subnet/:subnetId/trails";
	private static final String API_DELETE_TRAIL = "/trail/:trailId";
	// private static final String API_UPDATE_TRAIL = "/trail/:trailId";

	private static final String API_ADD_XC = "/xc";
	private static final String API_GET_XC = "/xc/:xcId";
	private static final String API_GET_ALL_XCS = "/xcs";
	private static final String API_GET_XCS_BY_TRAIL = "/trail/:trailId/xcs";
	private static final String API_GET_XCS_BY_NODE = "/node/:nodeId/xcs";
	private static final String API_DELETE_XC = "/xc/:xcId";
	// private static final String API_UPDATE_XC = "/xc/:xcId";

	private static final String API_ADD_PREFIX_ANN = "/prefixAnn";
	private static final String API_GET_PREFIX_ANN = "/prefixAnn/:prefixAnnId";	
	private static final String API_GET_ALL_PREFIX_ANNS = "/prefixAnns";	
	private static final String API_DELETE_PREFIX_ANN = "/prefixAnn/:prefixAnnId";
	// private static final String API_UPDATE_PREFIX_ANN = "/prefixAnn/:prefixAnnId";

	private static final String API_ADD_ROUTE = "/route";
	private static final String API_GEN_ROUTES = "/genroutes";
	private static final String API_GET_ROUTE = "/route/:routeId";	
	private static final String API_GET_ALL_ROUTES = "/routes";
	private static final String API_GET_ROUTES_BY_NODE = "/routes/node/:nodeId";
	private static final String API_DELETE_ROUTE = "/route/:routeId";

	private static final String API_ADD_FACE = "/face";
	private static final String API_GEN_FACES = "/genfaces";
	private static final String API_GET_FACE = "/face/:faceId";	
	private static final String API_GET_ALL_FACES = "/faces";
	private static final String API_GET_FACES_BY_NODE = "/node/:nodeId/faces";
	private static final String API_DELETE_FACE = "/face/:faceId";


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
		// router.patch(API_UPDATE_SUBNET).handler(this::apiUpdateSubnet);

		router.post(API_ADD_NODE).handler(this::apiAddNode);
		router.get(API_GET_ALL_NODES).handler(this::apiGetAllNodes);
		router.get(API_GET_NODES_BY_SUBNET).handler(this::apiGetNodesBySubnet);
		router.get(API_GET_NODE).handler(this::apiGetNode);
		router.delete(API_DELETE_NODE).handler(this::apiDeleteNode);
		// router.patch(API_UPDATE_NODE).handler(this::apiUpdateNode);

		router.post(API_ADD_LTP).handler(this::apiAddLtp);
		router.get(API_GET_ALL_LTPS).handler(this::apiGetAllLtps);
		router.get(API_GET_LTPS_BY_NODE).handler(this::apiGetLtpsByNode);
		router.get(API_GET_LTP).handler(this::apiGetLtp);
		router.delete(API_DELETE_LTP).handler(this::apiDeleteLtp);
		// router.patch(API_UPDATE_LTP).handler(this::apiUpdateLtp);

		router.post(API_ADD_CTP).handler(this::apiAddCtp);
		router.get(API_GET_ALL_CTPS).handler(this::apiGetAllCtps);
		router.get(API_GET_CTPS_BY_LTP).handler(this::apiGetCtpsByLtp);
		router.get(API_GET_CTPS_BY_NODE).handler(this::apiGetCtpsByNode);
		router.get(API_GET_CTPS_BY_LINK).handler(this::apiGetCtpsByLink);
		router.get(API_GET_CTP).handler(this::apiGetCtp);
		router.delete(API_DELETE_CTP).handler(this::apiDeleteCtp);
		// router.patch(API_UPDATE_CTP).handler(this::apiUpdateCtp);

		router.post(API_ADD_LINK).handler(this::apiAddLink);
		router.get(API_GET_ALL_LINKS).handler(this::apiGetAllLinks);
		router.get(API_GET_LINKS_BY_SUBNET).handler(this::apiGetLinksBySubnet);
		router.get(API_GET_LINK).handler(this::apiGetLink);
		router.delete(API_DELETE_LINK).handler(this::apiDeleteLink);
		// router.patch(API_UPDATE_LINK).handler(this::apiUpdateLink);

		router.post(API_ADD_LINKCONN).handler(this::apiAddLinkConn);
		router.get(API_GET_ALL_LINKCONNS).handler(this::apiGetAllLinkConns);
		router.get(API_GET_LINKCONNS_BY_LINK).handler(this::apiGetLinkConnsByLink);
		router.get(API_GET_LINKCONNS_BY_SUBNET).handler(this::apiGetLinkConnsBySubnet);
		router.get(API_GET_LINKCONN).handler(this::apiGetLinkConn);
		router.delete(API_DELETE_LINKCONN).handler(this::apiDeleteLinkConn);
		// router.patch(API_UPDATE_LINKCONN).handler(this::apiUpdateLinkConn);

		router.post(API_ADD_TRAIL).handler(this::apiAddTrail);
		router.get(API_GET_ALL_TRAILS).handler(this::apiGetAllTrails);
		router.get(API_GET_TRAILS_BY_SUBNET).handler(this::apiGetTrailsBySubnet);
		router.get(API_GET_TRAIL).handler(this::apiGetTrail);
		router.delete(API_DELETE_TRAIL).handler(this::apiDeleteTrail);
		// router.patch(API_UPDATE_TRAIL).handler(this::apiUpdateTrail);

		router.post(API_ADD_XC).handler(this::apiAddXc);
		router.get(API_GET_ALL_XCS).handler(this::apiGetAllXcs);
		router.get(API_GET_XCS_BY_TRAIL).handler(this::apiGetXcsByTrail);
		router.get(API_GET_XCS_BY_NODE).handler(this::apiGetXcsByNode);
		router.get(API_GET_XC).handler(this::apiGetXc);
		router.delete(API_DELETE_XC).handler(this::apiDeleteXc);
		// router.patch(API_UPDATE_XC).handler(this::apiUpdateXc);

		router.post(API_ADD_PREFIX_ANN).handler(this::apiAddPrefixAnn);
		router.get(API_GET_ALL_PREFIX_ANNS).handler(this::apiGetAllPrefixAnns);
		router.get(API_GET_PREFIX_ANN).handler(this::apiGetPrefixAnn);
		router.delete(API_DELETE_PREFIX_ANN).handler(this::apiDeletePrefixAnn);
		// router.patch(API_UPDATE_PREFIX_ANN).handler(this::apiUpdatePrefixAnn);

		router.post(API_ADD_ROUTE).handler(this::apiAddRoute);
		router.post(API_GEN_ROUTES).handler(this::apiGenRoutes);
		router.get(API_GET_ALL_ROUTES).handler(this::apiGetAllRoutes);
		router.get(API_GET_ROUTES_BY_NODE).handler(this::apiGetRoutesByNode);
		router.get(API_GET_ROUTE).handler(this::apiGetRoute);
		router.delete(API_DELETE_ROUTE).handler(this::apiDeleteRoute);

		router.post(API_ADD_FACE).handler(this::apiAddFace);
		router.post(API_GEN_FACES).handler(this::apiGenFaces);
		router.get(API_GET_ALL_FACES).handler(this::apiGetAllFaces);
		router.get(API_GET_FACES_BY_NODE).handler(this::apiGetFacesByNode);
		router.get(API_GET_FACE).handler(this::apiGetFace);
		router.delete(API_DELETE_FACE).handler(this::apiDeleteFace);

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

	// Node API
	private void apiAddSubnet(RoutingContext context) {
		final Vsubnet vsubnet = Json.decodeValue(context.getBodyAsString(), Vsubnet.class);
		// logger.debug("apiAddSubnet: "+vsubnet.toString());
		// JsonObject result = new JsonObject().put("message", "subnet_added");
		service.addVsubnet(vsubnet, resultHandlerNonEmpty(context, "id"));
	}
	private void apiGetSubnet(RoutingContext context) {
		String subnetId = context.request().getParam("subnetId");
		// logger.debug("apiGetSubnet, subnetId: "+subnetId);		
		service.getVsubnet(subnetId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllSubnets(RoutingContext context) {
		// logger.debug("apiGetAllSubnets");
		service.getAllVsubnets(resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteSubnet(RoutingContext context) {
		String subnetId = context.request().getParam("subnetId");
		// logger.debug("apiDeleteSubnet, subnetId: "+subnetId);		
		service.deleteVsubnet(subnetId, deleteResultHandler(context));
	}
	private void apiUpdateSubnet(RoutingContext context) {
		String id = context.request().getParam("subnetId");
		final Vsubnet vsubnet = Json.decodeValue(context.getBodyAsString(), Vsubnet.class);
		logger.debug("apiUpdateSubnet: "+vsubnet.toString());		
		service.updateVsubnet(id, vsubnet, resultHandlerNonEmpty(context));
	}


	// Node API 
	private void apiAddNode(RoutingContext context) {
		final Vnode vnode = Json.decodeValue(context.getBodyAsString(), Vnode.class);
		// logger.debug("apiAddNode: "+vnode.toString());		
		// JsonObject result = new JsonObject().put("message", "node_added");
		service.addVnode(vnode, resultHandlerNonEmpty(context, "id"));
	}
	private void apiGetNode(RoutingContext context) {
		String nodeId = context.request().getParam("nodeId");
		// logger.debug("apiGetNode, nodeId: "+nodeId);
		service.getVnode(nodeId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllNodes(RoutingContext context) {
		service.getAllVnodes(resultHandler(context, Json::encodePrettily));
	}
	private void apiGetNodesBySubnet(RoutingContext context) {	
		String subnetId = context.request().getParam("subnetId");		
		service.getVnodesByVsubnet(subnetId, resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteNode(RoutingContext context) {
		String nodeId = context.request().getParam("nodeId");
		service.deleteVnode(nodeId, deleteResultHandler(context));
	}
	private void apiUpdateNode(RoutingContext context) {
		String id = context.request().getParam("nodeId");
		final Vnode vnode = Json.decodeValue(context.getBodyAsString(), Vnode.class);		
		service.updateVnode(id, vnode, resultHandlerNonEmpty(context));
	}


	// Ltp API
	private void apiAddLtp(RoutingContext context) {
		final Vltp ltp = Json.decodeValue(context.getBodyAsString(), Vltp.class);		
		// JsonObject result = new JsonObject().put("message", "ltp_added");
		service.addVltp(ltp, resultHandlerNonEmpty(context, "id"));
	}
	private void apiGetLtp(RoutingContext context) {
		String ltpId = context.request().getParam("ltpId");
		service.getVltp(ltpId, resultHandlerNonEmpty(context));
	}
	private void apiGetLtpsByNode(RoutingContext context) {	
		String nodeId = context.request().getParam("nodeId");		
		service.getVltpsByVnode(nodeId, resultHandler(context, Json::encodePrettily));
	}
	private void apiGetAllLtps(RoutingContext context) {		
		service.getAllVltps(resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteLtp(RoutingContext context) {
		String ltpId = context.request().getParam("ltpId");
		service.deleteVltp(ltpId, deleteResultHandler(context));
	}
	private void apiUpdateLtp(RoutingContext context) {
		String id = context.request().getParam("ltpId");
		final Vltp vltp = Json.decodeValue(context.getBodyAsString(), Vltp.class);		
		service.updateVltp(id, vltp, resultHandlerNonEmpty(context));
	}


	// Ctp API
	private void apiAddCtp(RoutingContext context) {
		final Vctp ctp = Json.decodeValue(context.getBodyAsString(), Vctp.class);		
		// JsonObject result = new JsonObject().put("message", "ctp_added");
		service.addVctp(ctp, resultHandlerNonEmpty(context, "id"));
	}
	private void apiGetCtp(RoutingContext context) {
		String ctpId = context.request().getParam("ctpId");
		service.getVctp(ctpId, resultHandlerNonEmpty(context));
	}
	private void apiGetCtpsByLtp(RoutingContext context) {
		String ltpId = context.request().getParam("ltpId");		
		service.getVctpsByVltp(ltpId, resultHandler(context, Json::encodePrettily));		
	}
	private void apiGetCtpsByNode(RoutingContext context) {
		String nodeId = context.request().getParam("nodeId");		
		service.getVctpsByVnode(nodeId, resultHandler(context, Json::encodePrettily));		
	}
	private void apiGetCtpsByLink(RoutingContext context) {
		String linkId = context.request().getParam("linkId");		
		service.getVctpsByVlink(linkId, resultHandler(context, Json::encodePrettily));		
	}
	private void apiGetAllCtps(RoutingContext context) {		
		service.getAllVctps(resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteCtp(RoutingContext context) {
		String ctpId = context.request().getParam("ctpId");		
		service.deleteVctp(ctpId, deleteResultHandler(context));
	}
	private void apiUpdateCtp(RoutingContext context) {
		String id = context.request().getParam("ctpId");
		final Vctp vctp = Json.decodeValue(context.getBodyAsString(), Vctp.class);		
		service.updateVctp(id, vctp, resultHandlerNonEmpty(context));
	}


	// Link API
	private void apiAddLink(RoutingContext context) {
		final Vlink link = Json.decodeValue(context.getBodyAsString(), Vlink.class);		
		// JsonObject result = new JsonObject().put("message", "link_added");
		service.addVlink(link, resultHandlerNonEmpty(context, "id"));				
	}
	private void apiGetLink(RoutingContext context) {
		String linkId = context.request().getParam("linkId");
		service.getVlink(linkId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllLinks(RoutingContext context) {
		service.getAllVlinks(resultHandler(context, Json::encodePrettily));
	}
	private void apiGetLinksBySubnet(RoutingContext context) {	
		String subnetId = context.request().getParam("subnetId");		
		service.getVlinksByVsubnet(subnetId, resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteLink(RoutingContext context) {
		String linkId = context.request().getParam("linkId");		
		service.deleteVlink(linkId, deleteResultHandler(context));	
	}
	private void apiUpdateLink(RoutingContext context) {
		String id = context.request().getParam("linkId");
		final Vlink vlink = Json.decodeValue(context.getBodyAsString(), Vlink.class);		
		service.updateVlink(id, vlink, resultHandlerNonEmpty(context));
	}

	// LinkConn API
	private void apiAddLinkConn(RoutingContext context) {
		final VlinkConn vlinkConn = Json.decodeValue(context.getBodyAsString(), VlinkConn.class);		
		// JsonObject result = new JsonObject().put("message", "linkConn_added");
		service.addVlinkConn(vlinkConn, resultHandlerNonEmpty(context, "id"));				
	}
	private void apiGetLinkConn(RoutingContext context) {
		String linkConnId = context.request().getParam("linkConnId");		
		service.getVlinkConn(linkConnId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllLinkConns(RoutingContext context) {
		service.getAllVlinkConns(resultHandler(context, Json::encodePrettily));
	}
	private void apiGetLinkConnsByLink(RoutingContext context) {	
		String linkId = context.request().getParam("linkId");		
		service.getVlinkConnsByVlink(linkId, resultHandler(context, Json::encodePrettily));
	}
	private void apiGetLinkConnsBySubnet(RoutingContext context) {	
		String subnetId = context.request().getParam("subnetId");		
		service.getVlinkConnsByVsubnet(subnetId, resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteLinkConn(RoutingContext context) {
		String linkConnId = context.request().getParam("linkConnId");		
		service.deleteVlinkConn(linkConnId, deleteResultHandler(context));
	}
	private void apiUpdateLinkConn(RoutingContext context) {
		String id = context.request().getParam("linkConnId");
		final VlinkConn vlinkConn = Json.decodeValue(context.getBodyAsString(), VlinkConn.class);		
		service.updateVlinkConn(id, vlinkConn, resultHandlerNonEmpty(context));
	}


	// Trail API
	private void apiAddTrail(RoutingContext context) {
		final Vtrail vtrail = Json.decodeValue(context.getBodyAsString(), Vtrail.class);			
		// JsonObject result = new JsonObject().put("message", "trail_added");
		service.addVtrail(vtrail, resultHandlerNonEmpty(context, "id"));
	}
	private void apiGetTrail(RoutingContext context) {
		String trailId = context.request().getParam("trailId");			
		service.getVtrail(trailId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllTrails(RoutingContext context) {
		service.getAllVtrails(resultHandler(context, Json::encodePrettily));
	}
	private void apiGetTrailsBySubnet(RoutingContext context) {	
		String subnetId = context.request().getParam("subnetId");		
		service.getVtrailsByVsubnet(subnetId, resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteTrail(RoutingContext context) {
		String trailId = context.request().getParam("trailId");			
		service.deleteVtrail(trailId, deleteResultHandler(context));
	}
	private void apiUpdateTrail(RoutingContext context) {
		String id = context.request().getParam("trailId");
		final Vtrail vtrail = Json.decodeValue(context.getBodyAsString(), Vtrail.class);		
		service.updateVtrail(id, vtrail, resultHandlerNonEmpty(context));
	}


	// Xc API
	private void apiAddXc(RoutingContext context) {
		final Vxc vxc = Json.decodeValue(context.getBodyAsString(), Vxc.class);			
		// JsonObject result = new JsonObject().put("message", "xc_added");
		service.addVxc(vxc, resultHandlerNonEmpty(context, "id"));
	}
	private void apiGetXc(RoutingContext context) {
		String xcId = context.request().getParam("xcId");
		service.getVxc(xcId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllXcs(RoutingContext context) {
		service.getAllVxcs(resultHandler(context, Json::encodePrettily));
	}
	private void apiGetXcsByTrail(RoutingContext context) {	
		String trailId = context.request().getParam("trailId");			
		service.getVxcsByVtrail(trailId, resultHandler(context, Json::encodePrettily));
	}
	private void apiGetXcsByNode(RoutingContext context) {	
		String nodeId = context.request().getParam("nodeId");			
		service.getVxcsByVnode(nodeId, resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteXc(RoutingContext context) {
		String xcId = context.request().getParam("xcId");			
		service.deleteVxc(xcId, deleteResultHandler(context));
	}
	private void apiUpdateXc(RoutingContext context) {
		String id = context.request().getParam("xcId");
		final Vxc vxc = Json.decodeValue(context.getBodyAsString(), Vxc.class);		
		service.updateVxc(id, vxc, resultHandlerNonEmpty(context));
	}


	// Prefix Announcement API
	private void apiAddPrefixAnn(RoutingContext context) {
		final PrefixAnn prefixAnn = Json.decodeValue(context.getBodyAsString(), PrefixAnn.class);			
		// JsonObject result = new JsonObject().put("message", "prefixAnn_added");
		service.addPrefixAnn(prefixAnn, resultHandlerNonEmpty(context, "id"));
	}
	private void apiGetPrefixAnn(RoutingContext context) {
		String prefixAnnId = context.request().getParam("prefixAnnId");			
		service.getPrefixAnn(prefixAnnId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllPrefixAnns(RoutingContext context) {
		service.getAllPrefixAnns(resultHandler(context, Json::encodePrettily));
	}
	private void apiDeletePrefixAnn(RoutingContext context) {
		String prefixAnnId = context.request().getParam("prefixAnnId");			
		service.deletePrefixAnn(prefixAnnId, deleteResultHandler(context));
	}
	private void apiUpdatePrefixAnn(RoutingContext context) {
		String id = context.request().getParam("prefixAnnId");
		final PrefixAnn prefixAnn = Json.decodeValue(context.getBodyAsString(), PrefixAnn.class);		
		service.updatePrefixAnn(id, prefixAnn, resultHandlerNonEmpty(context));
	}


	// Routing Table Entry API
	private void apiAddRoute(RoutingContext context) {
		final Route route = Json.decodeValue(context.getBodyAsString(), Route.class);
		service.addRoute(route, resultHandlerNonEmpty(context, "id"));
	}
	private void apiGenRoutes(RoutingContext context) {
		String prefix = context.getBodyAsJson().getString("prefix");
		// JsonObject result = new JsonObject().put("message", "faces_created");
		service.generateRoutesToPrefix(prefix, resultHandlerNonEmpty(context));
	}
	private void apiGetRoute(RoutingContext context) {
		String routeId = context.request().getParam("routeId");
		service.getRoute(routeId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllRoutes(RoutingContext context) {
		service.getAllRoutes(resultHandler(context, Json::encodePrettily));
	}
	private void apiGetRoutesByNode(RoutingContext context) {	
		String nodeId = context.request().getParam("nodeId");		
		service.getRoutesByNode(nodeId, resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteRoute(RoutingContext context) {
		String routeId = context.request().getParam("routeId");
		service.deleteRoute(routeId, deleteResultHandler(context));
	}


	// Routing Table Entry API
	private void apiAddFace(RoutingContext context) {
		final Face face = Json.decodeValue(context.getBodyAsString(), Face.class);			
		service.addFace(face, resultHandlerNonEmpty(context, "id"));
	}
	private void apiGenFaces(RoutingContext context) {
		Integer linkConnId = context.getBodyAsJson().getInteger("vlinkConnId");
		JsonObject result = new JsonObject().put("message", "faces_created");
		service.generateFacesForLc(String.valueOf(linkConnId), resultVoidHandler(context, result));
	}
	private void apiGetFace(RoutingContext context) {
		String faceId = context.request().getParam("faceId");			
		service.getFace(faceId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllFaces(RoutingContext context) {
		service.getAllFaces(resultHandler(context, Json::encodePrettily));
	}
	private void apiGetFacesByNode(RoutingContext context) {	
		String nodeId = context.request().getParam("nodeId");		
		service.getFacesByNode(nodeId, resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteFace(RoutingContext context) {
		String faceId = context.request().getParam("faceId");			
		service.deleteFace(faceId, deleteResultHandler(context));
	}
}

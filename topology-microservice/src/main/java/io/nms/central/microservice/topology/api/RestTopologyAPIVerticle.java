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
 * @author Amar Abane
 */
public class RestTopologyAPIVerticle extends RestAPIVerticle {

	private static final Logger logger = LoggerFactory.getLogger(RestTopologyAPIVerticle.class);

	public static final String SERVICE_NAME = "topology-rest-api";

	private static final String API_VERSION = "/v";
	
	private static final String API_REPORT = "/reports";

	private static final String API_ONE_SUBNET = "/subnet/:subnetId";
	private static final String API_ALL_SUBNETS = "/subnets";

	private static final String API_ONE_NODE = "/node/:nodeId";	
	private static final String API_ALL_NODES = "/nodes";
	private static final String API_NODES_BY_SUBNET = "/subnet/:subnetId/nodes";

	private static final String API_ONE_LTP = "/ltp/:ltpId";	
	private static final String API_ALL_LTPS = "/ltps";
	private static final String API_LTPS_BY_NODE = "/node/:nodeId/ltps";

	private static final String API_ONE_CTP = "/ctp/:ctpId";	
	private static final String API_ALL_CTPS = "/ctps";
	private static final String API_CTPS_BY_LTP = "/ltp/:ltpId/ctps";
	private static final String API_CTPS_BY_NODE = "/node/:nodeId/ctps";

	private static final String API_ONE_LINK = "/link/:linkId";	
	private static final String API_ALL_LINKS = "/links";
	private static final String API_LINKS_BY_SUBNET = "/subnet/:subnetId/links";

	private static final String API_ONE_LINKCONN = "/linkConn/:linkConnId";	
	private static final String API_ALL_LINKCONNS = "/linkConns";
	private static final String API_LINKCONNS_BY_LINK = "/link/:linkId/linkConns";
	private static final String API_LINKCONNS_BY_SUBNET = "/subnet/:subnetId/linkConns";

	private static final String API_ONE_TRAIL = "/trail/:trailId";	
	private static final String API_ALL_TRAILS = "/trails";
	private static final String API_TRAILS_BY_SUBNET = "/subnet/:subnetId/trails";

	private static final String API_ONE_XC = "/xc/:xcId";
	private static final String API_ALL_XCS = "/xcs";
	private static final String API_XCS_BY_TRAIL = "/trail/:trailId/xcs";
	private static final String API_XCS_BY_NODE = "/node/:nodeId/xcs";

	private static final String API_ONE_PA = "/prefixAnn/:prefixAnnId";	
	private static final String API_ALL_PAS = "/prefixAnns";
	private static final String API_PAS_BY_SUBNET = "/subnet/:subnetId/prefixAnns";
	private static final String API_PAS_BY_NODE = "/node/:nodeId/prefixAnns";

	private static final String API_ONE_ROUTE = "/route/:routeId";	
	private static final String API_ALL_ROUTES = "/routes";
	private static final String API_ROUTES_BY_SUBNET = "/subnet/:subnetId/routes";
	private static final String API_ROUTES_BY_NODE = "/node/:nodeId/routes";
	// private static final String API_GEN_ROUTES = "/genroutes";

	private static final String API_ONE_FACE = "/face/:faceId";	
	private static final String API_ALL_FACES = "/faces";
	private static final String API_FACES_BY_SUBNET = "/subnet/:subnetId/faces";
	private static final String API_FACES_BY_NODE = "/node/:nodeId/faces";
	// private static final String API_GEN_FACES = "/genfaces";


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
		
		router.put(API_REPORT).handler(this::apiProcessReport);

		router.post(API_ALL_SUBNETS).handler(this::apiAddSubnet);
		router.get(API_ALL_SUBNETS).handler(this::apiGetAllSubnets);
		router.get(API_ONE_SUBNET).handler(this::apiGetSubnet);
		router.delete(API_ONE_SUBNET).handler(this::apiDeleteSubnet);
		router.put(API_ONE_SUBNET).handler(this::apiUpdateSubnet);

		router.post(API_ALL_NODES).handler(this::apiAddNode);
		router.get(API_ALL_NODES).handler(this::apiGetAllNodes);
		router.get(API_NODES_BY_SUBNET).handler(this::apiGetNodesBySubnet);
		router.get(API_ONE_NODE).handler(this::apiGetNode);
		router.delete(API_ONE_NODE).handler(this::apiDeleteNode);
		router.put(API_ONE_NODE).handler(this::apiUpdateNode);

		router.post(API_ALL_LTPS).handler(this::apiAddLtp);
		router.get(API_ALL_LTPS).handler(this::apiGetAllLtps);
		router.get(API_LTPS_BY_NODE).handler(this::apiGetLtpsByNode);
		router.get(API_ONE_LTP).handler(this::apiGetLtp);
		router.delete(API_ONE_LTP).handler(this::apiDeleteLtp);
		router.put(API_ONE_LTP).handler(this::apiUpdateLtp);

		router.post(API_ALL_CTPS).handler(this::apiAddCtp);
		router.get(API_ALL_CTPS).handler(this::apiGetAllCtps);
		router.get(API_CTPS_BY_LTP).handler(this::apiGetCtpsByLtp);
		router.get(API_CTPS_BY_NODE).handler(this::apiGetCtpsByNode);
		router.get(API_ONE_CTP).handler(this::apiGetCtp);
		router.delete(API_ONE_CTP).handler(this::apiDeleteCtp);
		router.put(API_ONE_CTP).handler(this::apiUpdateCtp);

		router.post(API_ALL_LINKS).handler(this::apiAddLink);
		router.get(API_ALL_LINKS).handler(this::apiGetAllLinks);
		router.get(API_LINKS_BY_SUBNET).handler(this::apiGetLinksBySubnet);
		router.get(API_ONE_LINK).handler(this::apiGetLink);
		router.delete(API_ONE_LINK).handler(this::apiDeleteLink);
		router.put(API_ONE_LINK).handler(this::apiUpdateLink);

		router.post(API_ALL_LINKCONNS).handler(this::apiAddLinkConn);
		router.get(API_ALL_LINKCONNS).handler(this::apiGetAllLinkConns);
		router.get(API_LINKCONNS_BY_LINK).handler(this::apiGetLinkConnsByLink);
		router.get(API_LINKCONNS_BY_SUBNET).handler(this::apiGetLinkConnsBySubnet);
		router.get(API_ONE_LINKCONN).handler(this::apiGetLinkConn);
		router.delete(API_ONE_LINKCONN).handler(this::apiDeleteLinkConn);
		router.put(API_ONE_LINKCONN).handler(this::apiUpdateLinkConn);

		router.post(API_ALL_TRAILS).handler(this::apiAddTrail);
		router.get(API_ALL_TRAILS).handler(this::apiGetAllTrails);
		router.get(API_TRAILS_BY_SUBNET).handler(this::apiGetTrailsBySubnet);
		router.get(API_ONE_TRAIL).handler(this::apiGetTrail);
		router.delete(API_ONE_TRAIL).handler(this::apiDeleteTrail);
		router.put(API_ONE_TRAIL).handler(this::apiUpdateTrail);

		router.post(API_ALL_XCS).handler(this::apiAddXc);
		router.get(API_ALL_XCS).handler(this::apiGetAllXcs);
		router.get(API_XCS_BY_TRAIL).handler(this::apiGetXcsByTrail);
		router.get(API_XCS_BY_NODE).handler(this::apiGetXcsByNode);
		router.get(API_ONE_XC).handler(this::apiGetXc);
		router.delete(API_ONE_XC).handler(this::apiDeleteXc);
		router.put(API_ONE_XC).handler(this::apiUpdateXc);

		router.post(API_ALL_FACES).handler(this::apiAddFace);
		router.get(API_ALL_FACES).handler(this::apiGetAllFaces);
		router.get(API_FACES_BY_SUBNET).handler(this::apiGetFacesBySubnet);
		router.get(API_FACES_BY_NODE).handler(this::apiGetFacesByNode);
		router.get(API_ONE_FACE).handler(this::apiGetFace);
		router.delete(API_ONE_FACE).handler(this::apiDeleteFace);

		// router.post(API_GEN_FACES).handler(this::apiGenFaces);

		router.post(API_ALL_PAS).handler(this::apiAddPrefixAnn);
		router.get(API_ALL_PAS).handler(this::apiGetAllPrefixAnns);
		router.get(API_PAS_BY_SUBNET).handler(this::apiGetPrefixAnnsBySubnet);
		router.get(API_PAS_BY_NODE).handler(this::apiGetPrefixAnnsByNode);
		router.get(API_ONE_PA).handler(this::apiGetPrefixAnn);
		router.delete(API_ONE_PA).handler(this::apiDeletePrefixAnn);

		router.post(API_ALL_ROUTES).handler(this::apiAddRoute);		
		router.get(API_ALL_ROUTES).handler(this::apiGetAllRoutes);
		router.get(API_ROUTES_BY_SUBNET).handler(this::apiGetRoutesBySubnet);
		router.get(API_ROUTES_BY_NODE).handler(this::apiGetRoutesByNode);
		router.get(API_ONE_ROUTE).handler(this::apiGetRoute);
		router.delete(API_ONE_ROUTE).handler(this::apiDeleteRoute);

		// router.post(API_GEN_ROUTES).handler(this::apiGenRoutes);

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
	
	// Report API (for TEST)
	private void apiProcessReport(RoutingContext context) {
		final JsonObject report = context.getBodyAsJson();
		JsonObject result = new JsonObject().put("message", "report processed");
		service.reportDispatcher(report, resultVoidHandler(context, result));
	}

	// Node API
	private void apiAddSubnet(RoutingContext context) {
		final Vsubnet vsubnet = Json.decodeValue(context.getBodyAsString(), Vsubnet.class);
		service.addVsubnet(vsubnet, createResultHandler(context, API_ONE_SUBNET));
	}
	private void apiGetSubnet(RoutingContext context) {
		String subnetId = context.request().getParam("subnetId");		
		service.getVsubnet(subnetId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllSubnets(RoutingContext context) {
		service.getAllVsubnets(resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteSubnet(RoutingContext context) {
		String subnetId = context.request().getParam("subnetId");		
		service.deleteVsubnet(subnetId, deleteResultHandler(context));
	}
	private void apiUpdateSubnet(RoutingContext context) {
		String id = context.request().getParam("subnetId");
		final Vsubnet vsubnet = Json.decodeValue(context.getBodyAsString(), Vsubnet.class);		
		service.updateVsubnet(id, vsubnet, resultHandlerNonEmpty(context, "n_updated"));
	}


	// Node API 
	private void apiAddNode(RoutingContext context) {
		final Vnode vnode = Json.decodeValue(context.getBodyAsString(), Vnode.class);
		service.addVnode(vnode, createResultHandler(context, API_ONE_NODE));
	}
	private void apiGetNode(RoutingContext context) {
		String nodeId = context.request().getParam("nodeId");
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
		service.updateVnode(id, vnode, resultHandlerNonEmpty(context, "n_updated"));
	}


	// Ltp API
	private void apiAddLtp(RoutingContext context) {
		final Vltp ltp = Json.decodeValue(context.getBodyAsString(), Vltp.class);		
		service.addVltp(ltp, createResultHandler(context, API_ONE_LTP));
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
		service.updateVltp(id, vltp, resultHandlerNonEmpty(context, "n_updated"));
	}


	// Ctp API
	private void apiAddCtp(RoutingContext context) {
		final Vctp ctp = Json.decodeValue(context.getBodyAsString(), Vctp.class);		
		service.addVctp(ctp, createResultHandler(context, API_ONE_CTP));
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
		service.updateVctp(id, vctp, resultHandlerNonEmpty(context, "n_updated"));
	}


	// Link API
	private void apiAddLink(RoutingContext context) {
		final Vlink link = Json.decodeValue(context.getBodyAsString(), Vlink.class);
		service.addVlink(link, createResultHandler(context, API_ONE_LINK));				
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
		service.updateVlink(id, vlink, resultHandlerNonEmpty(context, "n_updated"));
	}

	// LinkConn API
	private void apiAddLinkConn(RoutingContext context) {
		final VlinkConn vlinkConn = Json.decodeValue(context.getBodyAsString(), VlinkConn.class);
		service.addVlinkConn(vlinkConn, createResultHandler(context, API_ONE_LINKCONN));				
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
		service.updateVlinkConn(id, vlinkConn, resultHandlerNonEmpty(context, "n_updated"));
	}


	// Trail API
	private void apiAddTrail(RoutingContext context) {
		final Vtrail vtrail = Json.decodeValue(context.getBodyAsString(), Vtrail.class);			
		service.addVtrail(vtrail, createResultHandler(context, API_ONE_TRAIL));
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
		service.updateVtrail(id, vtrail, resultHandlerNonEmpty(context, "n_updated"));
	}


	// Xc API
	private void apiAddXc(RoutingContext context) {
		final Vxc vxc = Json.decodeValue(context.getBodyAsString(), Vxc.class);
		service.addVxc(vxc, createResultHandler(context, API_ONE_XC));
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
		service.updateVxc(id, vxc, resultHandlerNonEmpty(context, "n_updated"));
	}


	// Face API
	private void apiAddFace(RoutingContext context) {
		final Face face = Json.decodeValue(context.getBodyAsString(), Face.class);			
		service.addFace(face, createResultHandler(context, API_ONE_FACE));
	}	
	private void apiGetFace(RoutingContext context) {
		String faceId = context.request().getParam("faceId");
		service.getFace(faceId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllFaces(RoutingContext context) {
		service.getAllFaces(resultHandler(context, Json::encodePrettily));
	}
	private void apiGetFacesBySubnet(RoutingContext context) {	
		String subnetId = context.request().getParam("subnetId");		
		service.getFacesByVsubnet(subnetId, resultHandler(context, Json::encodePrettily));
	}
	private void apiGetFacesByNode(RoutingContext context) {	
		String nodeId = context.request().getParam("nodeId");		
		service.getFacesByNode(nodeId, resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteFace(RoutingContext context) {
		String faceId = context.request().getParam("faceId");			
		service.deleteFace(faceId, deleteResultHandler(context));
	}


	// Prefix Announcement API
	private void apiAddPrefixAnn(RoutingContext context) {
		final PrefixAnn prefixAnn = Json.decodeValue(context.getBodyAsString(), PrefixAnn.class);
		// use voidresult with PUT
		service.addPrefixAnn(prefixAnn, createResultHandler(context, API_ONE_PA));
	}
	private void apiGetPrefixAnn(RoutingContext context) {
		String prefixAnnId = context.request().getParam("prefixAnnId");			
		service.getPrefixAnn(prefixAnnId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllPrefixAnns(RoutingContext context) {
		service.getAllPrefixAnns(resultHandler(context, Json::encodePrettily));
	}
	private void apiGetPrefixAnnsBySubnet(RoutingContext context) {	
		String subnetId = context.request().getParam("subnetId");		
		service.getPrefixAnnsByVsubnet(subnetId, resultHandler(context, Json::encodePrettily));
	}
	private void apiGetPrefixAnnsByNode(RoutingContext context) {	
		String nodeId = context.request().getParam("nodeId");		
		service.getPrefixAnnsByVnode(nodeId, resultHandler(context, Json::encodePrettily));
	}
	private void apiDeletePrefixAnn(RoutingContext context) {
		String prefixAnnId = context.request().getParam("prefixAnnId");			
		service.deletePrefixAnn(prefixAnnId, deleteResultHandler(context));
	}


	// Routing Table Entry API
	private void apiAddRoute(RoutingContext context) {
		final Route route = Json.decodeValue(context.getBodyAsString(), Route.class);
		service.addRoute(route, createResultHandler(context, API_ONE_ROUTE));
	}
	private void apiGetRoute(RoutingContext context) {
		String routeId = context.request().getParam("routeId");
		service.getRoute(routeId, resultHandlerNonEmpty(context));
	}
	private void apiGetAllRoutes(RoutingContext context) {
		service.getAllRoutes(resultHandler(context, Json::encodePrettily));
	}
	private void apiGetRoutesBySubnet(RoutingContext context) {	
		String subnetId = context.request().getParam("subnetId");
		service.getRoutesByVsubnet(subnetId, resultHandler(context, Json::encodePrettily));
	}
	private void apiGetRoutesByNode(RoutingContext context) {	
		String nodeId = context.request().getParam("nodeId");		
		service.getRoutesByNode(nodeId, resultHandler(context, Json::encodePrettily));
	}
	private void apiDeleteRoute(RoutingContext context) {
		String routeId = context.request().getParam("routeId");
		service.deleteRoute(routeId, deleteResultHandler(context));
	}
}

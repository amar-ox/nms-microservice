package io.nms.central.microservice.topology.impl;

public class InternalSql {
	
	public static final String LOCK_TABLES_FOR_NODE = "LOCK TABLES "
			+ "Vltp WRITE, Vltp AS sLtp WRITE, Vltp AS dLtp WRITE, "
			+ "Vctp WRITE, Vctp AS sCtp WRITE, Vctp AS dCtp WRITE, "
			+ "Vnode WRITE, Vlink WRITE, VlinkConn WRITE, "
			+ "PrefixAnn WRITE, "
			+ "Face WRITE, Face AS sF WRITE, Face AS dF WRITE, "
			+ "Route WRITE";
	
	public static final String LOCK_TABLES_FOR_LTP = "LOCK TABLES "
			+ "Vltp WRITE, Vltp AS sLtp WRITE, Vltp AS dLtp WRITE, "
			+ "Vlink WRITE, VlinkConn WRITE, "
			+ "Vctp WRITE, Vctp AS sCtp WRITE, Vctp AS dCtp WRITE";
	
	public static final String LOCK_TABLES_FOR_LINK = "LOCK TABLES "
			+ "Vltp WRITE, Vltp AS sLtp WRITE, Vltp AS dLtp WRITE, "
			+ "Vlink WRITE, VlinkConn WRITE, "
			+ "Vctp WRITE, Vctp AS sCtp WRITE, Vctp AS dCtp WRITE";
	
	public static final String LOCK_TABLES_FOR_LC = "LOCK TABLES "
			+ "Vctp WRITE, Vctp AS sCtp WRITE, Vctp AS dCtp WRITE, "
			+ "Vltp WRITE, Vltp AS sLtp WRITE, Vltp AS dLtp WRITE, "
			+ "VlinkConn WRITE";
	
	public static final String LOCK_TABLES_FOR_LC_AUTO = "LOCK TABLES "
			+ "Vctp WRITE, Vctp AS sCtp WRITE, Vctp AS dCtp WRITE, "
			+ "Vltp WRITE, Vltp AS sLtp WRITE, Vltp AS dLtp WRITE, "
			+ "Face WRITE, Face AS sF WRITE, Face as dF WRITE, Vnode WRITE, "
			+ "Route WRITE, PrefixAnn WRITE, VlinkConn WRITE, Vlink WRITE";
	
	public static final String LOCK_TABLES_FOR_ROUTE = "LOCK TABLES "
			+ "Vctp WRITE, Vctp AS sCtp WRITE, Vctp AS dCtp WRITE, "
			+ "Vltp WRITE, Vltp AS sLtp WRITE, Vltp AS dLtp WRITE, "
			+ "Face WRITE, Face AS sF WRITE, Face as dF WRITE, Vnode WRITE, "
			+ "Route WRITE, PrefixAnn WRITE, VlinkConn WRITE, Vlink WRITE";
	
	/* ------------------------------------------ */
	
	// get info needed to generate CTPs for a LinkConn
	public static final String FETCH_CTPGEN_INFO = "SELECT "
			+ "sLtp.id AS sLtpId, dLtp.id AS dLtpId, sLtp.name AS sLtpName, dLtp.name AS dLtpName "
			+ "FROM ((Vlink "
			+ "INNER JOIN Vltp AS sLtp ON sLtp.id=srcVltpId) "
			+ "INNER JOIN Vltp AS dLtp ON dLtp.id=destVltpId) "
			+ "WHERE Vlink.id = ?";
	
	// get info needed to generate Faces for a LinkConn
	public static final String FETCH_FACEGEN_INFO = "SELECT "
			+ "VlinkConn.id AS vlinkConnId, sCtp.id AS sVctpId, dCtp.id AS dVctpId, "
			+ "JSON_UNQUOTE(JSON_EXTRACT(sLtp.info, '$.port')) AS sLtpPort, "
			+ "JSON_UNQUOTE(JSON_EXTRACT(dLtp.info, '$.port')) AS dLtpPort "
			+ "FROM ((VlinkConn "
			+ "INNER JOIN Vctp AS sCtp ON sCtp.id=srcVctpId INNER JOIN Vltp AS sLtp ON sLtp.id=sCtp.vltpId) "
			+ "INNER JOIN Vctp AS dCtp ON dCtp.id=destVctpId INNER JOIN Vltp AS dLtp ON dLtp.id=dCtp.vltpId) "
			+ "WHERE VlinkConn.id = ?";
	
	
	public static final String DELETE_ALL_ROUTES = "DELETE FROM Route";
	
	// get info needed to compute routes: linkConns
	public static final String FETCH_ROUTEGEN_LCS = "SELECT "
			+ "VlinkConn.id AS id, sF.id AS srcFaceId, dF.id AS destFaceId, sLtp.vnodeId AS srcNodeId, dLtp.vnodeId AS destNodeId "
			+ "FROM ((VlinkConn "
			+ "INNER JOIN "
			+ "Vctp AS sCtp ON sCtp.id=VlinkConn.srcVctpId INNER JOIN Vltp AS sLtp ON sLtp.id=sCtp.vltpId INNER JOIN Face AS sF ON sCtp.id=sF.vctpId) "
			+ "INNER JOIN "
			+ "Vctp AS dCtp ON dCtp.id=VlinkConn.destVctpId INNER JOIN Vltp AS dLtp ON dLtp.id=dCtp.vltpId INNER JOIN Face AS dF ON dCtp.id=dF.vctpId) "
			+ "WHERE VlinkConn.status != 'DOWN'";
	
	// get info needed to compute routes :nodes
	public static final String FETCH_ROUTEGEN_NODES = "SELECT id FROM Vnode WHERE status != 'DOWN'";
	
	// get info needed to compute routes: all prefix announcements
	public static final String FETCH_ROUTEGEN_ALL_PAS = "SELECT id, name, originId FROM PrefixAnn WHERE available IS true";
	
	// get info needed to compute routes: prefix announcements by name
	public static final String FETCH_ROUTEGEN_PAS_BY_NAME = "SELECT id, name, originId FROM PrefixAnn WHERE name = ? AND available IS true";
	
	
	public static final String UPDATE_LTP_BUSY = "UPDATE Vltp SET busy=IFNULL(?, busy) WHERE id = ?";
	
	public static final String UPDATE_ROUTE = "INSERT INTO Route (paId, nodeId, nextHopId, faceId, cost, origin) "
			+ "VALUES (?, ?, ?, ?, ?, ?) "
			+ "ON DUPLICATE KEY UPDATE nextHopId = VALUES(nextHopId), faceId = VALUES(faceId), cost = VALUES(cost)";
	public static final String UPDATE_FACE = "INSERT INTO Face (label, status, local, remote, scheme, vctpId, vlinkConnId) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?) "
			+ "ON DUPLICATE KEY UPDATE "
			+ "label = VALUES(label), "
			+ "status = VALUES(status), "
			+ "local = VALUES(local), "
			+ "remote = VALUES(remote), "
			+ "scheme = VALUES(scheme), "
			+ "vlinkConnId = VALUES(vlinkConnId)";
	
	
	public static final String UPDATE_NODE_STATUS = "UPDATE Vnode SET status=IFNULL(?, status) WHERE id = ?";
	public static final String UPDATE_LTP_STATUS = "UPDATE Vltp SET status=IFNULL(?, status) WHERE id = ?";
	public static final String UPDATE_LINK_STATUS = "UPDATE Vlink SET status=IFNULL(?, status) WHERE id = ?";
	public static final String UPDATE_LC_STATUS = "UPDATE VlinkConn SET status=IFNULL(?, status) WHERE id = ?";
	public static final String UPDATE_FACE_STATUS = "UPDATE Face SET status=IFNULL(?, status) WHERE id = ?";
	public static final String UPDATE_PA_STATUS_BY_NODE = "UPDATE PrefixAnn SET available=IFNULL(?, available) WHERE originId = ?";
	/* public static final String UPDATE_PA_STATUS_BY_NODE = "UPDATE PrefixAnn, "
	+ "("  
	+ "    SELECT id " 
	+ "    FROM Vnode "
	+ "    WHERE Vnode.id = ? OR Vnode.name = ? "
	+ ") as n "
	+ "SET available=IFNULL(?, available) WHERE PrefixAnn.originId = n.id"; */
	
	// get node status
	public static final String GET_NODE_STATUS = "SELECT status FROM Vnode WHERE id = ?";

	// get all node IDs
	public static final String FETCH_ALL_NODE_IDS = "SELECT id FROM Vnode";
	
	// get LTPs of a node by id or name
	public static final String FETCH_LTPS_BY_NODE = "SELECT "
			+ "Vltp.id, Vltp.name "
			+ "FROM Vnode "
			+ "INNER JOIN Vltp ON Vnode.id=Vltp.vnodeId "
			+ "WHERE Vnode.id = ?";
	
	// get the Link of an LTP by id or name
	public static final String FETCH_LINK_BY_LTP = "SELECT "
			+ "Vlink.id, Vlink.name "
			+ "FROM Vltp "
			+ "INNER JOIN Vlink ON Vltp.id=Vlink.srcVltpId OR Vltp.id=Vlink.destVltpId "
			+ "WHERE Vltp.id = ?";
	
	public static final String FETCH_LINK_LTP_STATUS = "SELECT "
			+ "Vltp.id , Vltp.status "
			+ "FROM Vlink "
			+ "INNER JOIN Vltp ON Vltp.id=Vlink.srcVltpId OR Vltp.id=Vlink.destVltpId "
			+ "WHERE Vlink.id = ?";
	
	// get LinkConns of a Link by id or name
	public static final String FETCH_LCS_BY_LINK = "SELECT "
			+ "VlinkConn.id, VlinkConn.name "
			+ "FROM Vlink "
			+ "INNER JOIN VlinkConn ON Vlink.id=VlinkConn.vlinkId "
			+ "WHERE Vlink.id = ?";
	
	// get ...
	public static final String FETCH_FACES_BY_LC = "SELECT "
			+ "Face.id "
			+ "FROM VlinkConn "
			+ "LEFT JOIN Face ON VlinkConn.id=Face.vlinkConnId "
			+ "WHERE VlinkConn.id = ?";
}

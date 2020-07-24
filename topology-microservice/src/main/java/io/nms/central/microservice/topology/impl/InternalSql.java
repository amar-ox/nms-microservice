package io.nms.central.microservice.topology.impl;

public class InternalSql {
	
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
			+ "JSON_EXTRACT(sLtp.info, '$.port') AS sLtpPort, "
			+ "JSON_EXTRACT(dLtp.info, '$.port') AS dLtpPort "
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
			+ "WHERE VlinkConn.status = 'UP'";
	// get info needed to compute routes :nodes
	public static final String FETCH_ROUTEGEN_NODES = "SELECT id FROM Vnode WHERE status = 'UP'";
	
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
	
	
	public static final String UPDATE_NODE_STATUS = "UPDATE Vnode SET status=IFNULL(?, status) WHERE id = ? OR name = ?";
	public static final String UPDATE_LTP_STATUS = "UPDATE Vltp SET status=IFNULL(?, status) WHERE id = ? OR name = ?";
	public static final String UPDATE_LINK_STATUS = "UPDATE Vlink SET status=IFNULL(?, status) WHERE id = ? OR name = ?";
	public static final String UPDATE_LC_STATUS = "UPDATE VlinkConn SET status=IFNULL(?, status) WHERE id = ? OR name = ?";
	public static final String UPDATE_FACE_STATUS = "UPDATE Face SET status=IFNULL(?, status) WHERE id = ?";
	public static final String UPDATE_PA_STATUS_BY_NODE = "UPDATE PrefixAnn SET available=IFNULL(?, available) WHERE originId = ?";
	
	// get LTPs of a node by id or name
	public static final String FETCH_LTPS_BY_NODE = "SELECT "
			+ "Vltp.id, Vltp.name "
			+ "FROM Vnode "
			+ "INNER JOIN Vltp ON Vnode.id=Vltp.vnodeId "
			+ "WHERE Vnode.id = ? OR Vnode.name = ?";
	
	// get the Link of an LTP by id or name
	public static final String FETCH_LINK_BY_LTP = "SELECT "
			+ "Vlink.id, Vlink.name "
			+ "FROM Vltp "
			+ "INNER JOIN Vlink ON Vltp.id=Vlink.srcVltpId OR Vltp.id=Vlink.destVltpId "
			+ "WHERE Vltp.id = ? OR Vltp.name = ?";
	
	public static final String FETCH_LINK_UP = "SELECT "
			+ "Vltp.id "
			+ "FROM Vlink "
			+ "INNER JOIN Vltp ON Vltp.id=Vlink.srcVltpId OR Vltp.id=Vlink.destVltpId "
			+ "WHERE (Vlink.id = ? OR Vlink.name = ?) AND Vltp.status = 'UP'" ;
	
	// get LinkConns of a Link by id or name
	public static final String FETCH_LCS_BY_LINK = "SELECT "
			+ "VlinkConn.id, VlinkConn.name "
			+ "FROM Vlink "
			+ "INNER JOIN VlinkConn ON Vlink.id=VlinkConn.vlinkId "
			+ "WHERE Vlink.id = ? OR Vlink.name = ?";
	
	// get ...
	public static final String FETCH_FACES_BY_LC = "SELECT "
			+ "Face.id "
			+ "FROM VlinkConn "
			+ "LEFT JOIN Face ON VlinkConn.id=Face.vlinkConnId "
			+ "WHERE VlinkConn.id = ? OR VlinkConn.name = ?";
}

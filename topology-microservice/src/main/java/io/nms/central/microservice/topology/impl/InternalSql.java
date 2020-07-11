package io.nms.central.microservice.topology.impl;

public class InternalSql {
	
	// get info needed to generate faces
	public static final String FETCH_FACEGEN_INFO = "SELECT "
			+ "VlinkConn.id AS vlinkConnId, sCtp.id AS sVctpId, dCtp.id AS dVctpId, sLtp.info AS sLtpInfo, dLtp.info AS dLtpInfo "
			+ "FROM ((VlinkConn "
			+ "INNER JOIN Vctp AS sCtp ON sCtp.id=srcVctpId INNER JOIN Vltp AS sLtp ON sLtp.id=sCtp.vltpId) "
			+ "INNER JOIN Vctp AS dCtp ON dCtp.id=destVctpId INNER JOIN Vltp AS dLtp ON dLtp.id=dCtp.vltpId) "
			+ "WHERE VlinkConn.id = ?";
	
	
	public static final String FETCH_ROUTEGEN_LCS = "SELECT "
			+ "VlinkConn.id AS id, sF.id AS srcFaceId, dF.id AS destFaceId, sLtp.vnodeId AS srcNodeId, dLtp.vnodeId AS destNodeId "
// 			+ "JSON_EXTRACT(sLtp.info, '$.port') AS sLtpInfo, JSON_EXTRACT(dLtp.info, '$.port') AS dLtpInfo "
			+ "FROM ((VlinkConn "
			+ "INNER JOIN "
			+ "Vctp AS sCtp ON sCtp.id=VlinkConn.srcVctpId INNER JOIN Vltp AS sLtp ON sLtp.id=sCtp.vltpId INNER JOIN Face AS sF ON sCtp.id=sF.vctpId) "
			+ "INNER JOIN "
			+ "Vctp AS dCtp ON dCtp.id=VlinkConn.destVctpId INNER JOIN Vltp AS dLtp ON dLtp.id=dCtp.vltpId INNER JOIN Face AS dF ON dCtp.id=dF.vctpId)";
	
	public static final String FETCH_ROUTEGEN_NODES = "SELECT id FROM Vnode";
	
	public static final String FETCH_ROUTEGEN_PAS = "SELECT id, name, originId FROM PrefixAnn";
	public static final String FETCH_ROUTEGEN_PAS_BY_NAME = "SELECT id, name, originId FROM PrefixAnn WHERE name = ?";
	
	
	public static final String UPDATE_LTP_BUSY = "UPDATE Vltp "
	+ "SET busy=IFNULL(?, busy) WHERE id = ?";

}

package io.nms.central.microservice.topology.impl;

public class InternalSql {
	
	// get info needed to generate faces
	public static final String FETCH_FACEGEN_INFO = "SELECT "
			+ "VlinkConn.id AS vlinkConnId, sCtp.id AS sVctpId, dCtp.id AS dVctpId, sLtp.info AS sLtpInfo, dLtp.info AS dLtpInfo "
			+ "FROM ((VlinkConn "
			+ "INNER JOIN Vctp AS sCtp ON sCtp.id=srcVctpId INNER JOIN Vltp AS sLtp ON sLtp.id=sCtp.vltpId) "
			+ "INNER JOIN Vctp AS dCtp ON dCtp.id=destVctpId INNER JOIN Vltp AS dLtp ON dLtp.id=dCtp.vltpId) "
			+ "WHERE VlinkConn.id = ?";
	
	public static final String FETCH_ROUTEGEN_LCS = "SELECT...";
	public static final String FETCH_ROUTEGEN_NODES = "SELECT...";
	
	
	
	
	public static final String UPDATE_LTP_BUSY = "UPDATE Vltp "
	+ "SET busy=IFNULL(?, busy) WHERE id = ?";

}

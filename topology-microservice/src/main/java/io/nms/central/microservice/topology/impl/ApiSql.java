package io.nms.central.microservice.topology.impl;

public class ApiSql {

	
	
	/*-------------------- TABLE CREATION --------------------*/
	public static final String CREATE_TABLE_VSUBNET = "CREATE TABLE IF NOT EXISTS `Vsubnet` (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    `name` VARCHAR(60) NOT NULL UNIQUE,\n" + 
			"    `label` VARCHAR(60) NOT NULL,\n" + 
			"    `description` VARCHAR(255) NOT NULL,\n" +
			"	 `info` JSON DEFAULT NULL,\n" +
			"    `status` VARCHAR(10) NOT NULL,\n" +
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" + 
			"    PRIMARY KEY (`id`)\n" +
			")";
	public static final String CREATE_TABLE_VNODE = "CREATE TABLE IF NOT EXISTS `Vnode` (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    `name` VARCHAR(60) NOT NULL UNIQUE,\n" + 
			"    `label` VARCHAR(60) NOT NULL,\n" + 
			"    `description` VARCHAR(255) NOT NULL,\n" +
			"	 `info` JSON DEFAULT NULL,\n" +
			"    `status` VARCHAR(10) NOT NULL,\n" +
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" + 
			"    `posx` INT,\n" + 
			"    `posy` INT,\n" + 
			"    `location` VARCHAR(20),\n" + 
			"    `type` VARCHAR(10) NOT NULL,\n" +
			"    `vsubnetId` INT NOT NULL,\n" + 
			"    PRIMARY KEY (`id`),\n" +
			"    FOREIGN KEY (`vsubnetId`)\n" + 
			"    	REFERENCES Vsubnet(`id`)\n" + 
			"       ON DELETE CASCADE\n" + 
			"		ON UPDATE CASCADE\n" + 
			")";
	public static final String CREATE_TABLE_VLTP = "CREATE TABLE IF NOT EXISTS Vltp (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    `name` VARCHAR(60) NOT NULL UNIQUE,\n" + 
			"    `label` VARCHAR(60) NOT NULL,\n" + 
			"    `description` VARCHAR(255) NOT NULL,\n" +
			"	 `info` JSON DEFAULT NULL,\n" +
			"    `status` VARCHAR(10) NOT NULL,\n" +
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +  		
			"    `busy` BOOLEAN DEFAULT 0,\n" +
			"    `vnodeId` INT NOT NULL,\n" + 
			"    PRIMARY KEY (`id`),\n" + 
			"    FOREIGN KEY (`vnodeId`) \n" + 
			"        REFERENCES Vnode(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"		 ON UPDATE CASCADE\n" + 
			")";
	public static final String CREATE_TABLE_VCTP = "CREATE TABLE IF NOT EXISTS Vctp (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    `name` VARCHAR(60) NOT NULL UNIQUE,\n" + 
			"    `label` VARCHAR(60) NOT NULL,\n" + 
			"    `description` VARCHAR(255),\n" +
			"	 `info` JSON DEFAULT NULL,\n" +
			"    `status` VARCHAR(10) NOT NULL,\n" +
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
			"    `busy` BOOLEAN DEFAULT 0,\n" +
			"    `vltpId` INT NOT NULL,\n" +  
			"    PRIMARY KEY (`id`),\n" + 
			"    FOREIGN KEY (`vltpId`) \n" + 
			"       REFERENCES Vltp(`id`)\n" + 
			"       ON DELETE CASCADE\n" + 
			"		ON UPDATE CASCADE\n" +
			")";
	public static final String CREATE_TABLE_VLINK = "CREATE TABLE IF NOT EXISTS Vlink (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    `name` VARCHAR(60) NOT NULL UNIQUE,\n" + 
			"    `label` VARCHAR(60) NOT NULL,\n" + 
			"    `description` VARCHAR(255),\n" +
			"	 `info` JSON DEFAULT NULL,\n" +
			"    `status` VARCHAR(10) NOT NULL,\n" +
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
			"    `type` VARCHAR(10) NOT NULL,\n" + 
			"    `srcVltpId` INT NOT NULL,\n" + 
			"    `destVltpId` INT NOT NULL,\n" + 
			"    PRIMARY KEY (`id`),\n" + 
			"    FOREIGN KEY (`srcVltpId`) \n" + 
			"        REFERENCES Vltp(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE,\n" + 
			"    FOREIGN KEY (`destVltpId`) \n" + 
			"        REFERENCES Vltp(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE\n" + 
			")";
	public static final String CREATE_TABLE_VLINKCONN = "CREATE TABLE IF NOT EXISTS VlinkConn (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    `name` VARCHAR(60) NOT NULL UNIQUE,\n" + 
			"    `label` VARCHAR(60) NOT NULL,\n" + 
			"    `description` VARCHAR(255) NOT NULL,\n" +
			"	 `info` JSON DEFAULT NULL,\n" +
			"    `status` VARCHAR(10) NOT NULL,\n" +
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
			"    `vlinkId` INT NOT NULL,\n" +
			"    `srcVctpId` INT NOT NULL,\n" + 
			"    `destVctpId` INT NOT NULL,\n" +
			"    PRIMARY KEY (`id`),\n" + 
			"    FOREIGN KEY (`vlinkId`) \n" + 
			"        REFERENCES Vlink(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE,\n" +
			"    FOREIGN KEY (`srcVctpId`) \n" + 
			"        REFERENCES Vctp(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE,\n" + 
			"    FOREIGN KEY (`destVctpId`) \n" + 
			"        REFERENCES Vctp(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE\n" + 
			")";
	public static final String CREATE_TABLE_VTRAIL = "CREATE TABLE IF NOT EXISTS Vtrail (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    `name` VARCHAR(60) NOT NULL UNIQUE,\n" + 
			"    `label` VARCHAR(60) NOT NULL,\n" + 
			"    `description` VARCHAR(255) NOT NULL,\n" +
			"	 `info` JSON DEFAULT NULL,\n" +
			"    `status` VARCHAR(10) NOT NULL,\n" +
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
			"    `srcVctpId` INT NOT NULL,\n" + 
			"    `destVctpId` INT NOT NULL,\n" +
			"    PRIMARY KEY (`id`),\n" + 
			"    FOREIGN KEY (`srcVctpId`) \n" + 
			"        REFERENCES Vctp(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE,\n" + 
			"    FOREIGN KEY (`destVctpId`) \n" + 
			"        REFERENCES Vctp(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE\n" + 
			")";
	public static final String CREATE_TABLE_VXC = "CREATE TABLE IF NOT EXISTS Vxc (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    `name` VARCHAR(60) NOT NULL UNIQUE,\n" + 
			"    `label` VARCHAR(60) NOT NULL,\n" + 
			"    `description` VARCHAR(255) NOT NULL,\n" +
			"	 `info` JSON DEFAULT NULL,\n" +
			"    `status` VARCHAR(10) NOT NULL,\n" +
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
			"    `type` VARCHAR(10) NOT NULL,\n" +  
			"    `vnodeId` INT NOT NULL,\n" +
			"    `vtrailId` INT NOT NULL,\n" +
			"    `srcVctpId` INT NOT NULL,\n" + 
			"    `destVctpId` INT NOT NULL,\n" +
			"    `dropVctpId` INT DEFAULT NULL,\n" +
			"    PRIMARY KEY (`id`),\n" + 
			"    FOREIGN KEY (`vnodeId`) \n" + 
			"        REFERENCES Vnode(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE,\n" +
			"    FOREIGN KEY (`vtrailId`) \n" + 
			"        REFERENCES Vtrail(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE,\n" + 
			"    FOREIGN KEY (`srcVctpId`) \n" + 
			"        REFERENCES Vctp(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE,\n" + 
			"    FOREIGN KEY (`destVctpId`) \n" + 
			"        REFERENCES Vctp(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE,\n" + 
			"    FOREIGN KEY (`dropVctpId`) \n" + 
			"        REFERENCES Vctp(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE\n" + 
			")";
	public static final String CREATE_TABLE_PREFIX_ANN = "CREATE TABLE IF NOT EXISTS PrefixAnn (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    `name` VARCHAR(255) NOT NULL,\n" + 
			"    `nodeId` INT NOT NULL,\n" +
			"    `strategy` VARCHAR(60) NOT NULL,\n" + 
			"    `status` VARCHAR(10) NOT NULL,\n" +
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
			"    PRIMARY KEY (`id`),\n" + 
			"    FOREIGN KEY (`nodeId`) \n" + 
			"        REFERENCES Vnode(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"		 ON UPDATE CASCADE\n" +
			")";
	public static final String CREATE_TABLE_RTE = "CREATE TABLE IF NOT EXISTS Rte (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    `prefixId` INT NOT NULL,\n" +
			"    `fromNodeId` INT NOT NULL,\n" +
			"    `nextHopId` INT NOT NULL,\n" +
			"    `cost` INT NOT NULL,\n" +
			"    `ctpId` INT NOT NULL,\n" +
			"    `status` VARCHAR(10) NOT NULL,\n" +
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +			
			"    PRIMARY KEY (`id`),\n" +
			"    FOREIGN KEY (`prefixId`) \n" +
			"        REFERENCES PrefixAnn(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"		 ON UPDATE CASCADE,\n" +
			"    FOREIGN KEY (`fromNodeId`) \n" + 
			"        REFERENCES Vnode(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"		 ON UPDATE CASCADE,\n" +
			"    FOREIGN KEY (`nextHopId`) \n" + 
			"        REFERENCES Vnode(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"		 ON UPDATE CASCADE,\n" +
			"    FOREIGN KEY (`ctpId`) \n" + 
			"        REFERENCES Vctp(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"		 ON UPDATE CASCADE\n" +
			")";
	public static final String CREATE_TABLE_FACE = "CREATE TABLE IF NOT EXISTS Face (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    `local` VARCHAR(60) NOT NULL,\n" +
			"    `remote` VARCHAR(60) NOT NULL,\n" +
			"    `scheme` VARCHAR(30) NOT NULL,\n" +
			"    `label` VARCHAR(60) NOT NULL,\n" +			
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
			"    `vctpId` INT NOT NULL,\n" +
			"    `vlinkConnId` INT NOT NULL,\n" +
			"    PRIMARY KEY (`id`),\n" + 
			"    FOREIGN KEY (`vctpId`) \n" + 
			"       REFERENCES Vctp(`id`)\n" + 
			"       ON DELETE CASCADE\n" + 
			"		ON UPDATE CASCADE,\n" +
			"    FOREIGN KEY (`vlinkConnId`) \n" + 
			"       REFERENCES VlinkConn(`id`)\n" + 
			"       ON DELETE CASCADE\n" + 
			"		ON UPDATE CASCADE\n" +
			")";
	
	/*-------------------- INSERT ITEMS --------------------*/
	
	public static final String INSERT_VSUBNET = "INSERT INTO Vsubnet (name, label, description, info, status) "
			+ "VALUES (?, ?, ?, ?, ?)";
	public static final String INSERT_VNODE = "INSERT INTO Vnode (name, label, description, info, status, posx, posy, location, type, vsubnetId) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VLTP = "INSERT INTO Vltp (name, label, description, info, status, busy, vnodeId) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VCTP = "INSERT INTO Vctp (name, label, description, info, status, busy, vltpId) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VLINK = "INSERT INTO Vlink (name, label, description, info, status, type, srcVltpId, destVltpId) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VLINKCONN = "INSERT INTO VlinkConn (name, label, description, info, status, srcVctpId, destVctpId, vlinkId) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VTRAIL = "INSERT INTO Vtrail (name, label, description, info, status, srcVctpId, destVctpId) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VXC = "INSERT INTO Vxc (name, label, description, info, status, type, vnodeId, vtrailId, srcVctpId, destVctpId, dropVctpId) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VXC_1 = "INSERT INTO Vxc (name, label, description, info, status, type, vnodeId, vtrailId, srcVctpId, destVctpId) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_PREFIX_ANN = "INSERT INTO PrefixAnn (name, strategy, nodeId, status) "
			+ "VALUES (?, ?, ?, ?)";
	public static final String INSERT_RTE = "INSERT INTO Rte (prefixId, fromNodeId, nextHopId, ctpId, cost, status) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	public static final String INSERT_FACE = "INSERT INTO Face (label, local, remote, scheme, vctpId, vlinkConnId) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";

	
	/*-------------------- FETCH ALL ITEMS --------------------*/
	
	// get all Vsubnets (without Vnodes and Vlinks)
	public static final String FETCH_ALL_VSUBNETS = "SELECT "
			+ "id, name, label, description, info, status, created, updated "
			+ "FROM Vsubnet";

	// get all Vnodes without Vltps
	// use: FETCH_VNODE_BY_ID to get a Vnode with its Vltps
	public static final String FETCH_ALL_VNODES = "SELECT "
			+ "id, name, label, description, info, status, created, updated, posx, posy, location, type, vsubnetId "
			+ "FROM Vnode";

	// get all Vltps without Vctps
	// use: FETCH_VLTP_BY_ID to get a Vltp with its Vctps
	public static final String FETCH_ALL_VLTPS = "SELECT "
			+ "id, name, label, description, info, status, created, updated, busy, vnodeId "
			+ "FROM Vltp";

	// get all Vctps
	public static final String FETCH_ALL_VCTPS = "SELECT "
			+ "id, name, label, description, info, status, created, updated, busy, vltpId "
			+ "FROM Vctp"; 

	// get all Vlinks without VlinkConns
	// use: FETCH_VLINK_BY_ID to get a Vltp with its Vctps
	public static final String FETCH_ALL_VLINKS = "SELECT "
			+ "Vlink.id, Vlink.name, Vlink.label, Vlink.description, Vlink.info, Vlink.status, Vlink.created, Vlink.updated, "
			+ "Vlink.type, Vlink.srcVltpId, Vlink.destVltpId, "
			+ "s.vnodeId AS srcVnodeId, d.vnodeId AS destVnodeId "
			+ "FROM ((Vlink INNER JOIN Vltp AS s ON Vlink.srcVltpId=s.id) INNER JOIN Vltp AS d ON Vlink.destVltpId=d.id)";

	// get all VlinkConns
	public static final String FETCH_ALL_VLINKCONNS = "SELECT "
			+ "VlinkConn.id, VlinkConn.name, VlinkConn.label, VlinkConn.description, VlinkConn.info, VlinkConn.status, "
			+ "VlinkConn.created, VlinkConn.updated, VlinkConn.srcVctpId, VlinkConn.destVctpId, VlinkConn.vlinkId, "
			+ "sCtp.vltpId AS srcVltpId, dCtp.vltpId AS destVltpId, sLtp.vnodeId AS srcVnodeId, dLtp.vnodeId AS destVnodeId "
			+ "FROM ((VlinkConn "
			+ "INNER JOIN Vctp AS sCtp ON VlinkConn.srcVctpId=sCtp.id INNER JOIN Vltp AS sLtp ON sCtp.vltpId=sLtp.id) "
			+ "INNER JOIN Vctp AS dCtp ON VlinkConn.destVctpId=dCtp.id INNER JOIN Vltp AS dLtp ON dCtp.vltpId=dLtp.id)";

	// get all Vtrails without Vxc
	// use: FETCH_VTRAIL_BY_ID to get a Vtrail with its Vxcs 
	public static final String FETCH_ALL_VTRAILS = "SELECT "
			+ "id, name, label, description, info, status, created, updated, srcVctpId, destVctpId "
			+ "FROM Vtrail"; 

	// get all Vxcs
	public static final String FETCH_ALL_VXCS = "SELECT "
			+ "id, name, label, description, info, status, created, updated, type, vnodeId, vtrailId, srcVctpId, destVctpId, dropVctpId "
			+ "FROM Vxc"; 
	
	
	public static final String FETCH_ALL_PREFIX_ANNS = "SELECT "
			+ "id, name, nodeId, strategy, status, created, updated "
			+ "FROM PrefixAnn";
	
	public static final String FETCH_ALL_RTES = "SELECT "
			+ "Rte.id, Rte.prefixId, PrefixAnn.name AS prefixName, Rte.fromNodeId, Rte.nextHopId, Rte.cost, Rte.ctpId, Rte.status, Rte.created, Rte.updated "
			+ "FROM Rte INNER JOIN PrefixAnn on Rte.prefixId=PrefixAnn.id";

	public static final String FETCH_ALL_FACES = "SELECT "
			+ "id, label, local, remote, scheme, created, updated, vctpId, vlinkConnId "
			+ "FROM Face"; 

	/*-------------------- FETCH ITEMS BY ANOTHER --------------------*/
	
	// get all the Vnodes of a Vsubnet (without Vltps)
	// use: FETCH_VNODE_BY_ID to get a Vnode with its Vltps
	public static final String FETCH_VNODES_BY_VSUBNET = "SELECT "
			+ "id, name, label, description, info, status, created, updated, posx, posy, location, type, vsubnetId "
			+ "FROM Vnode WHERE vsubnetId = ?";

	// Assuming that: a link is in a subnet if its source-node is in that subnet
	// get all the Vlinks of a Vsubnet (without VlinkConns)
	// use: FETCH_VLINK_BY_ID to get a Vlink with its VlinkConns
	public static final String FETCH_VLINKS_BY_VSUBNET = "SELECT "
			+ "Vlink.id, Vlink.name, Vlink.label, Vlink.description, Vlink.info, Vlink.status, Vlink.created, Vlink.updated, "
			+ "Vlink.type, Vlink.srcVltpId, Vlink.destVltpId, " 
			+ "Vltp.vnodeId AS srcVnodeId, destLtp.vnodeId AS destVnodeId, "
			+ "Vnode.vsubnetId " 
			+ "FROM Vnode "
			+ "INNER JOIN Vltp ON Vnode.id=Vltp.vnodeId "
			+ "INNER JOIN Vlink ON Vltp.id=Vlink.srcVltpId INNER JOIN Vltp as destLtp ON Vlink.destVltpId=destLtp.id "
			+ "WHERE Vnode.vsubnetId = ?";
	
	// get all the Vltps of a Vnode (without Vctps)
	// use: FETCH_VLTP_BY_ID to get a Vltp with its Vctps
	public static final String FETCH_VLTPS_BY_VNODE = "SELECT "
			+ "id, name, label, description, info, status, created, updated, busy, vnodeId "
			+ "FROM Vltp WHERE vnodeId = ?";

	// get all the Vctps of a Vltp
	public static final String FETCH_VCTPS_BY_VLTP = "SELECT "
			+ "id, name, label, description, info, status, created, updated, busy, vltpId "
			+ "FROM Vctp WHERE vltpId = ?";
	
	// get all the Vctps reachable over a Vlink
	public static final String FETCH_VCTPS_BY_VLINK = "SELECT "
			+ "allCtps.id, allCtps.name, allCtps.label, allCtps.description, allCtps.info, allCtps.status, allCtps.created, allCtps.updated, "
			+ "allCtps.busy, allCtps.vltpId, Vlink.id AS vlinkId " 
			+ "FROM ((Vlink "
			+ "INNER JOIN Vltp AS sLtp ON Vlink.srcVltpId=sLtp.id) "
			+ "INNER JOIN Vltp AS dLtp ON Vlink.destVltpId=dLtp.id) "
			+ "INNER JOIN Vctp AS allCtps ON allCtps.vltpId=sLtp.id OR allCtps.vltpId=dLtp.id "	
			+ "WHERE Vlink.id = ?";
	
	// get all the Vctps of a Vltp
	public static final String FETCH_VCTPS_BY_VNODE = "SELECT "
			+ "Vctp.id, Vctp.name, Vctp.label, Vctp.description, Vctp.info, Vctp.status, Vctp.created, Vctp.updated, "
			+ "Vctp.busy, Vctp.vltpId "
			+ "FROM Vnode "
			+ "INNER JOIN Vltp ON Vnode.id=Vltp.vnodeId INNER JOIN Vctp ON Vltp.id=Vctp.vltpId "
			+ "WHERE Vnode.Id = ?";

	// get all the VlinkConns that goes over a Vlink
	public static final String FETCH_VLINKCONNS_BY_VLINK = "SELECT "
			+ "VlinkConn.id, VlinkConn.name, VlinkConn.label, VlinkConn.description, VlinkConn.info, VlinkConn.status, "
			+ "VlinkConn.created, VlinkConn.updated, VlinkConn.srcVctpId, VlinkConn.destVctpId, VlinkConn.vlinkId, "
			+ "sCtp.vltpId AS srcVltpId, dCtp.vltpId AS destVltpId, sLtp.vnodeId AS srcVnodeId, dLtp.vnodeId AS destVnodeId "
			+ "FROM ((VlinkConn "
			+ "INNER JOIN Vctp AS sCtp ON VlinkConn.srcVctpId=sCtp.id INNER JOIN Vltp AS sLtp ON sCtp.vltpId=sLtp.id) "
			+ "INNER JOIN Vctp AS dCtp ON VlinkConn.destVctpId=dCtp.id INNER JOIN Vltp AS dLtp ON dCtp.vltpId=dLtp.id) "
			+ "WHERE VlinkConn.vlinkId = ?";
	
	// Assuming that: a linkConn is in a subnet if its source-node is in that subnet
	// get all the VlinkConns of a Vsubnet
	public static final String FETCH_VLINKCONNS_BY_VSUBNET = "SELECT "
			+ "VlinkConn.id, VlinkConn.name, VlinkConn.label, VlinkConn.description, VlinkConn.info, VlinkConn.status, VlinkConn.created, VlinkConn.updated, "
			+ "VlinkConn.srcVctpId, VlinkConn.destVctpId, VlinkConn.vlinkId, "
			+ "Vltp.id AS srcVltpId, destLtp.id AS destVltpId, "
			+ "Vltp.vnodeId AS srcVnodeId, destLtp.vnodeId AS destVnodeId, "
			+ "Vnode.vsubnetId " 
			+ "FROM Vnode "
			+ "INNER JOIN Vltp ON Vnode.id=Vltp.vnodeId INNER JOIN Vctp ON Vltp.id=Vctp.vltpId "
			+ "INNER JOIN VlinkConn ON Vctp.id=VlinkConn.srcVctpId INNER JOIN Vctp as destCtp ON VlinkConn.destVctpId=destCtp.id "
			+ "INNER JOIN Vltp AS destLtp ON destCtp.vltpId=destLtp.id "
			+ "WHERE Vnode.vsubnetId = ?";
	
	// get all the Vtrails of a Vsubnet
	public static final String FETCH_VTRAILS_BY_VSUBNET = "SELECT "
			+ "Vtrail.id, Vtrail.name, Vtrail.label, Vtrail.description, Vtrail.info, Vtrail.status, Vtrail.created, Vtrail.updated, "
			+ "Vtrail.srcVctpId, Vtrail.destVctpId "
			+ "FROM Vnode "
			+ "INNER JOIN Vltp ON Vnode.id=Vltp.vnodeId INNER JOIN Vctp ON Vltp.id=Vctp.vltpId "
			+ "INNER JOIN Vtrail ON Vctp.id=Vtrail.srcVctpId INNER JOIN Vctp as destCtp ON Vtrail.destVctpId=destCtp.id "
			+ "WHERE Vnode.vsubnetId = ?";

	// get all the Vxcs defined in a Vnode
	public static final String FETCH_VXC_BY_VNODE = "SELECT "
			+ "id, name, label, description, info, status, created, updated, type, vnodeId, vtrailId, srcVctpId, destVctpId, dropVctpId "
			+ "FROM Vxc WHERE vnodeId = ?";

	// get all the Vxcs that form the Vtrail
	public static final String FETCH_VXC_BY_VTRAIL = "SELECT "
			+ "id, name, label, description, info, status, created, updated, type, vnodeId, vtrailId, srcVctpId, destVctpId, dropVctpId "
			+ "FROM Vxc WHERE vtrailId = ?";
	
	// get all Routing entries of a node
	public static final String FETCH_RTES_BY_NODE = "SELECT "
			+ "Rte.id, Rte.prefixId, PrefixAnn.name AS prefixName, Rte.fromNodeId, Rte.nextHopId, Rte.cost, Rte.ctpId, Rte.status, Rte.created, Rte.updated "
			+ "FROM Rte INNER JOIN PrefixAnn on Rte.prefixId=PrefixAnn.id WHERE Rte.fromNodeId=?";
	
	// get all Face on a node
	public static final String FETCH_FACES_BY_NODE = "SELECT "
			+ "Face.id, Face.label, Face.local, Face.remote, Face.scheme, Face.created, Face.updated, Face.vctpId, Face.vlinkConnId "
			+ "FROM Vnode "
			+ "INNER JOIN Vltp on Vltp.vnodeId=Vnode.id "
			+ "INNER JOIN Vctp on Vctp.vltpId=Vltp.id "
			+ "INNER JOIN Face on Face.vctpId=Vctp.id "
			+ "WHERE Vnode.id = ?";


	/*-------------------- FETCH ITEMS BY ID --------------------*/

	// get a Vsubnet without Vnodes and Vlinks
	// use: FETCH_VNODES_BY_VSUBNET and FETCH_VLINKS_BY_VSUBNET to get Vnodes and Vlinks respectively 
	public static final String FETCH_VSUBNET_BY_ID = "SELECT "
			+ "id, name, label, description, info, status, created, updated FROM Vsubnet "
			+ "WHERE id = ?";

	// get a Vnode and its Vltps
	public static final String FETCH_VNODE_BY_ID = "SELECT "
			+ "Vnode.id, Vnode.name, Vnode.label, Vnode.description, Vnode.info, Vnode.status, Vnode.created, Vnode.updated, "
			+ "Vnode.posx, Vnode.posy, Vnode.location, Vnode.type, Vnode.vsubnetId, "
			+ "Vltp.id AS vltpId, Vltp.name AS vltpName, Vltp.label AS vltpLabel, Vltp.description AS vltpDescription, Vltp.info AS vltpInfo, "
			+ "Vltp.status AS vltpStatus, Vltp.created AS vltpCreated, Vltp.updated AS vltpUpdated, "
			+ "Vltp.busy AS vltpBusy, Vltp.vnodeId AS vltpVnodeId "
			+ "FROM `Vnode` LEFT JOIN `Vltp` ON Vnode.id=Vltp.vnodeId WHERE Vnode.id = ?";

	// get a Vltp and its Vctps
	public static final String FETCH_VLTP_BY_ID = "SELECT "
			+ "Vltp.id, Vltp.name, Vltp.label, Vltp.description, Vltp.info, Vltp.status, Vltp.created, Vltp.updated, Vltp.busy, Vltp.vnodeId, "
			+ "Vctp.id AS vctpId, Vctp.name AS vctpName, Vctp.label AS vctpLabel, Vctp.description AS vctpDescription, "
			+ "Vctp.info AS vctpInfo, Vctp.status AS vctpStatus, Vctp.created AS vctpCreated, Vctp.updated AS vctpUpdated, "
			+ "Vctp.busy AS vctpBusy, Vctp.vltpId AS vctpVltpId "
			+ "FROM `Vltp` LEFT JOIN `Vctp` ON Vltp.id=Vctp.vltpId WHERE Vltp.id = ?";

	// get a Vctp
	public static final String FETCH_VCTP_BY_ID = "SELECT "
			+ "id, name, label, description, info, status, created, updated, busy, vltpId "
			+ "FROM Vctp WHERE id = ?";

	// get a Vlink and its VlinkConns
	public static final String FETCH_VLINK_BY_ID = "SELECT "
			+ "Vlink.id, Vlink.name, Vlink.label, Vlink.description, Vlink.info, Vlink.status, Vlink.created, Vlink.updated, "
			+ "Vlink.type, Vlink.srcVltpId, Vlink.destVltpId, "
			+ "sLtp.vnodeId AS srcVnodeId, dLtp.vnodeId AS destVnodeId, "
			+ "sLtp.id AS srcVltpId, dLtp.id AS destVltpId, "
			+ "vlc.id AS vlcId, vlc.name AS vlcName, vlc.label AS vlcLabel, vlc.description AS vlcDescription, vlc.info AS vlcInfo, "
			+ "vlc.status AS vlcStatus, vlc.created AS vlcCreated, vlc.updated AS vlcUpdated, "
			+ "vlc.srcVctpId AS vlcSrcVctpId, vlc.destVctpId AS vlcDestVctpId, vlc.vlinkId AS vlcVlinkId, "
			+ "sLtp.id AS vlcSrcVltpId, dLtp.id AS vlcDestVltpId "
			+ "FROM (((Vlink "
			+ "INNER JOIN Vltp AS sLtp ON Vlink.srcVltpId=sLtp.id) "
			+ "INNER JOIN Vltp AS dLtp ON Vlink.destVltpId=dLtp.id) "
			+ "LEFT JOIN VlinkConn as vlc ON Vlink.id=vlc.vlinkId) "
			+ "WHERE Vlink.id = ? GROUP BY vlcId";

	// get a VlinkConn
	public static final String FETCH_VLINKCONN_BY_ID = "SELECT "
			+ "VlinkConn.id, VlinkConn.name, VlinkConn.label, VlinkConn.description, VlinkConn.info, VlinkConn.status, "
			+ "VlinkConn.created, VlinkConn.updated, VlinkConn.srcVctpId, VlinkConn.destVctpId, VlinkConn.vlinkId, "
			+ "sLtp.id AS srcVltpId, dLtp.id AS destVltpId, sLtp.vnodeId AS srcVnodeId, dLtp.vnodeId AS destVnodeId "
			+ "FROM ((VlinkConn "
			+ "INNER JOIN Vctp AS sCtp ON sCtp.id=srcVctpId INNER JOIN Vltp AS sLtp ON sLtp.id=sCtp.vltpId) "
			+ "INNER JOIN Vctp AS dCtp ON dCtp.id=destVctpId INNER JOIN Vltp AS dLtp ON dLtp.id=dCtp.vltpId) "
			+ "WHERE VlinkConn.id = ?";

	// get a Vtrail and its Vxcs
	public static final String FETCH_VTRAIL_BY_ID = "SELECT "
			+ "Vtrail.id, Vtrail.name, Vtrail.label, Vtrail.description, Vtrail.info, Vtrail.status, Vtrail.created, Vtrail.updated, "
			+ "Vtrail.srcVctpId, Vtrail.destVctpId, "
			+ "Vxc.id AS vxcId, Vxc.name AS vxcName, Vxc.label AS vxcLabel, Vxc.description AS vxcDescription, Vxc.info AS vxcInfo, "
			+ "Vxc.status AS vxcStatus, Vxc.created AS vxcCreated, Vxc.updated AS vxcUpdated, "
			+ "Vxc.type AS vxcType, Vxc.vtrailId AS vxcVtrailId, Vxc.srcVctpId AS vxcSrcVctpId, Vxc.destVctpId AS vxcDestVctpId, IFNULL(Vxc.dropVctpId, 0) AS vxcDropVctpId "
			+ "FROM Vtrail LEFT JOIN Vxc ON Vtrail.id=Vxc.vtrailId "
			+ "WHERE Vtrail.id = ?";

	// get a Vxc
	public static final String FETCH_VXC_BY_ID = "SELECT "
			+ "id, name, label, description, info, status, created, updated, type, vnodeId, vtrailId, srcVctpId, destVctpId, dropVctpId "
			+ "FROM Vxc WHERE id = ?"; 

	
	public static final String FETCH_PREFIX_ANN_BY_ID = "SELECT "
			+ "id, name, nodeId, strategy, status, created, updated "
			+ "FROM PrefixAnn WHERE id=?";
	
	public static final String FETCH_RTE_BY_ID = "SELECT "
			+ "Rte.id, Rte.prefixId, PrefixAnn.name AS prefixName, Rte.fromNodeId, Rte.nextHopId, Rte.cost, Rte.ctpId, Rte.status, Rte.created, Rte.updated "
			+ "FROM Rte INNER JOIN PrefixAnn on Rte.prefixId=PrefixAnn.id WHERE Rte.id=?";

	public static final String FETCH_FACE_BY_ID = "SELECT "
			+ "id, label, local, remote, scheme, created, updated, vctpId, vlinkConnId "
			+ "FROM Face WHERE id = ?"; 
	
	
	
	/*-------------------- DELETE ITEMS BY ID --------------------*/

	public static final String DELETE_VSUBNET = "DELETE FROM Vsubnet WHERE id=?";
	public static final String DELETE_VNODE = "DELETE FROM Vnode WHERE id=?";
	public static final String DELETE_VLTP = "DELETE FROM Vltp WHERE id=?";
	public static final String DELETE_VCTP = "DELETE FROM Vctp WHERE id=?";
	public static final String DELETE_VLINK = "DELETE FROM Vlink WHERE id=?";
	public static final String DELETE_VLINKCONN = "DELETE FROM VlinkConn WHERE id=?";
	public static final String DELETE_VTRAIL = "DELETE FROM Vtrail WHERE id=?";
	public static final String DELETE_VXC = "DELETE FROM Vxc WHERE id=?";
	
	public static final String DELETE_PREFIX_ANN = "DELETE FROM PrefixAnn WHERE id=?";
	public static final String DELETE_RTE = "DELETE FROM Rte WHERE id=?";
	
	public static final String DELETE_FACE = "DELETE FROM Face WHERE id=?";
	


	/*-------------------- UPDATE ITEMS BY ID --------------------*/
	// external references can not be modified
	
	public static final String UPDATE_VSUBNET = "UPDATE Vsubnet "
			+ "SET label=IFNULL(?, label), description=IFNULL(?, description), info=IFNULL(?, info), status=IFNULL(?, status) "
			+ "WHERE id = ?";
	public static final String UPDATE_VNODE = "UPDATE Vnode "
			+ "SET label=IFNULL(?, label), description=IFNULL(?, description), info=IFNULL(?, info), status=IFNULL(?, status), "
			+ "posx=IFNULL(?, posx), posy=IFNULL(?, posy), location=IFNULL(?, location), type=IFNULL(?, type) "
			+ "WHERE id = ?";
	public static final String UPDATE_VLTP = "UPDATE Vltp "
			+ "SET label=IFNULL(?, label), description=IFNULL(?, description), info=IFNULL(?, info), status=IFNULL(?, status), "
			+ "busy=IFNULL(?, busy) "
			+ "WHERE id = ?";
	public static final String UPDATE_VCTP = "UPDATE Vctp "
			+ "SET label=IFNULL(?, label), description=IFNULL(?, description), info=IFNULL(?, info), status=IFNULL(?, status), "
			+ "busy=IFNULL(?, busy) "
			+ "WHERE id = ?";
	public static final String UPDATE_VLINK = "UPDATE Vlink "
			+ "SET label=IFNULL(?, label), description=IFNULL(?, description), info=IFNULL(?, info), status=IFNULL(?, status), "
			+ "type=IFNULL(?, type) "
			+ "WHERE id = ?";
	public static final String UPDATE_VLINKCONN = "UPDATE VlinkConn "
			+ "SET label=IFNULL(?, label), description=IFNULL(?, description), info=IFNULL(?, info), status=IFNULL(?, status) "
			+ "WHERE id = ?";
	public static final String UPDATE_VTRAIL = "UPDATE Vtrail "
			+ "SET label=IFNULL(?, label), description=IFNULL(?, description), info=IFNULL(?, info), status=IFNULL(?, status) "
			+ "WHERE id = ?";
	public static final String UPDATE_VXC = "UPDATE Vxc "
			+ "SET label=IFNULL(?, label), description=IFNULL(?, description), info=IFNULL(?, info), status=IFNULL(?, status), "
			+ "type=IFNULL(?, type) "
			+ "WHERE id = ?";
	
	public static final String UPDATE_PREFIX_ANN = "UPDATE PrefixAnn "
			+ "SET status=IFNULL(?, status), name=IFNULL(?, name), strategy=IFNULL(?, strategy) "
			+ "WHERE id = ?";
		
	public static final String UPDATE_RTE = "UPDATE Rte "
			+ "SET status=IFNULL(?, status) "
			+ "WHERE id = ?";
}

package io.nms.central.microservice.topology.impl;

public class Sql {
	
	public static final String CREATE_TABLE_VSUBNET = "CREATE TABLE IF NOT EXISTS `Vsubnet` (\n" + 
			"    `id` VARCHAR(30) NOT NULL,\n" + 
			"    `label` VARCHAR(60),\n" + 
			"    `description` VARCHAR(255),\n" + 
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" + 
			"    PRIMARY KEY (`id`)\n" + 
			")";
	public static final String CREATE_TABLE_VNODE = "CREATE TABLE IF NOT EXISTS `Vnode` (\n" + 
			"    `id` VARCHAR(30) NOT NULL,\n" + 
			"    `label` VARCHAR(60),\n" + 
			"    `description` VARCHAR(255),\n" + 
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" + 
			"    `posx` INT,\n" + 
			"    `posy` INT,\n" + 
			"    `location` VARCHAR(20),\n" + 
			"    `type` VARCHAR(10),\n" + 
			"    `managed` BOOLEAN,\n" +
			"    `status` VARCHAR(10),\n" +
			"    `vsubnetId` VARCHAR(30),\n" + 
			"    PRIMARY KEY (`id`),\n" + 
			"    FOREIGN KEY (`vsubnetId`)\n" + 
			"    	REFERENCES Vsubnet(`id`)\n" + 
			"       ON DELETE CASCADE\n" + 
			"		ON UPDATE CASCADE\n" + 
			")";
	public static final String CREATE_TABLE_VLTP = "CREATE TABLE IF NOT EXISTS Vltp (\n" + 
			"    `id` VARCHAR(30) NOT NULL,\n" + 
			"    `label` VARCHAR(60),\n" + 
			"    `description` VARCHAR(255),\n" + 
			"    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" + 
			"    `status` VARCHAR(10) NOT NULL,\n" + 		
			"    `busy` BOOLEAN DEFAULT 0,\n" +
			"    `vnodeId` VARCHAR(30) NOT NULL,\n" + 
			"    PRIMARY KEY (`id`),\n" + 
			"    FOREIGN KEY (`vnodeId`) \n" + 
			"        REFERENCES Vnode(`id`)\n" + 
			"        ON DELETE CASCADE\n" + 
			"	ON UPDATE CASCADE\n" + 
			")";
	public static final String CREATE_TABLE_VCTP = "CREATE TABLE IF NOT EXISTS Vctp (\n" + 
			"    id VARCHAR(30) NOT NULL,\n" + 
			"    label VARCHAR(60),\n" + 
			"    description VARCHAR(255),\n" + 
			"    created DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" + 
			"    connType ENUM('VLAN', 'WAVELENGTH') NOT NULL,\n" + 
			"    connValue BIGINT NOT NULL,\n" + 
			"    status VARCHAR(10) NOT NULL,\n" +
			"    vltpId VARCHAR(30) NOT NULL,\n" + 
			"    vlinkId VARCHAR(30) NOT NULL,\n" + 
			"    PRIMARY KEY (id),\n" + 
			"    FOREIGN KEY (vltpId) \n" + 
			"       REFERENCES Vltp(id)\n" + 
			"       ON DELETE CASCADE\n" + 
			"		ON UPDATE CASCADE,\n" + 
			"    FOREIGN KEY (vlinkId) \n" + 
			"        REFERENCES Vlink(id)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE\n" + 
			")";
	public static final String CREATE_TABLE_VLINK = "CREATE TABLE IF NOT EXISTS Vlink (\n" + 
			"    id VARCHAR(30) NOT NULL,\n" + 
			"    label VARCHAR(60),\n" + 
			"    description VARCHAR(255),\n" + 
			"    created DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
			"    status VARCHAR(10) NOT NULL,\n" +
			"    speed VARCHAR(20),\n" + 
			"    srcVltpId VARCHAR(30) NOT NULL,\n" + 
			"    destVltpId VARCHAR(30) NOT NULL,\n" + 
			"    PRIMARY KEY (id),\n" + 
			"    FOREIGN KEY (srcVltpId) \n" + 
			"        REFERENCES Vltp(id)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE,\n" + 
			"    FOREIGN KEY (destVltpId) \n" + 
			"        REFERENCES Vltp(id)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE\n" + 
			")";
	public static final String CREATE_TABLE_VLINKCONN = "CREATE TABLE IF NOT EXISTS VlinkConn (\n" + 
			"    id VARCHAR(30) NOT NULL,\n" + 
			"    label VARCHAR(60),\n" + 
			"    description VARCHAR(255),\n" + 
			"    created DATETIME DEFAULT CURRENT_TIMESTAMP,\n" + 
			"    updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" + 
			"    srcVctpId VARCHAR(30) NOT NULL,\n" + 
			"    destVctpId VARCHAR(30) NOT NULL,\n" +
			"    status VARCHAR(10) NOT NULL,\n" +
			"    PRIMARY KEY (id),\n" + 
			"    FOREIGN KEY (srcVctpId) \n" + 
			"        REFERENCES Vctp(id)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE,\n" + 
			"    FOREIGN KEY (destVctpId) \n" + 
			"        REFERENCES Vctp(id)\n" + 
			"        ON DELETE CASCADE\n" + 
			"        ON UPDATE CASCADE\n" + 
			")";
	public static final String CREATE_TABLE_VTRAIL = "";
	public static final String CREATE_TABLE_VXC = "";
	
	public static final String INSERT_VSUBNET = "INSERT INTO Vsubnet (`id`, `label`, `description`) "
			+ "VALUES (?, ?, ?)";
	public static final String INSERT_VNODE = "INSERT INTO Vnode (`id`, `label`, `description`, `posx`, `posy`, `location`, `type`, `managed`, `status`, `vsubnetId`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VLTP = "INSERT INTO Vltp (id, label, description, status, busy, vnodeId) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VCTP = "INSERT INTO Vctp (id, label, description, connType, connValue, status, vltpId, vlinkId) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VLINK = "INSERT INTO Vlink (id, label, description, status, speed, srcVltpId, destVltpId) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VLINKCONN = "INSERT INTO VlinkConn (id, label, description, srcVctpId, destVctpId, status) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	public static final String INSERT_VTRAIL = "INSERT INTO Vtrail (id, label, description) "
			+ "VALUES (?, ?, ?)";
	public static final String INSERT_VXC = "INSERT INTO Vxc (id, label, description) "
			+ "VALUES (?, ?, ?)";
	
	
	/*---------------------------------------*/
	
	public static final String FETCH_VSUBNET = "SELECT "
			+ "id, label, description, created, updated FROM Vsubnet WHERE id = ?";
	
	public static final String FETCH_VNODE = "SELECT "
			+ "Vnode.id, Vnode.label, Vnode.description, Vnode.created, Vnode.updated, Vnode.posx, Vnode.posy, Vnode.location, "
			+ "Vnode.type, Vnode.managed, Vnode.status, Vnode.vsubnetId, "
			+ "Vltp.id AS vltpId, Vltp.label AS vltpLabel, Vltp.description AS vltpDescription, Vltp.status AS vltpStatus, Vltp.busy AS vltpBusy, "
			+ "Vltp.created AS vltpCreated, Vltp.updated AS vltpUpdated, Vltp.vnodeId AS vltpVnodeId "
			+ "FROM `Vnode` LEFT JOIN `Vltp` ON Vnode.id=Vltp.vnodeId WHERE Vnode.id = ? GROUP BY Vnode.id";
	
	public static final String FETCH_VLTP = "SELECT "
			+ "Vltp.id, Vltp.label, Vltp.description, Vltp.status, Vltp.busy, Vltp.created, Vltp.updated, Vltp.vnodeId, "
			+ "Vctp.id AS vctpId, Vctp.label AS vctpLabel, Vctp.description AS vctpDescription, Vctp.connType AS vctpConnType, Vctp.connValue AS vctpConnValue, "
			+ "Vctp.status AS vctpStatus, Vctp.vltpId AS vctpVltpId, Vctp.vlinkId AS vctpVlinkId, Vctp.created AS vctpCreated, Vctp.updated AS vctpUpdated "
			+ "FROM `Vltp` LEFT JOIN `Vctp` ON Vltp.id=Vctp.vltpId WHERE Vltp.id = ? GROUP BY Vltp.id";
	
	public static final String FETCH_VCTP = "SELECT "
			+ "id, label, description, connType, connValue, status, vltpId, vlinkId, created, updated "
			+ "FROM Vctp WHERE id = ?";
	
	public static final String FETCH_VLINK = "SELECT "
			+ "Vlink.id, Vlink.label, Vlink.description, Vlink.status, Vlink.speed, Vlink.srcVltpId, Vlink.destVltpId, Vlink.created, Vlink.updated, "
			+ "sltp.vnodeId AS srcVnodeId, dltp.vnodeId AS destVnodeId, "
			+ "vlc.id AS vlcId, vlc.label AS vlcLabel, vlc.description AS vlcDescription, vlc.srcVctpId AS vlcSrcVctpId, vlc.destVctpId AS vlcDestVctpId, "
			+ "vlc.status AS vlcStatus, vlc.created AS vlcCreated, vlc.updated AS vlcUpdated "
			+ "FROM (((Vlink "
			+ "INNER JOIN Vltp AS sltp ON Vlink.srcVltpId=sltp.id) "
			+ "INNER JOIN Vltp AS dltp ON Vlink.destVltpId=dltp.id) "
			+ "LEFT JOIN Vctp ON Vlink.id=Vctp.vlinkId LEFT JOIN VlinkConn as vlc ON Vctp.id=vlc.srcVctpId OR Vctp.id=vlc.destVctpId) "
			+ "WHERE Vlink.id = ? GROUP BY Vlink.id";
	
	public static final String FETCH_VLINKCONN = "SELECT "
			+ "id, label, description, srcVctpId, destVctpId, status, created, updated "
			+ "FROM VlinkConn WHERE id = ?";
	
	
	/*---------------------------------------*/
	
	public static final String FETCH_ALL_VSUBNETS = "SELECT "
			+ "id, label, description, created, updated "
			+ "FROM Vsubnet";
	
	public static final String FETCH_ALL_VNODES = "SELECT "
			+ "`id`, `label`, `description`, `posx`, `posy`, `location`, `type`, `managed`, `status`, `vsubnetId`, `created`, `updated` "
			+ "FROM Vnode";
	
	public static final String FETCH_ALL_VLTPS = "SELECT "
			+ "id, label, description, status, busy, vnodeId, created, updated "
			+ "FROM Vltp";
	
	public static final String FETCH_ALL_VCTPS = "SELECT "
			+ "id, label, description, connType, connValue, status, vltpId, vlinkId, created, updated "
			+ "FROM Vctp";
	
	public static final String FETCH_ALL_VLINKS = "SELECT "
			+ "Vlink.id, Vlink.label, Vlink.description, Vlink.status, Vlink.speed, Vlink.srcVltpId, Vlink.destVltpId, Vlink.created, Vlink.updated, "
			+ "s.vnodeId AS srcVnodeId, d.vnodeId AS destVnodeId "
			+ "FROM ((Vlink INNER JOIN Vltp AS s ON Vlink.srcVltpId=s.id) INNER JOIN Vltp AS d ON Vlink.destVltpId=d.id)";
	
	public static final String FETCH_ALL_VLINKCONNS = "SELECT "
			+ "id, label, description, srcVctpId, destVctpId, status, created, updated "
			+ "FROM VlinkConn";

	
	/*---------------------------------------*/
	
	public static final String FETCH_VNODES_BY_VSUBNET = "SELECT "
			+ "id, label, description, posX, posY, location, type, managed, status, vsubnetId, created, updated FROM Vnode WHERE vsubnetId = ?";
	
	public static final String FETCH_VLTPS_BY_VNODE = "SELECT "
			+ "id, label, description, status, busy, vnodeId, created, updated FROM Vltp WHERE vnodeId = ?";
	
	public static final String FETCH_VCTPS_BY_VLTP = "SELECT "
			+ "id, label, description, connType, connValue, status, vltpId, vlinkId, created, updated FROM Vctp WHERE vltpId = ?";
	
	public static final String FETCH_VCTPS_BY_VLINK = "SELECT "
			+ "id, label, description, connType, connValue, status, vltpId, vlinkId, created, updated FROM Vctp WHERE vlinkId = ?";
	
	/*---------------------------------------*/
	
	
	public static final String DELETE_VSUBNET = "DELETE FROM Vsubnet WHERE id=?";
	public static final String DELETE_VNODE = "DELETE FROM Vnode WHERE id=?";
	public static final String DELETE_VLTP = "DELETE FROM Vltp WHERE id=?";
	public static final String DELETE_VCTP = "DELETE FROM Vctp WHERE id=?";
	public static final String DELETE_VLINK = "DELETE FROM Vlink WHERE id=?";
	public static final String DELETE_VLINKCONN = "DELETE FROM VlinkConn WHERE id=?";
	// public static final String DELETE_VTRAIL = "DELETE FROM Vtrail WHERE id=?";
	// public static final String DELETE_VXC = "DELETE FROM Vxc WHERE id=?";

}

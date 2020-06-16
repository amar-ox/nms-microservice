package io.nms.central.microservice.topology.model;

import java.util.List;

import io.vertx.core.json.JsonObject;

public class ModelHelper {
	
	public static String check(Vsubnet vsubnet) {
		return "";
	}
	
	public static String check(Vnode vnode) {
		return "";
	}
	
	public static String check(Vltp vltp) {
		return "";
	}
	
	public static String check(Vctp vctp) {
		return "";
	}
	
	public static String check(Vlink vlink) {
		return "";
	}
	
	public static String check(VlinkConn vlinkConn) {
		return "";
	}
	
	/* "SELECT "
			+ "Vnode.id, Vnode.label, Vnode.description, Vnode.created, Vnode.updated, Vnode.posX, Vnode.posY, Vnode.location, "
			+ "Vnode.type, Vnode.managed, Vnode.status, Vnode.vsubnetId, "
			+ "Vltp.id AS vltpId, Vltp.label AS vltpLabel, Vltp.description AS vltpDescription, Vltp.status AS vltpStatus, Vltp.busy AS vltpBusy, "
			+ "Vltp.created AS vltpCreated, Vltp.updated AS vltpUpdated, Vltp.vnodeId AS vltpVnodeId "
			+ "FROM Vnode LEFT JOIN Vltp ON Vnode.id=Vltp.vnodeId WHERE Vnode.id = ? GROUP BY Vnode.id" */
	public static Vnode toVnodeFromJsonRows(List<JsonObject> rows) {
		if (rows.size() == 0) {
			return new Vnode();
		}
		Vnode vnode = new Vnode(rows.get(0).getString("id"));
		vnode.setLabel(rows.get(0).getString("label"));
		vnode.setDescription(rows.get(0).getString("description"));
		vnode.setCreated(rows.get(0).getString("created"));
		vnode.setUpdated(rows.get(0).getString("updated"));
		vnode.setPosx(rows.get(0).getInteger("posx"));
		vnode.setPosy(rows.get(0).getInteger("posy"));
		vnode.setLocation(rows.get(0).getString("location"));
		vnode.setType(rows.get(0).getString("type"));
		vnode.setManaged(rows.get(0).getBoolean("managed"));
		vnode.setStatus(rows.get(0).getString("status"));
		vnode.setVsubnetId(rows.get(0).getString("vsubnetId"));
		
		if (rows.get(0).getString("vltpId") == null) {
			return vnode;
		}
		rows.forEach(row -> {
			Vltp vltp = new Vltp(row.getString("vltpId"));
			vltp.setLabel(row.getString("vltpLabel"));
			vltp.setDescription(row.getString("vltpDescription"));
			vltp.setStatus(row.getString("vltpStatus"));
			vltp.setBusy(row.getBoolean("vltpBusy"));
			vltp.setCreated(row.getString("vlptCreated"));
			vltp.setUpdated(row.getString("vltpUpdated"));
			vltp.setVnodeId(row.getString("vltpVnodeId"));
			vnode.addVltp(vltp);
		});
		return vnode;
	}
	
	/* "SELECT "
			+ "Vlink.id, Vlink.label, Vlink.description, Vlink.status, Vlink.speed, Vlink.srcVltpId, Vlink.destVltpId, Vlink.created, Vlink.updated, "
			+ "s.vnodeId AS srcVnodeId, d.vnodeId AS destVnodeId, "
			+ "vlc.id AS vlcId, vlc.label AS vlcLabel, vlc.description AS vlcDescription, vlc.srcVctpId AS vlcSrcVctpId, vlc.destVctpId AS vlcDestVctpId, "
			+ "vlc.status AS vlcStatus, vlc.created AS vlcCreated, vlc.updated AS vlcUpdated "
			+ "FROM (((Vlink INNER JOIN Vltp AS s ON Vlink.srcVltpId=s.id) INNER JOIN Vltp AS d ON Vlink.destVltpId=d.id) LEFT JOIN VlinkConn AS vlc ON Vlink.id=vlc.vlinkId) "
			+ "WHERE Vlink.id = ?" */
	public static Vlink toVlinkFromJsonRows(List<JsonObject> rows) {
		if (rows.size() == 0) {
			return new Vlink();
		}
		Vlink vlink = new Vlink(rows.get(0).getString("id"));
		
		vlink.setLabel(rows.get(0).getString("label"));
		vlink.setDescription(rows.get(0).getString("description"));
		vlink.setStatus(rows.get(0).getString("status"));
		vlink.setSpeed(rows.get(0).getString("speed"));
		vlink.setSrcVltpId(rows.get(0).getString("srcVltpId"));
		vlink.setDestVltpId(rows.get(0).getString("destVltpId"));		
		vlink.setCreated(rows.get(0).getString("created"));
		vlink.setUpdated(rows.get(0).getString("updated"));
		vlink.setSrcVnodeId(rows.get(0).getString("srcVnodeId"));
		vlink.setDestVnodeId(rows.get(0).getString("destVnodeId"));
		
		if (rows.get(0).getString("vlcId") == null) {
			return vlink;
		}
		
		rows.forEach(row -> {
			VlinkConn vlc = new VlinkConn(row.getString("vlcId"));
			
			vlc.setLabel(row.getString("vlcLabel"));
			vlc.setDescription(row.getString("vlcDescription"));
			vlc.setSrcVctpId(row.getString("vlcSrcVctpId"));
			vlc.setDestVctpId(row.getString("vlcDestVctpId"));
			vlc.setStatus(row.getString("vlcStatus"));			
			vlc.setCreated(row.getString("vlcCreated"));
			vlc.setUpdated(row.getString("vlcUpdated"));
						
			vlink.addVlinkConn(vlc);
		});
		return vlink;
	}
	
	/* "SELECT "
			+ "Vltp.id, Vltp.label, Vltp.description, Vltp.status, Vltp.busy, Vltp.created, Vltp.updated, Vltp.vnodeId, "
			+ "Vctp.id AS vctpId, Vctp.label AS vctpLabel, Vctp.description AS vctpDescription, Vctp.connType AS vctpConnType, Vctp.connValue AS vctpConnValue, "
			+ "Vctp.status AS vctpStatus, Vctp.vltpId AS vctpVltpId, Vctp.vlinkId AS vctpVlinkId, Vctp.created AS vctpCreated, Vctp.updated AS vctpUpdated"
			+ "FROM Vltp INNER JOIN Vctp ON Vltp.id=Vctp.vltpId WHERE Vltp.id = ?" */
	public static Vltp toVltpFromJsonRows(List<JsonObject> rows) {
		if (rows.size() == 0) {
			return new Vltp();
		}
		Vltp vltp = new Vltp(rows.get(0).getString("id"));
		vltp.setLabel(rows.get(0).getString("label"));
		vltp.setDescription(rows.get(0).getString("description"));
		vltp.setStatus(rows.get(0).getString("status"));
		vltp.setBusy(rows.get(0).getBoolean("busy"));
		vltp.setCreated(rows.get(0).getString("created"));
		vltp.setUpdated(rows.get(0).getString("updated"));
		vltp.setVnodeId(rows.get(0).getString("vnodeId"));
		
		if (rows.get(0).getString("vctpId") == null) {
			return vltp;
		}
		
		rows.forEach(row -> {
			Vctp vctp = new Vctp(row.getString("vctpId"));
			vctp.setLabel(row.getString("vctpLabel"));
			vctp.setDescription(row.getString("vctpDescription"));
			vctp.setConnType(row.getString("vctpConnType"));
			vctp.setConnValue(row.getLong("vctpConnValue"));
			vctp.setStatus(row.getString("vctpStatus"));
			vctp.setVltpId(row.getString("vctpVltpId"));
			vctp.setVlinkId(row.getString("vctpVlinkId"));
			vctp.setCreated(row.getString("vctpCreated"));
			vctp.setUpdated(row.getString("vctpUpdated"));
						
			vltp.addVctp(vctp);
		});
		return vltp;
	}
}

package io.nms.central.microservice.topology.model;

import java.util.List;

import io.vertx.core.json.JsonObject;

public class ModelObjectMapper {
	/* FETCH_VNODE_BY_ID = "SELECT "
			+ "Vnode.id, Vnode.name, Vnode.label, Vnode.description, Vnode.info, Vnode.status, Vnode.created, Vnode.updated, "
			+ "Vnode.posx, Vnode.posy, Vnode.location, Vnode.type, Vnode.vsubnetId "
			+ "Vltp.id AS vltpId, Vltp.name AS VltpName, Vltp.label AS vltpLabel, Vltp.description AS vltpDescription, Vltp.info AS vltpInfo, "
			+ "Vltp.status AS vltpStatus, Vltp.created AS vltpCreated, Vltp.updated AS vltpUpdated, "
			+ "Vltp.busy AS vltpBusy, Vltp.vnodeId AS vltpVnodeId "
			+ "FROM `Vnode` LEFT JOIN `Vltp` ON Vnode.id=Vltp.vnodeId WHERE Vnode.id = ? GROUP BY Vnode.id" */
	public static Vnode toVnodeFromJsonRows(List<JsonObject> rows) {
		if (rows.size() == 0) {
			return new Vnode();
		}
		Vnode vnode = new Vnode(rows.get(0).getInteger("id"));
		vnode.setName(rows.get(0).getString("name"));
		vnode.setLabel(rows.get(0).getString("label"));
		vnode.setDescription(rows.get(0).getString("description"));
		vnode.setInfo(new JsonObject(rows.get(0).getString("info")).getMap());
		vnode.setStatus(rows.get(0).getString("status"));
		vnode.setCreated(rows.get(0).getString("created"));
		vnode.setUpdated(rows.get(0).getString("updated"));
		vnode.setPosx(rows.get(0).getInteger("posx"));
		vnode.setPosy(rows.get(0).getInteger("posy"));
		vnode.setLocation(rows.get(0).getString("location"));
		vnode.setType(rows.get(0).getString("type"));
		vnode.setVsubnetId(rows.get(0).getInteger("vsubnetId"));
		
		if (rows.get(0).getInteger("vltpId") == null) {
			return vnode;
		}
		rows.forEach(row -> {
			Vltp vltp = new Vltp(row.getInteger("vltpId"));
			vltp.setName(row.getString("vltpName"));
			vltp.setLabel(row.getString("vltpLabel"));
			vltp.setDescription(row.getString("vltpDescription"));
			vltp.setInfo(new JsonObject(rows.get(0).getString("vltpInfo")).getMap());
			vltp.setStatus(row.getString("vltpStatus"));			
			vltp.setCreated(row.getString("vlptCreated"));
			vltp.setUpdated(row.getString("vltpUpdated"));
			vltp.setBusy(row.getBoolean("vltpBusy"));
			vltp.setVnodeId(row.getInteger("vltpVnodeId"));			
			
			vnode.addVltp(vltp);
		});
		return vnode;
	}
	
	/* FETCH_VLTP_BY_ID = "SELECT "
			+ "Vltp.id, Vltp.name, Vltp.label, Vltp.description, Vltp.info, Vltp.status, Vltp.created, Vltp.updated, Vltp.busy, Vltp.vnodeId, "
			+ "Vctp.id AS vctpId, Vctp.name AS vctpName, Vctp.label AS vctpLabel, Vctp.description AS vctpDescription, "
			+ "Vctp.info AS vctpInfo, Vctp.status AS vctpStatus, Vctp.created AS vctpCreated, Vctp.updated AS vctpUpdated"
			+ "Vctp.vltpId AS vctpVltpId, "
			+ "FROM `Vltp` LEFT JOIN `Vctp` ON Vltp.id=Vctp.vltpId WHERE Vltp.id = ? GROUP BY Vltp.id" */
	public static Vltp toVltpFromJsonRows(List<JsonObject> rows) {
		if (rows.size() == 0) {
			return new Vltp();
		}
		Vltp vltp = new Vltp(rows.get(0).getInteger("id"));
		vltp.setName(rows.get(0).getString("name"));
		vltp.setLabel(rows.get(0).getString("label"));
		vltp.setDescription(rows.get(0).getString("description"));
		vltp.setInfo(new JsonObject(rows.get(0).getString("info")).getMap());
		vltp.setStatus(rows.get(0).getString("status"));		
		vltp.setCreated(rows.get(0).getString("created"));
		vltp.setUpdated(rows.get(0).getString("updated"));
		vltp.setBusy(rows.get(0).getBoolean("busy"));
		vltp.setVnodeId(rows.get(0).getInteger("vnodeId"));		
		
		if (rows.get(0).getInteger("vctpId") == null) {
			return vltp;
		}
		
		rows.forEach(row -> {
			Vctp vctp = new Vctp(row.getInteger("vctpId"));
			vctp.setName(rows.get(0).getString("vctpName"));
			vctp.setLabel(row.getString("vctpLabel"));
			vctp.setDescription(row.getString("vctpDescription"));
			vctp.setInfo(new JsonObject(row.getString("vctpInfo")).getMap());			
			vctp.setStatus(row.getString("vctpStatus"));
			vctp.setCreated(row.getString("vctpCreated"));
			vctp.setUpdated(row.getString("vctpUpdated"));
			vctp.setVltpId(row.getInteger("vctpVltpId"));
			
			vltp.addVctp(vctp);
		});
		return vltp;
	}
	
	/* FETCH_VLINK_BY_ID = "SELECT "
			+ "Vlink.id, Vlink.name, Vlink.label, Vlink.description, Vlink.info, Vlink.status, Vlink.created, Vlink.updated, "
			+ "Vlink.type, Vlink.srcVltpId, Vlink.destVltpId, "
			+ "sLtp.vnodeId AS srcVnodeId, dLtp.vnodeId AS destVnodeId, "
			+ "sLtp.id AS srcVltpId, dLtp.id AS destVltpId, "
			+ "vlc.id AS vlcId, vlc.name AS vlcName, vlc.label AS vlcLabel, vlc.description AS vlcDescription, vlc.info AS vlcInfo, "
			+ "vlc.status AS vlcStatus, vlc.created AS vlcCreated, vlc.updated AS vlcUpdated, "
			+ "vlc.srcVctpId AS vlcSrcVctpId, vlc.destVctpId AS vlcDestVctpId, "
			+ "FROM (((Vlink "
			+ "INNER JOIN Vltp AS sLtp ON Vlink.srcVltpId=sLtp.id) "
			+ "INNER JOIN Vltp AS dLtp ON Vlink.destVltpId=dLtp.id) "
			+ "LEFT JOIN Vctp AS allVctps ON sLtp.id=Vctp.vltpId OR dLtp.id=Vctp.vltpId "
			+ "LEFT JOIN VlinkConn as vlc ON allVctps.id=vlc.srcVctpId OR allVctps.id=vlc.destVctpId) "
			+ "WHERE Vlink.id = ? GROUP BY vlcId" */
	public static Vlink toVlinkFromJsonRows(List<JsonObject> rows) {
		if (rows.size() == 0) {
			return new Vlink();
		}
		Vlink vlink = new Vlink(rows.get(0).getInteger("id"));
		vlink.setName(rows.get(0).getString("name"));
		vlink.setLabel(rows.get(0).getString("label"));
		vlink.setDescription(rows.get(0).getString("description"));
		vlink.setInfo(new JsonObject(rows.get(0).getString("info")).getMap());
		vlink.setStatus(rows.get(0).getString("status"));
		vlink.setCreated(rows.get(0).getString("created"));
		vlink.setUpdated(rows.get(0).getString("updated"));
		vlink.setType(rows.get(0).getString("type"));
		vlink.setSrcVltpId(rows.get(0).getInteger("srcVltpId"));
		vlink.setDestVltpId(rows.get(0).getInteger("destVltpId"));
		vlink.setSrcVnodeId(rows.get(0).getInteger("srcVnodeId"));
		vlink.setDestVnodeId(rows.get(0).getInteger("destVnodeId"));		
		
		if (rows.get(0).getInteger("vlcId") == null) {
			return vlink;
		}

		rows.forEach(row -> {
			VlinkConn vlc = new VlinkConn(row.getInteger("vlcId"));
			vlc.setName(rows.get(0).getString("vlcName"));
			vlc.setLabel(row.getString("vlcLabel"));
			vlc.setDescription(row.getString("vlcDescription"));
			vlc.setInfo(new JsonObject(row.getString("vlcInfo")).getMap());
			vlc.setStatus(row.getString("vlcStatus"));
			vlc.setCreated(row.getString("vlcCreated"));
			vlc.setUpdated(row.getString("vlcUpdated"));
			vlc.setSrcVctpId(row.getInteger("vlcSrcVctpId"));
			vlc.setDestVctpId(row.getInteger("vlcDestVctpId"));

			vlink.addVlinkConn(vlc);
		});
		return vlink;
	}
	
	
	/* FETCH_VTRAIL_BY_ID = "SELECT "
			+ "Vtrail.id, Vtrail.name, Vtrail.label, Vtrail.description, Vtrail.info, Vtrail.status, Vtrail.created, Vtrail.updated, "
			+ "Vtrail.srcVctpId, Vtrail.destVctpId, "
			+ "Vxc.id AS vxcId, Vxc.name AS vxcName, Vxc.label AS vxcLabel, Vxc.description AS vxcDescription, Vxc.info AS vxcInfo, "
			+ "Vxc.status AS vxcStatus, Vxc.created AS vxcCreated, Vxc.updated AS vxcUpdated, "
			+ "Vxc.type AS vxcType, Vxc.vtrailId AS vxcVtrailId, Vxc.srcVctpId AS vxcSrcVctpId, Vxc.destVctpId AS vxcDestVctpId, Vxc.dropVctpId AS vxcDropVctpId "
			+ "FROM Vtrail LEFT JOIN Vxc ON Vtrail.id=Vxc.vtrailId"
			+ "WHERE id = ? GROUP BY Vxc.id" */
	public static Vtrail toVtrailFromJsonRows(List<JsonObject> rows) {
		if (rows.size() == 0) {
			return new Vtrail();
		}
		Vtrail vtrail = new Vtrail(rows.get(0).getInteger("id"));
		vtrail.setName(rows.get(0).getString("name"));
		vtrail.setLabel(rows.get(0).getString("label"));
		vtrail.setDescription(rows.get(0).getString("description"));
		vtrail.setInfo(new JsonObject(rows.get(0).getString("info")).getMap());
		vtrail.setStatus(rows.get(0).getString("status"));				
		vtrail.setCreated(rows.get(0).getString("created"));
		vtrail.setUpdated(rows.get(0).getString("updated"));
		vtrail.setSrcVctpId(rows.get(0).getInteger("srcVctpId"));
		vtrail.setDestVctpId(rows.get(0).getInteger("destVctpId"));
		
		if (rows.get(0).getInteger("vxcId") == null) {
			return vtrail;
		}
		
		rows.forEach(row -> {
			Vxc vxc = new Vxc(row.getInteger("vxcId"));
			vxc.setName(rows.get(0).getString("vxcName"));
			vxc.setLabel(row.getString("vxcLabel"));
			vxc.setDescription(row.getString("vxcDescription"));
			vxc.setInfo(new JsonObject(row.getString("vxcInfo")).getMap());			
			vxc.setStatus(row.getString("vxcStatus"));
			vxc.setCreated(row.getString("vxcCreated"));
			vxc.setUpdated(row.getString("vxcUpdated"));
			vxc.setType(row.getString("vxcType"));
			vxc.setVtrailId(row.getInteger("vxcVtrailId"));
			vxc.setSrcVctpId(row.getInteger("vxcSrcVctpId"));
			vxc.setDestVctpId(row.getInteger("vxcDestVctpId"));
			vxc.setDropVctpId(row.getInteger("vxcDropVctpId"));
						
			vtrail.addVxc(vxc);
		});
		return vtrail;
	}
}

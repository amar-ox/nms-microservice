package io.nms.central.microservice.topology.model;

public class ModelObjectMapper {
	/* public static Vnode toVnodeFromJsonRows(List<JsonObject> rows) {
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
			vltp.setCreated(row.getString("vltpCreated"));
			vltp.setUpdated(row.getString("vltpUpdated"));
			vltp.setBusy(row.getBoolean("vltpBusy"));
			vltp.setVnodeId(row.getInteger("vltpVnodeId"));			
			
			vnode.addVltp(vltp);
		});
		return vnode;
	}
	
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
			vctp.setCreated(row.getString("vctpCreated"));
			vctp.setUpdated(row.getString("vctpUpdated"));
			vctp.setVltpId(row.getInteger("vctpVltpId"));
			
			vltp.addVctp(vctp);
		});
		return vltp;
	}
	
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
			vlc.setVlinkId(row.getInteger("vlcVlinkId"));

			vlink.addVlinkConn(vlc);
		});
		return vlink;
	} */
}

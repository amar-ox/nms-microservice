package io.nms.central.microservice.fault.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.nms.central.microservice.common.service.JdbcRepositoryWrapper;
import io.nms.central.microservice.fault.FaultService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 *
 */
public class FaultServiceImpl extends JdbcRepositoryWrapper implements FaultService {

	// private static final Logger logger = LoggerFactory.getLogger(FaultServiceImpl.class);

	public FaultServiceImpl(Vertx vertx, JsonObject config) {
		super(vertx, config);
	}

	@Override
	public FaultService initializePersistence(Handler<AsyncResult<List<Integer>>> resultHandler) {
		List<String> statements = new ArrayList<String>();
		statements.add(Sql.CREATE_TABLE_TEST);		
		client.getConnection(connHandler(resultHandler, connection -> {
			connection.batch(statements, r -> {
				resultHandler.handle(r);
				connection.close();
			});
		}));
		return this;
	}

	
	// Example
	/********** Vsubnet **********/
	// INSERT_VSUBNET = "INSERT INTO Vsubnet (name, label, description, info, status) "
	/* @Override
	public TopologyService addVsubnet(Vsubnet vsubnet, Handler<AsyncResult<Void>> resultHandler) {
		// logger.debug("addSubnet: "+vsubnet.toString());
		JsonArray params = new JsonArray()
				.add(vsubnet.getName())
				.add(vsubnet.getLabel())
				.add(vsubnet.getDescription())
				.add(new JsonObject(vsubnet.getInfo()).encode())
				.add(vsubnet.getStatus());
		executeNoResult(params, Sql.INSERT_VSUBNET, resultHandler);
		return this;
	}
	@Override
	public TopologyService getVsubnet(String vsubnetId, Handler<AsyncResult<Vsubnet>> resultHandler) {
		this.retrieveOne(vsubnetId, Sql.FETCH_VSUBNET_BY_ID)
		.map(option -> option.map(json -> {
			Vsubnet vsubnet = new Vsubnet(json);
			vsubnet.setInfo(new JsonObject(json.getString("info")).getMap());
			return vsubnet;
		}).orElse(null))
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService getAllVsubnets(Handler<AsyncResult<List<Vsubnet>>> resultHandler) {
		this.retrieveAll(Sql.FETCH_ALL_VSUBNETS)
		.map(rawList -> rawList.stream()
				.map(row -> {
					Vsubnet vsubnet = new Vsubnet(row);
					vsubnet.setInfo(new JsonObject(row.getString("info")).getMap());
					return vsubnet;
				})
				.collect(Collectors.toList())
				)
		.onComplete(resultHandler);
		return this;
	}
	@Override
	public TopologyService deleteVsubnet(String vsubnetId, Handler<AsyncResult<Void>> resultHandler) {
		this.removeOne(vsubnetId, Sql.DELETE_VSUBNET, resultHandler);
		return this;
	}
	// UPDATE_VSUBNET = "UPDATE Vsubnet SET label = ?, description = ?, info = ?, status = ? WHERE id = ?";
	@Override
	public TopologyService updateVsubnet(String id, Vsubnet vsubnet, Handler<AsyncResult<Vsubnet>> resultHandler) {
		JsonArray params = new JsonArray()
				.add(vsubnet.getLabel())
				.add(vsubnet.getDescription())
				.add(new JsonObject(vsubnet.getInfo()).encode())
				.add(vsubnet.getStatus())
				.add(id);
		this.execute(params, Sql.UPDATE_VSUBNET, vsubnet, resultHandler);
		return this;
	} */
}

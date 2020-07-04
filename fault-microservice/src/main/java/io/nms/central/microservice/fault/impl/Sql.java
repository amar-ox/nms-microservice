package io.nms.central.microservice.fault.impl;

public class Sql {

	
	
	/*-------------------- TABLE CREATION --------------------*/
	public static final String CREATE_TABLE_TEST = "CREATE TABLE IF NOT EXISTS `Test` (\n" +
			"    `id` INT NOT NULL AUTO_INCREMENT,\n" +
			"    PRIMARY KEY (`id`)\n" +
			")";
	
	/*-------------------- INSERT ITEMS --------------------*/
	
	public static final String INSERT_VSUBNET = "INSERT INTO Prefix (name, label, description, info, status) "
			+ "VALUES (?, ?, ?, ?, ?)";

	
	/*-------------------- FETCH ALL ITEMS --------------------*/
	
	// get all Prefixes
	public static final String FETCH_ALL_PREFIXESS = "SELECT "
			+ "id, name, label, description, info, status, created, updated "
			+ "FROM Prefix";

	/*-------------------- FETCH ITEMS BY ANOTHER --------------------*/
	


	/*-------------------- FETCH ITEMS BY ID --------------------*/

	// get a Prefix 
	public static final String FETCH_PREFIX_BY_ID = "SELECT "
			+ "id, name, label, description, info, status, created, updated FROM Prefix "
			+ "WHERE id = ?";


	
	/*-------------------- DELETE ITEMS BY ID --------------------*/

	public static final String DELETE_VSUBNET = "DELETE FROM Prefix WHERE id=?";


	/*-------------------- UPDATE ITEMS BY ID --------------------*/
	// external references can not be modified	
	public static final String UPDATE_VSUBNET = "UPDATE Prefix "
			+ "SET label=IFNULL(?, label), description=IFNULL(?, description), info=IFNULL(?, info), status=IFNULL(?, status) "
			+ "WHERE id = ?";	
}

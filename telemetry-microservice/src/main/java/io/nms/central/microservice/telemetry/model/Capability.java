package io.nms.central.microservice.telemetry.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * <h1>Capability</h1>
 * This class implements the Capability message type.
 * extends Message class.
 * Renames the verb field to "capability".
 */
public class Capability extends Message {
	@JsonProperty("capability")
	protected String verb;

	/**
	 * The default constructor creates an empty Capability message.
	 */
	public Capability() {
		super();
		msgType = Message.Type.CAPABILITY;
	}
	
	public static List<Capability> toListfromString(String str) {
		ObjectMapper mapper = new ObjectMapper();
		List<Capability> ml = Arrays.asList();
		try {
			ml = Arrays.asList(mapper.readValue(str, Capability[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ml;
	}
	
	public static String toStringFromList(List<Capability> msgList) {
		ObjectMapper mapper = new ObjectMapper();
		String str = "[]";
		try {
			str = mapper.writeValueAsString(msgList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return str;
	}
  
	/**
	 * Redefinition to hide "content" field in the JSON representation.
	 */	
	@JsonIgnore
	public Map<String, String> getContent() {
		return content;
	}
	
	/**
	 * Redefinition to hide "errors" field in the JSON representation.
	 */
	@JsonIgnore
	public List<String> getErrors() {
		return errors;
	}
}

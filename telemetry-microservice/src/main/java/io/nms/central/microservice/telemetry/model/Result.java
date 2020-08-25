package io.nms.central.microservice.telemetry.model;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * <h1>Result</h1>
 * This class implements the Result message type.
 * extends Message class.
 * Renames the verb field to "result".
 */
public class Result extends Message {
	@JsonProperty("result")
	protected String verb;
  
	/**
	 * The default constructor creates an empty Result message.
	 */
	public Result() {
		super();
		msgType = Message.Type.RESULT;
	}
  
	/**
	 * Creates a Result from a generic Message.
	 * @param msg The message to copy from.
	 */
	public Result(Message msg) {
		super(msg);
		msgType = Message.Type.RESULT;
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

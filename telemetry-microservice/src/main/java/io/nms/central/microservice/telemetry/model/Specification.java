package io.nms.central.microservice.telemetry.model;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * <h1>Specification</h1>
 * This class implements the Specification message type.
 * extends Message class.
 * Renames the verb field to "specification".
 */
public class Specification extends Message {
	@JsonProperty("specification")
	protected String verb;
	
	/**
	 * The default constructor creates an empty Specification message.
	 */
	public Specification() {
		super();
		msgType = Message.Type.SPECIFICATION;
	}
  
	/**
	 * Creates a Specification from a generic Message.
	 * @param msg The message to copy from.
	 */
	public Specification(Message msg) {
		super(msg);
		msgType = Message.Type.SPECIFICATION;
		verb = msg.getVerb();
	}
	
	public void setParameter(String key, String value) {
		if (parameters.containsKey(key)) {
			parameters.put(key, value);
		}
	}
	
	/**
	 * Verifies if all expected fields are correctly defined.
	 * Should be called after message creation.
	 * Overrides parent method and checks fields required for Specification. 
	 * Updates errors field if errors are found.
	 * @return boolean True on success False on error.
	 * @see NMS document for fields definition.
	 */
	@JsonIgnore
	public boolean check() {
		if (super.check()) {
			if (results.isEmpty()) {
				this.errors.add(Error.RESULTS_EMPTY);
				return false;
			}
			if (token.isEmpty()) {
         	this.errors.add(Error.TOKEN_EMPTY);
         	return false;
			}
			return true;
		}
		return false;
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

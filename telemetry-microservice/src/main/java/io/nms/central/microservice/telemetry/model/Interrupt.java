package io.nms.central.microservice.telemetry.model;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * <h1>Interrupt</h1>
 * This class implements the Interrupt message type.
 * extends Message class.
 * Renames the verb field to "interrupt".
 */
public class Interrupt extends Message {
	@JsonProperty("interrupt")
	protected String verb;
	
	/**
	 * The default constructor creates an empty Interrupt message.
	 */
	public Interrupt() {
		super();
		msgType = Message.Type.INTERRUPT;
	}
  
	/**
	 * Creates an Interrupt from a generic Message.
	 * @param msg The message to copy from.
	 */
	public Interrupt(Message msg) {
		super(msg);
		msgType = Message.Type.INTERRUPT;
		verb = msg.getVerb();
	}
	
	/**
	 * Verifies if all expected fields are correctly defined.
	 * Should be called after message creation.
	 * Overrides parent method and checks fields required for Interrupt. 
	 * Updates errors field if errors are found.
	 * @return boolean True on success False on error.
	 * @see NMS document for fields definition.
	 */
	@JsonIgnore
	public boolean check() {
		if (super.check()) {
			if (parameters.isEmpty()) {
				this.errors.add(Error.PARAMS_EMPTY);
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

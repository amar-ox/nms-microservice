package io.nms.central.microservice.telemetry.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * <h1>Receipt</h1>
 * This class implements the Receipt message type.
 * extends Message class.
 * Renames the verb field to "receipt".
 */
public class Receipt extends Message {
	@JsonProperty("receipt")
	protected String verb;
	
	/**
	 * The default constructor creates an empty Receipt message.
	 */
	public Receipt() {
		super();
		msgType = Message.Type.RECEIPT;
	}
  
	/**
	 * Creates a Receipt from a generic Message.
	 * @param msg The message to copy from.
	 */
	public Receipt(Message msg) {
		super(msg);
		msgType = Message.Type.RECEIPT;
	}
	
	public static List<Receipt> toRctListfromString(String str) {
		ObjectMapper mapper = new ObjectMapper();
		List<Receipt> ml = Arrays.asList();
		try {
			ml = Arrays.asList(mapper.readValue(str, Receipt[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ml;
	}
	
	public static String toStringFromRctList(List<Receipt> msgList) {
		ObjectMapper mapper = new ObjectMapper();
		String str = "[]";
		try {
			str = mapper.writeValueAsString(msgList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return str;
	}
}

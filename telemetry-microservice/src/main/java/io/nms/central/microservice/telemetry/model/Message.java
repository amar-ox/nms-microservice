package io.nms.central.microservice.telemetry.model;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;


/**
 * <h1>Message</h1>
 * This class implements the generic NMS 
 * message format and common operations.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
	
	/* message data */
	protected String verb;
	protected String label;
	protected String token;
	protected String endpoint;
	protected String when;
	protected String name;
	protected Map<String, String> parameters;
	protected List<String> results;
	protected List<List<String>> resultValues;
	protected List<String> errors = new ArrayList<String>();
	protected Map<String, String> content;
	
	/* metadata */
	protected String schema = "";
	protected String timestamp = "";
	protected String agentId = "";
	protected String role = "";
	
	/* internal fields */
	protected int msgType;
	protected Date dStart;
	protected Date dStop;
	protected int period = 0;
	
	/**
	 * The default constructor creates an empty message 
	 * with undefined type.
	 */
	public Message() {
		//this.id = COUNTER.getAndIncrement();
		this.msgType = Type.UNDEFINED;
	}
  
	/**
	 * Creates a message from another one.
	 * @param msg The message to copy from.
	 */
	public Message(Message msg) {
		this.msgType = msg.getMsgType();
		this.verb = msg.getVerb();
		this.label = msg.getLabel();
		this.when = msg.getWhen();
		this.name = msg.getName();
		this.parameters = msg.getParameters();
		this.results = msg.getResults();
		this.errors = msg.getErrors();
		this.endpoint = msg.getEndpoint();
		this.token = msg.getToken();
		this.resultValues = msg.getResultValues();
				
		this.agentId = msg.agentId;
		this.role = msg.role;
		this.setSchema();
	}
  
	/**
	 * Creates a new Message from a valid JSON representation.
	 * The message type is created accordingly. 
	 * @param str The JSON string.
	 * @return Message Type should be checked and cast accordingly.
	 * @see getMessageType().
	 * @see NMS document for JSON representation.
	 */
	public static Message fromJsonString(String str) {
		ObjectMapper mapper = new ObjectMapper();
		Message msg = null;
		try {
			if (str.contains("specification")) {
				msg = mapper.readValue(str, Specification.class);
			} else if (str.contains("interrupt")) {
				msg = mapper.readValue(str, Interrupt.class);
			} else if (str.contains("capability")) {
				msg = mapper.readValue(str, Capability.class);
			} else if (str.contains("receipt")) {
				msg = mapper.readValue(str, Receipt.class);
			} else if (str.contains("result")) {
				msg = mapper.readValue(str, Result.class);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public static <T> T fromJsonString(String str, Class<T> clazz) {
		// ObjectMapper mapper = new ObjectMapper();
		ObjectMapper mapper = DatabindCodec.mapper();
		try {
			return (T) mapper.readValue(str, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* ------------------- last updates -------------------- */	
	public void setParameter(String key, String value) {
		parameters.put(key, value);
	}
	
	public void setContent(String key, String value) {
		content.put(key, value);
	}
	
	public String getContent(String key) {
		return content.getOrDefault(key, "");
	}
	
	public String getParameter(String key) {
		return parameters.getOrDefault(key, "");
	}
	
	public void clearParameters() {
		parameters.clear();
	}
	
	public void clearContent() {
		content.clear();
	}
	
	public String getTimestamp() {
		return this.timestamp;
	}
	
	public String getAgentId() {
		return this.agentId;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getSchema() {
		if (schema.isEmpty()) {
			setSchema();
		}
		return this.schema;
	}
	
	public void setTimestampNow() {
		Timestamp ts = new Timestamp(new Date().getTime());
		this.timestamp = ts.toString();
	}
	
	/* 
	 * Uniquely identifies a measurement 
	 */
	public void setSchema() {		
		this.schema = getMd5(name + endpoint + agentId);
	}
	/* --------------------------------------------------- */
	
	@Override
	public String toString() {
		// ObjectMapper mapper = new ObjectMapper();
		ObjectMapper mapper = DatabindCodec.mapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "{}";
		}
	}
	
	public JsonObject toJson() {
		// ObjectMapper mapper = new ObjectMapper();
		ObjectMapper mapper = DatabindCodec.mapper();
		try {
			return (JsonObject) Json.decodeValue(mapper.writeValueAsString(this));
		} catch (DecodeException | JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
  
	/**
	 * Verifies if all expected fields are 
	 * correctly defined in the message.
	 * Should be called after message creation.
	 * Checks only the fields required for all message types. 
	 * Updates errors field if errors are found.
	 * @return boolean True on success False on error.      
	 * @see NMS document for fields definition.
	 */
	@JsonIgnore
	public boolean check() {
		errors.clear();
		/* required: msgType */
		if (msgType <= Type.UNDEFINED) {
			this.errors.add(Error.TYPE_UNSUPPORTED);
			return false;
		}
		/* required: verb */
		if (verb.isEmpty()) {
			verb = "<undefined>";
			this.errors.add(Error.VERB_EMPTY);
			return false;
		}
		/* required: endpoint */
		if (endpoint.isEmpty()) {
			endpoint = "<undefined>";	
			this.errors.add(Error.ENDPOINT_EMPTY);
			return false;
		}
		/* required: name */
		if (name.isEmpty()) {
			name = "<undefined>";
			this.errors.add(Error.NAME_EMPTY);
			return false;
		}
		/* required: when */
		if (when.isEmpty()) {
			when = "<undefined>";
			this.errors.add(Error.WHEN_EMPTY);
			return false;
		}
		return parseWhen();
	}
	
	/**
	 * Verifies if the message should be processed immediately AND only once 
	 * according to the temporal scope field.
	 * Used for Specification and Interrupt processing.
	 * @return boolean. True if the message must be executed immediately AND once.
	 */
	@JsonIgnore
	public boolean isOneNow() {
		return ((dStart == null) && (dStop == null));
	}
  
	/**
	 * Verifies if the message should be processed later AND only once 
	 * according to the temporal scope field.
	 * Used for Specification and Interrupt processing.
	 * @return boolean. True if the message must be executed later AND once.
	 */
	@JsonIgnore
	public boolean isFuture() {
		// if dStart is 'now'
		if (dStart == null) {
			return false;
		}
		return dStart.after(new Date());
	}
  
	/**
	 * Verifies if the message should be executed periodically 
	 * according to the temporal scope field.
	 * Used for Specification and Interrupt processing.
	 * @return boolean. True if the message must be executed periodically.
	 */
	@JsonIgnore
	public boolean isPeriodic() {
		return ((dStop != null) && (period > 0));
	}
  
	/**
	 * Gives the period of the message.  
	 * Used for Specification and Interrupt processing.
	 * @return int. Period duration in milliseconds.
	 */
	@JsonIgnore
	public int getPeriod() {
		return period;
	}
  
	/**
	 * Gives the time at which the message should be executed.
	 * Used for Specification and Interrupt processing.
	 * @return Date. The date and time of execution.
	 */
	@JsonIgnore
	public Date getStart() {
		if (dStart == null) {
			return new Date();
		}
		return dStart;
	}
  
	/**
	 * Gives the time at which the execution of a periodic message should stop.
	 * Used for Specification and Interrupt processing.
	 * @return Date. The date and time of termination.
	 */
	@JsonIgnore
	public Date getStop() {
		return dStop;
	}
  
	/**
	 * Processes the temporal scope of the message.
	 * Updates errors field if errors are found.
	 * @return boolean True on success False on error.
	 */
	@JsonIgnore
	private boolean parseWhen() {
		String regexStr = "(now|\\d+)( ... (\\d+) / (\\d+))?";
		Pattern pattern = Pattern.compile(regexStr);
		Matcher matcher = pattern.matcher(when);
		if (matcher.matches()) {
			if (matcher.end(2) == -1) {    // start only
				dStop = null;
				String sStart = matcher.group(1);
				if (sStart.equals("now")) {   // one now
					dStart = null;
					return true;
				}
				/*try {    // one later
					dStart = new Date(Long.parseLong(sStart));
					if (dStart.before(new Date())) {
						this.errors.add(Error.WHEN_WRONG);
						return false;
					}
				} catch (NumberFormatException | NullPointerException nfe ) {
					this.errors.add(Error.WHEN_WRONG);
					return false;
				}*/
			} else {  // start and stop periodic
				String sStart = matcher.group(1);
				if (sStart.equals("now")) {
					dStart = null;
				} else {
					try {
						dStart = new Date(Long.parseLong(sStart));
						// measure cannot be in past, collect can
						if (verb.equals("measure") && dStart.before(new Date())) {
							this.errors.add(Error.WHEN_WRONG);
							return false;
						}
					} catch (NumberFormatException | NullPointerException nfe ) {
						this.errors.add(Error.WHEN_WRONG);
						return false;
					}
				}
				String sStop = matcher.group(3);
				try {
					dStop = new Date(Long.parseLong(sStop));
					Date testStart;
					if (dStart == null) {
						testStart = new Date();
					} else {
						testStart = (Date) dStart.clone();	  
					}
					// stop must be after start, for either measure and collect
					if (!dStop.after(testStart)) {
						this.errors.add(Error.WHEN_WRONG);
						return false;
					}
				} catch (NumberFormatException | NullPointerException nfe ) {
					this.errors.add(Error.WHEN_WRONG);
					return false;
				}
				period = Integer.parseInt(matcher.group(4));
			}
		} else {
			this.errors.add(Error.WHEN_WRONG);
			return false; 	  
		}
		return true;
	}
  
	/**
	 * Gives the type of the message.
	 * @return int. The message type as defined in Message.Type class.
	 */
	@JsonIgnore
	public int getMsgType() {
		return msgType;
	}
  
	/**
	 * Gives the verb of the message.
	 * @return String. The verb of the message
	 * @see the Verb class for possible message verbs.
	 */
	public String getVerb() {
		return verb;
	}
  
	/**
	 * Gives the end-point of the message.
	 * Typically the REST HTTP end-point.
	 * The meaning of end-point differs according to the message type.  
	 * @return String. The end-point of the message
	 * @see NMS document.
	 */
	public String getEndpoint() {
		return endpoint;
	}

	/**
	 * Gives the label of the message.
	 * Typically defined in Capability.  
	 * @return String. The label of the message.
	 * @see NMS document.
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Gives the temporal aspect of the message.
	 * The meaning of temporal aspect differs according to the message type.  
	 * @return String. The temporal aspect of the message
	 * @see NMS document.
	 */
	public String getWhen() {
		return when;
	}
	
	/**
	 * Gives the action name of the message.  
	 * @return String. The action name of the message.
	 * @see NMS document.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gives the parameters defined in the message.
	 * Typically defined in Capability, Specification, and Interrupt.
	 * The meaning of parameters differs according to the message type.  
	 * @return Map<String, String>. A list of key-value elements. 
	 * @see NMS document.
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}
	
	/**
	 * Gives the results defined in the message.
	 * Typically defined in Capability and Specification.
	 * The meaning of results differs according to the message type.  
	 * @return List<String>. An array of String values. 
	 * @see NMS document.
	 */
	public List<String> getResults() {
		return results;
	}

	/**
	 * Gives the results values contained in the message.
	 * Defined in Specification.
	 * @return List<List<String>>. An array of arrays of String values. 
	 * @see NMS document for resultValues interpretation.
	 */
	public List<List<String>> getResultValues() {
		return resultValues;
	}
	
	/**
	 * Gives the arbitrary content contained in the message.
	 * Typically defined in Receipt.  
	 * @return Map<String, String>. A list of key-value elements. 
	 * @see NMS document.
	 */
	public Map<String, String> getContent() {
		return content;
	}

	/**
	 * Gives the errors defined in the message.
	 * Typically defined in Receipt.  
	 * @return List<String>. An array of String values. 
	 * @see NMS document.
	 */
	public List<String> getErrors() {
		return errors;
	}
  
	/**
	 * Gives the token defined in the message.
	 * The meaning of the token field differs according to the message type.
	 * @return String. The token value. 
	 * @see NMS document.
	 */
	public String getToken() {
		return this.token;
	}

	/**
	 * Setter methods for the corresponding getters previously defined.
	 */
	@JsonIgnore
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public void setVerb(String verb) {
		this.verb = verb;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setWhen(String when) {
	  this.when = when;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	public void setResults(List<String> results) {
		this.results = results;
	}
	public void setResultValues(List<List<String>> resultValues) {
		this.resultValues = resultValues;
	}
	public void setContent(Map<String, String> content) {
		this.content = content;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
  
	private String getMd5(String input) 
    { 
        try { 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            byte[] messageDigest = md.digest(input.getBytes()); 
            BigInteger no = new BigInteger(1, messageDigest); 
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext; 
        } 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }

	/**
	 * <h1>Message.Type</h1>
	 * Defines the predefined message types values  
	 */
	public class Type {
		public final static int UNDEFINED = 0;
		public final static int CAPABILITY = 1;
		public final static int SPECIFICATION = 2;
		public final static int RESULT = 3;
		public final static int RECEIPT = 4;
		public final static int INTERRUPT = 5;
	}
  
	/**
	 * <h1>Message.Format</h1>
	 * Defines the predefined strings related to format.  
	 */
	public class Format {
		public final static String WHEN = "<start> ... <stop> / ";
	}
  
	/**
	 * <h1>Message.Verb</h1>
	 * Defines the predefined verb values for messages  
	 */
	public class Verb {
		public final static String MEASURE = "measure";
		public final static String STOP = "stop";
	}
  
	/**
	 * <h1>Message.Error</h1>
	 * Defines the message processing error values  
	 */
	public class Error {
		public final static String TYPE_UNSUPPORTED = "Unsupported message type";
		public final static String VERB_EMPTY = "Verb is required";
		public final static String NAME_EMPTY = "Name is required";
		public final static String PARAMS_EMPTY = "Parameters is required";
		public final static String RESULTS_EMPTY = "Results is required";
		public final static String WHEN_EMPTY = "When is required";
		public final static String WHEN_WRONG = "Temporal scope error";
		public final static String TOKEN_EMPTY = "Token is required";
		public final static String ENDPOINT_EMPTY = "Endpoint is required";
	}
}

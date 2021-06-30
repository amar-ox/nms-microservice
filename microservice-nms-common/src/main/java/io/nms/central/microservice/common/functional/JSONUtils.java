package io.nms.central.microservice.common.functional;

import java.lang.reflect.Field;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.json.JsonObject;

/**
 * Functional helper class.
 *
 * @author Amar Abane
 */
public final class JSONUtils {

	private static final ObjectMapper objectMapper;

  	static {
    	objectMapper = new ObjectMapper();
  	}

  	public JSONUtils() {
  	}

  	public static ObjectMapper getObjectMapper() {
    	return objectMapper;
  	}

  	public static <T> String pojo2Json(T pojo, boolean isPretty) {
    	try {
      		return !isPretty ? objectMapper.writeValueAsString(pojo)
          		: objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pojo);
    	} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;  
    	}
  	}
    public static <T> T json2Pojo(String x, Class<T> clazz) {
        if (x == null || x.equals("")) {
            return null;
		}
		String cleaned = x.replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("\\}\"", "}");
        try {
			return objectMapper.readValue(cleaned, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> void fromJson(JsonObject json, T obj, Class<T> clazz) {
		try {
			T tmp = objectMapper.readValue(json.encode(), clazz);
			for (Field field : tmp.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				field.set(obj, field.get(tmp));
			}
		} catch (JsonProcessingException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
  	public static <T> String pojo2JsonE(T pojo, boolean isPretty) throws Exception {
      		return !isPretty ? objectMapper.writeValueAsString(pojo)
          		: objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pojo);
  	}
    public static <T> T json2PojoE(String x, Class<T> clazz) throws Exception {
        if (x == null || x.equals("")) {
			throw new Exception("Empty json string");
		}
		String cleaned = x.replaceAll("\\\\", "").replaceAll("\"\\{", "{").replaceAll("\\}\"", "}");
		return objectMapper.readValue(cleaned, clazz);
    }
}

package jblog.guohai.org.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonTool {
	
	private static Logger logger = LoggerFactory.getLogger(JsonTool.class);
	
	private static final ObjectMapper defaultMapper = new ObjectMapper();
	static {
		// 属性为默认值不序列化
		defaultMapper.setSerializationInclusion(Include.NON_DEFAULT);
		defaultMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	/**
	 * 反序列化 JSON 到 Object
	 * 
	 * @param data
	 * @param clazz
	 * @return
	 */
	public static <T> T toBeanFormStr(String data, Class<T> clazz) {
		try {
			return defaultMapper.readValue(data, clazz);
		} catch (Exception ex) {
			logger.error("toBeanFormStr解析异常:", ex);
			return null;
		}
	}
	
	/**
	 * 序列化对象到 JSON
	 * 
	 * @param obj
	 * @return
	 */
	public static String toStrFormBean(Object obj) {
		try {
			return defaultMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("toStrFormBean解析异常:", e);
			return null;
		}
	}


}

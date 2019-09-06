package whut.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 处理json字符串工具类
 * @author chen cheng
 *
 */
public class JsonUtils {
	
	private JsonNode rootNode;
	
	public JsonUtils(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		//create tree from JSON
		try {
			rootNode = mapper.readTree(jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getIntValue(String key) {
		return rootNode.findValue(key).asInt();
	}
	
	/**
	 * 传递空字符串null会被认为是null
	 * @param key
	 * @return
	 */
	public String getStringValue(String key) {
		if(rootNode.findValue(key).asText().equals("null")) {
			return null;
		}else {
			return rootNode.findValue(key).asText();
		}
	}
	
	public Double getDoubleValue(String key) {
		return rootNode.findValue(key).asDouble();
	}
	
	/**
	 * 生成json的使用方法
	 */
//	public void vv() {
//		ObjectMapper mapper = new ObjectMapper();
//		 
//		//生成对象结点
//		ObjectNode objNode = mapper.createObjectNode();
//		objNode.put("属性名", 1);    /*在jdk1.8中，简单值用put设置*/
//		objNode.set("属性名", 1);    /*在jdk1.8中，子节点用set设置*/
//		 
//		//生成数组结点
//		ArrayNode arrNode = mapper.createArrayNode();
//		arrNode.add("属性或子节点");    /*数组结点添加元素不做简单值和结点类的区分*/
//	}
}
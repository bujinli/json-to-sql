package com.sap.gdpr.demo.processactivity.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.gdpr.demo.processactivity.controllers.DemoController;
import com.sap.gdpr.demo.processactivity.models.FieldEntity;
import com.sap.gdpr.demo.processactivity.models.NodeEntity;
import com.sap.gdpr.demo.processactivity.services.JsonToTreeMapper;
import com.sap.gdpr.demo.processactivity.services.ProcessActivitypPersistService;
import com.sap.gdpr.demo.processactivity.treemodeling.ProcessingActRoot;
import com.sap.gdpr.demo.processactivity.treemodeling.ProcessingActivityResponeBuilder;
import com.sap.gdpr.demo.processactivity.treemodeling.TreeNode;
import com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue.IntegerFieldValue;
import com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue.StringFieldValue;

@RunWith(SpringRunner.class)
public class JsonToMapTest {

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// String json =
		// "{\"key\":\"1234\",\"type\":\"\",\"create_datetime\":\"\",\"content\":[{\"custom1\":\"value1\",\"custom2\":{\"subfile1\":\"sf1\",\"subfile2\":\"sf2\"},\"custom3\":[{\"31\":\"31v\",\"32\":\"32v\"},{\"33\":\"33v\",\"34\":\"34v\",\"35\":\"35v\"}],\"custom4\":[\"123\",\"45\"]}]}";
		String json = "{\"key\":\"12345677\",\"create_datetime\":\"20170101\",\"content\":{\"data_controller\":[\"jeff.li01@sap.com\",\"jeff.li02@sap.com\",{\"dd\":\"23\"},1],\"data_volumn\":{\"size\":\"1000K\",\"location\":\"SAPWDF\"},\"evaluation\":\"yes\",\"rating\":5}}";
		Map<String, Object> map = new HashMap<String, Object>();

		// convert JSON string to Map
		map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
		});

		Map<String, Object> obj = (Map<String, Object>) map.get("content");

		ProcessingActRoot root = new ProcessingActRoot();
		Map<String, TreeNode> tree = parseMap(obj);
		root.setContent(tree);

		String retJson = mapper.writeValueAsString(root);
		System.out.println("++++++++++++++++++++++++++++++++++++++");
		System.out.println(retJson);

		// obj.get("data_controller");
		// obj.get("data_volumn");
		// obj.get("evaluation");
		// obj.get("rating");
		//
		// if (obj instanceof Collection<?>) {
		//
		// List<Object> arrayObject = (List<Object>) obj;
		//
		// Map<String, Object> map2 = (Map<String, Object>) arrayObject.get(0);
		//
		// List<Object> arrayObject2 = (List<Object>) map2.get("custom4");
		//
		// arrayObject2.get(0);
		//
		// // ((Collection) obj).parallelStream().map((a)->());
		// // Map<String, Object> map2 = mapper.readValue(obj, new
		// // TypeReference<Map<String, Object>>() {
		// // });
		// }

		System.out.println(map);

	}

	@SuppressWarnings("unchecked")
	private Map<String, TreeNode> parseMap(Map<String, Object> obj) {

		Map<String, TreeNode> ret = new HashMap<>();

		for (Entry<String, Object> entry : obj.entrySet()) {
			// node.setFieldName(entry.getKey());
			TreeNode node = new TreeNode();

			if (entry.getValue() instanceof List<?>) {
				List<Object> list = (List<Object>) entry.getValue();

				for (Object temp : list) {
					if (temp instanceof Map<?, ?>) {
						node.setSubNode(parseMap((Map<String, Object>) temp));
					} else {
						addValue(temp, node);
					}
				}
			} else if (entry.getValue() instanceof Map<?, ?>) {
				node.setSubNode(parseMap((Map<String, Object>) entry.getValue()));
			} else {
				addValue(entry.getValue(), node);
			}
			ret.put(entry.getKey(), node);
		}
		return ret;
	}

	private void addValue(Object obj, TreeNode node) {
		if (obj instanceof String) {
			node.addValue(new StringFieldValue((String) obj));
		} else if (obj instanceof Integer) {
			node.addValue(new IntegerFieldValue((Integer) obj));
		}
	}

	@Test
	public void test2() throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		ProcessingActRoot root = ProcessingActivityResponeBuilder.build();
		String json = mapper.writeValueAsString(root);
		System.out.println("++++++++++++++++++++++++++++++++++++++");
		System.out.println(json);

	}
	
	@Test
	public void test4(){
		callback();
	}
	
	static int level = 1;
	static void callback(){
	    level++;
	    System.out.println("level:"+level);
	    callback();
	}
  

	@Test
	public void test3() throws JsonParseException, JsonMappingException, IOException {

		// String json =
		// "{\"key\":\"12345677\",\"create_datetime\":\"20170101\",\"content\":{\"data_controller\":[\"jeff.li01@sap.com\",\"jeff.li02@sap.com\",{\"dd\":\"23\"},1],\"data_volumn\":{\"size\":\"1000K\",\"location\":\"SAPWDF\"},\"evaluation\":\"yes\",\"rating\":5}}";
		// String json =
		// "{\"key\":\"12345677\",\"create_datetime\":\"20170101\",\"content\":{\"data_controller\":[\"jeff.li01@sap.com\",\"jeff.li02@sap.com\",{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":\"23\"}}}}}}}}}}}}}}}}}}},1],\"data_volumn\":{\"size\":\"1000K\",\"location\":\"SAPWDF\"},\"evaluation\":\"yes\",\"rating\":5}}";
		// String json =
		// "{\"key\":\"12345677\",\"create_datetime\":\"20170101\",\"content\":{\"data_controller\":[\"jeff.li01@sap.com\",\"jeff.li02@sap.com\",{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":\"23\"}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}},1],\"data_volumn\":{\"size\":\"1000K\",\"location\":\"SAPWDF\"},\"evaluation\":\"yes\",\"rating\":5}}";
		String json = "{\"key\":\"12345677\",\"create_datetime\":\"20170101\",\"content\":{\"data_controller\":[\"jeff.li01@sap.com\",\"jeff.li02@sap.com\",{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":\"23\"}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}},1,{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":\"23\"}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}},1,\"jeff.li02@sap.com\",{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":\"23\"}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}},1,{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":{\"dd\":\"23\"}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}},1],\"data_volumn\":{\"size\":\"1000K\",\"location\":\"SAPWDF\"},\"evaluation\":\"yes\",\"rating\":5}}";
		
		Long time1;
		Long time2;
		System.out.println(time1 = new Date().getTime());
		for (int i = 0; i <= 1000; i++) {
			JsonToTable(json);
		}
		System.out.println(time2 = new Date().getTime());
		System.out.println(time2 - time1);

	}

	private void JsonToTable(String json) throws JsonParseException, JsonMappingException, IOException {
		ProcessingActRoot root = new JsonToTreeMapper().parseString(json);

		List<NodeEntity> retNodeList = new ArrayList<>();
		List<FieldEntity> retFieldList = new ArrayList<>();
		ProcessActivitypPersistService.saveProcessActivity(root, retNodeList, retFieldList);

		List<String> nodeList = retNodeList.parallelStream().map((t) -> t.toString()).collect(Collectors.toList());
		List<String> fieldList = retFieldList.parallelStream().map((t) -> t.toString()).collect(Collectors.toList());

		StringBuilder sb = new StringBuilder();

		sb.append(String.format(DemoController.nodeFormat, "node_key", "process_activity_key", "parent_node_key"));
		for (String temp : nodeList) {
			sb.append(temp);
		}

		sb.append(String.format(DemoController.fieldFormat, "field_key", "node_key", "field_name", "field_value",
				"field_type"));
		for (String temp : fieldList) {
			sb.append(temp);
		}

//		System.out.println("++++++++++++++++++++++++++++++++++++++");
//		System.out.println(sb.toString());
	}

}

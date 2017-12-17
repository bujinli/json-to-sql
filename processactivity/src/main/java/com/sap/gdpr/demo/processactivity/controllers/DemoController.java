package com.sap.gdpr.demo.processactivity.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sap.gdpr.demo.processactivity.models.FieldEntity;
import com.sap.gdpr.demo.processactivity.models.NodeEntity;
import com.sap.gdpr.demo.processactivity.services.JsonToTreeMapper;
import com.sap.gdpr.demo.processactivity.services.ProcessActivitypPersistService;
import com.sap.gdpr.demo.processactivity.treemodeling.ProcessingActRoot;

@RestController
@RequestMapping("/demo")
public class DemoController {

	public final static String nodeFormat = "%-40s | %-40s | %-40s | %n";
	public final static String fieldFormat = "%-40s | %-40s | %-40s | %-40s | %-80s %n";

	@PostMapping("/parse_to_root")
	public ProcessingActRoot parseToRoot(@RequestBody String json)
			throws JsonParseException, JsonMappingException, IOException {

		ProcessingActRoot root = new JsonToTreeMapper().parseString(json);

		List<NodeEntity> retNodeList = new ArrayList<>();
		List<FieldEntity> retFieldList = new ArrayList<>();
		ProcessActivitypPersistService.saveProcessActivity(root, retNodeList, retFieldList);

		return root;
	}

	@PostMapping("/parse_to_table")
	public String parseToTable(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {

		ProcessingActRoot root = new JsonToTreeMapper().parseString(json);

		List<NodeEntity> retNodeList = new ArrayList<>();
		List<FieldEntity> retFieldList = new ArrayList<>();
		ProcessActivitypPersistService.saveProcessActivity(root, retNodeList, retFieldList);

		List<String> nodeList = retNodeList.parallelStream().map((t) -> t.toString()).collect(Collectors.toList());
		List<String> fieldList = retFieldList.parallelStream().map((t) -> t.toString()).collect(Collectors.toList());

		StringBuilder sb = new StringBuilder();

		sb.append(String.format(nodeFormat, "node_key", "process_activity_key", "parent_node_key"));
		for (String temp : nodeList) {
			sb.append(temp);
		}

		sb.append(String.format(fieldFormat, "field_key", "node_key", "field_name", "field_value", "field_type"));
		for (String temp : fieldList) {
			sb.append(temp);
		}

		return sb.toString();
	}

	// @SuppressWarnings("unchecked")
	// private ProcessingActRoot parseString(String json) throws
	// JsonParseException, JsonMappingException, IOException {
	// ObjectMapper mapper = new ObjectMapper();
	//
	// Map<String, Object> map = mapper.readValue(json, new
	// TypeReference<Map<String, Object>>() {
	// });
	//
	// Map<String, Object> obj = map.get("content") instanceof Map<?, ?> ?
	// (Map<String, Object>) map.get("content")
	// : new HashMap<>();
	// ProcessingActRoot root = new ProcessingActRoot();
	// Map<String, TreeNode> tree = parseMap(obj);
	// root.setContent(tree);
	//
	// return root;
	//
	// }
	//
	// @SuppressWarnings("unchecked")
	// private Map<String, TreeNode> parseMap(Map<String, Object> obj) {
	//
	// Map<String, TreeNode> ret = new HashMap<>();
	//
	// for (Entry<String, Object> entry : obj.entrySet()) {
	// // node.setFieldName(entry.getKey());
	// TreeNode node = new TreeNode();
	//
	// if (entry.getValue() instanceof List<?>) {
	// List<Object> list = (List<Object>) entry.getValue();
	//
	// for (Object temp : list) {
	// if (temp instanceof Map<?, ?>) {
	// node.setSubNode(parseMap((Map<String, Object>) temp));
	// } else {
	// addValue(temp, node);
	// }
	// }
	// } else if (entry.getValue() instanceof Map<?, ?>) {
	// node.setSubNode(parseMap((Map<String, Object>) entry.getValue()));
	// } else {
	// addValue(entry.getValue(), node);
	// }
	// ret.put(entry.getKey(), node);
	// }
	// return ret;
	// }
	//
	// private void addValue(Object obj, TreeNode node) {
	// if (obj instanceof String) {
	// node.addValue(new StringFieldValue((String) obj));
	// } else if (obj instanceof Integer) {
	// node.addValue(new IntegerFieldValue((Integer) obj));
	// }
	// }
}

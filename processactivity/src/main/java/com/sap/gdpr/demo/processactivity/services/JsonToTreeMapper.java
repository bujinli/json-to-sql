package com.sap.gdpr.demo.processactivity.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.gdpr.demo.processactivity.treemodeling.ProcessingActRoot;
import com.sap.gdpr.demo.processactivity.treemodeling.TreeNode;
import com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue.IntegerFieldValue;
import com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue.StringFieldValue;

public class JsonToTreeMapper {

	@SuppressWarnings("unchecked")
	public ProcessingActRoot parseString(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
		});

		Map<String, Object> obj = map.get("content") instanceof Map<?, ?> ? (Map<String, Object>) map.get("content")
				: new HashMap<>();
		ProcessingActRoot root = new ProcessingActRoot();
		Map<String, TreeNode> tree = parseMap(obj);
		root.setContent(tree);

		return root;

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
}

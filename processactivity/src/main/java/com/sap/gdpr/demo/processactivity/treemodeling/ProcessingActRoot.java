package com.sap.gdpr.demo.processactivity.treemodeling;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue.IFieldValue;

import lombok.Data;

@Data
public class ProcessingActRoot {

	private String id = null;
	private String activityName;

	private Map<String, TreeNode> content = new HashMap<>();

	public ProcessingActRoot putContent(String key, TreeNode node) {
		content.put(key, node);
		return this;
	}

	public String getId() {
		if (id == null) {
			id = UUID.randomUUID().toString();
		}

		return id;
	}

}

package com.sap.gdpr.demo.processactivity.treemodeling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue.IFieldValue;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
public @Data class TreeNode {

	private String id = null;

	@JsonIgnore
	private String fieldName = null;

	// @JsonInclude(JsonInclude.Include.NON_NULL)
	private List<IFieldValue> valueList = null;

	// @JsonInclude(JsonInclude.Include.NON_NULL)
	private Map<String, TreeNode> subNode = null;

	public  void addValue(IFieldValue value) {
		if (valueList == null) {
			valueList = new ArrayList<>();
		}

		this.valueList.add(value);
	}

	public void putSubNode(String nodeName, TreeNode node) {

		if (subNode == null) {
			subNode = new HashMap<>();
		}

		subNode.put(nodeName, node);
	}

	public String getId() {
		if (id == null) {
			id = UUID.randomUUID().toString();
		}
		return id;
	}

	public void setId(String id) {
		// this.id = id;
	}

}

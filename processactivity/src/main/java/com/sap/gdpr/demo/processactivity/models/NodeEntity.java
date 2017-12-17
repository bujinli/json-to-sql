package com.sap.gdpr.demo.processactivity.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sap.gdpr.demo.processactivity.controllers.DemoController;

@Entity
@Table(name = "T_NODE")
public class NodeEntity {

	@Id
	@Column(name = "node_key")
	private String nodeKey;

	@Column(name = "process_activity_key")
	private String processActivityKey;

	@Column(name = "parent_node_key")
	private String parentNodeKey;

	public String getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public String getProcessActivityKey() {
		return processActivityKey;
	}

	public void setProcessActivityKey(String processActivityKey) {
		this.processActivityKey = processActivityKey;
	}

	public String getParentNodeKey() {
		return parentNodeKey;
	}

	public void setParentNodeKey(String parentNodeKey) {
		this.parentNodeKey = parentNodeKey;
	}

	public String toString() {
		return String.format(DemoController.nodeFormat, nodeKey, processActivityKey, parentNodeKey);

	}

}

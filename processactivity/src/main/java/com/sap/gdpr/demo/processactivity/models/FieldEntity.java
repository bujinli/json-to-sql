package com.sap.gdpr.demo.processactivity.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sap.gdpr.demo.processactivity.controllers.DemoController;

import lombok.Data;

@Entity
@Table(name = "T_FIELD")
public @Data class FieldEntity {

	@Id
	@Column(name = "field_key")
	private String fieldKey;

	@Column(name = "node_key")
	private String nodeKey;

	@Column(name = "field_name")
	private String fieldName;

	@Column(name = "field_value")
	private String fieldValue;

	@Column(name = "field_type")
	private String fieldType;

	public FieldEntity() {
	}

	public FieldEntity(String fieldKey, String nodeKey, String fieldName, String fieldValue, String fieldType) {
		this.fieldKey = fieldKey;
		this.nodeKey = nodeKey;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.fieldType = fieldType;

	}

	public String toString() {
		return String.format(DemoController.fieldFormat, fieldKey, nodeKey, fieldName, fieldValue, fieldType);

	}

}

package com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StringFieldValue implements IFieldValue {

	@NonNull
	private String fieldData;
	@JsonIgnore
	private Class<?> fieldType = String.class;

	@Override
	public String getValue() {
		return this.fieldData;
	}

	@Override
	public Class<? extends IFieldValue> getConcreteClass() {
		return StringFieldValue.class;
	}

	
	
	
}

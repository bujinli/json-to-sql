package com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LongFieldValue implements IFieldValue {

	@NonNull
	private Long fieldData;
	@JsonIgnore
	private Class<?> fieldType = Long.class;

	@Override
	public String getValue() {
		return Long.toString(fieldData);
	}

	@Override
	public Class<? extends IFieldValue> getConcreteClass() {
		return LongFieldValue.class;
	}

}

package com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IFieldValue {

	@JsonIgnore
	Class<? extends IFieldValue> getConcreteClass();

	@JsonIgnore
	String getValue();

}

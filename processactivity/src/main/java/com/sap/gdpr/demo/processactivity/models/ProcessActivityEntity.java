package com.sap.gdpr.demo.processactivity.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_PROCESS_ACTIVITY")
public class ProcessActivityEntity {

	@Id
	@Column(name = "process_activity_key")
	private String key;

	@Column(name = "process_activity_name")
	private String name;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

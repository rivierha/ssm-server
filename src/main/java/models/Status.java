package models;

import java.sql.Timestamp;

public class Status {
	private String id;
	private String name;
	private String description;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	
	public String getStatusId() {
		return id;
	}
	
	public void setStatusId(String id) {
		this.id = id;
	}
	
	public String getStatusName() {
		return name;
	}
	
	public void setStatusName(String name) {
		this.name = name;
	}

	public String getStatusDescription() {
		return description;
	}

	public void setStatusDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createddAt) {
		this.createdAt = createddAt;
	}

	public Timestamp getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
}

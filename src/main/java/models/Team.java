package models;

import java.sql.Timestamp;

public class Team {
	private String id;
	private String name;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	@Override
    public String toString() {
        return "{" + "id=" + id + ", name=" + name + 
        		  ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt +"}";
    }
}

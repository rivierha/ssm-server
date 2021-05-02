package models;

import java.sql.Timestamp;

public class Instance {
	
	private String id;
	private String name;
	private String team;
	private String status;
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
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
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
        		", team=" + team + 
        		", status=" + status + 
        		", createdAt=" + createdAt + 
        		", modifiedAt=" + modifiedAt +"}";
    }

}

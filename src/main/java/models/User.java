package models;

import java.sql.Timestamp;
//import java.util.UUID;

public class User {
	
	private String id;
	private String email;
	private String name;
	private String team;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
        return "{" + "id=" + id + ", email=" + email + ", name=" + name + 
        		 ", team=" + team + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt +"}";
    }
}

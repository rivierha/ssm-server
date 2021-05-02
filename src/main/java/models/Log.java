package models;

import java.sql.Timestamp;

public class Log {
	private String id;
	private String reason;
	private String instance;
	private String user;
	private Timestamp startTime;
	private Timestamp endTime;
	private int totalTime;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String name) {
		this.reason = name;
	}
	
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public int getTotalTime() {
		return totalTime;
	}
	
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
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
        return "{" + "id=" + id + ", reason=" + reason + 
        		", instance=" + instance + 
        		", user=" + user + 
        		", startTime=" + startTime +
        		", endTime=" + endTime + 
        		", totalTime=" + totalTime + 
        		", createdAt=" + createdAt + 
        		", modifiedAt=" + modifiedAt +"}";
    }
}

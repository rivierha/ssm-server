package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.gson.JsonObject;

import java.sql.*;
import api.MySQLJDBCUtil;
import models.Log;

public class LogService {

	Connection conn;
	private InstanceService instanceService = new InstanceService();

	
	public LogService() {
		// TODO Auto-generated constructor stub
		try {
			conn = MySQLJDBCUtil.getConnection();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public Log createLog(JsonObject data) throws SQLException {
		
		String create_log = "insert into instancelogs (id, reason, instance, user) values(?, ?, ?, ?)";
		String newLogId = UUID.randomUUID().toString();
		
			PreparedStatement st = conn.prepareStatement(create_log);
			st.setString(1, newLogId);
			st.setString(2, data.get("reason").getAsString());
			st.setString(3, data.get("instance").getAsString());
			st.setString(4, data.get("user").getAsString());
			
			int i = st.executeUpdate();  
			System.out.println(i+" records updated");
			if(i==0)
				return null;
			Log newLog = getLog(newLogId);
			this.runScript(newLog.getInstance(), newLog.getId(), newLog.getStartTime());
			return newLog;
	}
	
	public Log getLog(String id) throws SQLException {
		String get_log = "select * from instancelogs where id='"+id+"'";
		Log log = new Log();
	
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(get_log);
		
		if(rs.next()) {
			log.setId(rs.getString(1));
			log.setReason(rs.getString(2));
			log.setInstance(rs.getString(3));
			log.setUser(rs.getString(4));
			log.setStartTime(rs.getTimestamp(5));
			log.setEndTime(rs.getTimestamp(6));
			log.setTotalTime(rs.getInt(7));
			log.setCreatedAt(rs.getTimestamp(8));
			log.setModifiedAt(rs.getTimestamp(9));
		}
		if(log.getId() == null)
			return null;
		return log;
	}
	
	public List<Log> getAllLogs(String instanceId) throws SQLException {
		
		String get_logs;
		if(instanceId == null)
			get_logs = "select * from instancelogs "
					+ "left join instances on instancelogs.instance = instances.id "
					+ "left join users on instancelogs.user = users.id";
		else
			get_logs = "select * from instancelogs "
					+ "left join instances on instancelogs.instance = instances.id "
					+ "left join users on instancelogs.user = users.id "
					+ "where instancelogs.instance = '"+instanceId+ "'";
		List<Log> logs = new ArrayList<>();
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(get_logs);
		
		while(rs.next()) {
			Log log = new Log();
			log.setId(rs.getString(1));
			log.setReason(rs.getString(2));
			log.setInstance(rs.getString(3));
			log.setUser(rs.getString(4));
			log.setStartTime(rs.getTimestamp(5));
			log.setEndTime(rs.getTimestamp(6));
			log.setTotalTime(rs.getInt(7));
			log.setCreatedAt(rs.getTimestamp(8));
			log.setModifiedAt(rs.getTimestamp(9));
			logs.add(log);
		}
		System.out.println(logs.size());
		return logs;
	}
	
	public String deleteLog(String id) throws SQLException {
		String delete_log = "delete from instancelogs where id='"+id+"'";
			
		PreparedStatement st = conn.prepareStatement(delete_log);
		int i=st.executeUpdate();  
		System.out.println(i+" records deleted");  
		if(i==0)
			return null;
		return "Log successfully deleted";
	}
	
	public void deleteAllLog(String instanceId) throws SQLException {
		String delete_logs = "delete from instancelogs where instance='"+instanceId+"'";
			
		PreparedStatement st = conn.prepareStatement(delete_logs);
		int i=st.executeUpdate();  
		System.out.println(i+" records deleted");  
		System.out.println(i+ "Logs successfully deleted");
	}
	
	public Log updateLog(String id, JsonObject data) throws SQLException {
				
		String update_log = "update instancelogs set totalTime=?, endTime=?, modifiedAt=? where id=?";
			PreparedStatement st = conn.prepareStatement(update_log);
			st.setInt(1, data.get("totalTime").getAsInt());
			st.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
			st.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
			st.setString(4, id);
		
			int i = st.executeUpdate();  
			System.out.println(i+" records updated");  
			if(i==0)
				return null;
			return getLog(id);
	}

	public void setTimeout(final Runnable runnable, final int delay){
	    new Thread(() -> {
	        try {
	            Thread.sleep(delay);
	            runnable.run();
	        }
	        catch (Exception e){
	            System.err.println(e);
	        }
	    }).start();
	}
	
	private void runScript(String instanceId, String logId, Timestamp startTime) throws SQLException {
		Random r = new Random();
		int val = r.nextInt(60) + 1;
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("status", "INUSE");
		instanceService.updateInstance(instanceId, jsonObject);
		
		JsonObject updateInstanceObject = new JsonObject();
		updateInstanceObject.addProperty("status", "FREE");
		
		
		
		this.setTimeout(() -> {
			try {
				instanceService.updateInstance(instanceId, updateInstanceObject);
				
				java.sql.Timestamp currentTime = new java.sql.Timestamp(new java.util.Date().getTime());
				System.out.println(currentTime+ "current time");
				int diff = (int) ((currentTime.getTime() - startTime.getTime()) / 1000);
				
				JsonObject updateLogObject = new JsonObject();
				updateLogObject.addProperty("endTime", currentTime.toString());
				updateLogObject.addProperty("totalTime", diff);
				System.out.println(updateLogObject);
				
				this.updateLog(logId, updateLogObject);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}, val*1000);
		return;
	}
	
}

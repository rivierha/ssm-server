package services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import java.sql.*;
import api.MySQLJDBCUtil;
import models.Instance;

public class InstanceService {

	Connection conn;
	
	public InstanceService() {
		// TODO Auto-generated constructor stub
		try {
			conn = MySQLJDBCUtil.getConnection();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public Instance createInstance(JsonObject data) throws SQLException {
		
		String create_instance = "insert into instances (id, name, status, team) values(?, ?, ?, ?)";
		String newInstanceId = UUID.randomUUID().toString();
			PreparedStatement st = conn.prepareStatement(create_instance);
			st.setString(1, newInstanceId);
			st.setString(2, data.get("name").getAsString());
			st.setString(3, "FREE");
			st.setString(4, data.get("team").getAsString());
			
			int i = st.executeUpdate();  
			System.out.println(i+" records updated");
			if(i==0)
				return null;
			return getInstance(newInstanceId);
	}
	
	public Instance getInstance(String id) throws SQLException {
		String get_instance = "select * from instances where id='"+id+"'";
		Instance instance = new Instance();
	
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(get_instance);
		
		if(rs.next()) {
			instance.setId(rs.getString(1));
			instance.setName(rs.getString(2));
			instance.setStatus(rs.getString(3));
			instance.setTeam(rs.getString(4));
			instance.setCreatedAt(rs.getTimestamp(5));
			instance.setModifiedAt(rs.getTimestamp(6));
		}
		if(instance.getId() == null)
			return null;
		return instance;
	}
	
	public List<Instance> getAllInstances(String teamId) throws SQLException {
		
		String get_instances;
		if(teamId == null)
			get_instances = "select * from instances "
					+ "left join teams on instances.team = teams.id "
					+ "left join status on instances.status = status.id";
		else
			get_instances = "select * from instances "
					+ "left join teams on instances.team = teams.id "
					+ "left join status on instances.status = status.id "
					+ "where instances.team = '"+teamId+"'";
		

		List<Instance> instances = new ArrayList<>();
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(get_instances);
		
		while(rs.next()) {
			Instance instance = new Instance();
			instance.setId(rs.getString(1));
			instance.setName(rs.getString(2));
			instance.setStatus(rs.getString(3));
			instance.setTeam(rs.getString(4));
			instance.setCreatedAt(rs.getTimestamp(5));
			instance.setModifiedAt(rs.getTimestamp(6));
			instances.add(instance);
		}
		System.out.println(instances.size());
		return instances;
	}
	
	public String deleteInstance(String id) throws SQLException {
		String delete_instance = "delete from instances where id='"+id+"'";
			
		PreparedStatement st = conn.prepareStatement(delete_instance);
		int i=st.executeUpdate();  
		System.out.println(i+" records deleted");  
		if(i==0)
			return null;
		return "Instance successfully deleted";
	}
	
	public Instance updateInstance(String id, JsonObject data) throws SQLException {
				
		String update_instance = "update instances set status=?, modifiedAt=? where id=?";
			PreparedStatement st = conn.prepareStatement(update_instance);
			st.setString(1, data.get("status").getAsString());
			st.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
			st.setString(3, id);
		
			int i = st.executeUpdate();  
			System.out.println(i+" records updated");  
			if(i==0)
				return null;
			return getInstance(id);
	}

}

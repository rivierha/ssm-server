package services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import java.sql.*;
import api.MySQLJDBCUtil;
import models.User;

public class UserService {
	Connection conn;
	
	public UserService() {
		try {
				conn = MySQLJDBCUtil.getConnection();
				
			} catch (Exception e) {
				System.out.println(e);
		}
	}
	
	
	public User createUser(JsonObject data) throws SQLException {
	
		String create_user = "insert into users (id, email, name, team) values(?, ?, ?, ?)";
		String newUserId = UUID.randomUUID().toString();
		
			PreparedStatement st = conn.prepareStatement(create_user);
			st.setString(1, newUserId);
			st.setString(2, data.get("email").getAsString());
			st.setString(3, data.get("name").getAsString());
			st.setString(4, null);
			
			int i = st.executeUpdate();  
			System.out.println(i+" records updated");
			if(i==0)
				return null;
			return getUser(newUserId);
	}
	
	public User getUser(String id) throws SQLException {
		String get_user = "select * from users where id='"+id+"'";
		User user = new User();
	
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(get_user);
		
		if(rs.next()) {
			System.out.println(rs.getString(4));
			user.setId(rs.getString(1));
			user.setEmail(rs.getString(2));
			user.setName(rs.getString(3));
			user.setTeam(rs.getString(4));
			user.setCreatedAt(rs.getTimestamp(5));
			user.setModifiedAt(rs.getTimestamp(6));
		}
		if(user.getId() == null)
			return null;
		return user;
	}
	
	public List<User> getAllUsers(String email) throws SQLException {
		
		System.out.println(email);
		String get_users;
		if(email == null)
			get_users = "select * from users left join teams on users.team=teams.id";
		else
			get_users = "select * from users left join teams on users.team=teams.id where users.email like '%"+email+"%'";
		System.out.println(get_users);
		List<User> users = new ArrayList<>();
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(get_users);
		
		while(rs.next()) {
			User user = new User();
			user.setId(rs.getString(1));
			user.setEmail(rs.getString(2));
			user.setName(rs.getString(3));
			user.setTeam(rs.getString(4));
			user.setCreatedAt(rs.getTimestamp(5));
			user.setModifiedAt(rs.getTimestamp(6));
			users.add(user);
		}
		System.out.println(users.size());
		return users;
	}
	
	public String deleteUser(String id) throws SQLException {
		String delete_user = "delete from users where id='"+id+"'";
			
		PreparedStatement st = conn.prepareStatement(delete_user);
		int i=st.executeUpdate();  
		System.out.println(i+" records deleted");  
		if(i==0)
			return null;
		return "User successfully deleted";
	}
	
	public User updateUser(String id, JsonObject data) throws SQLException {
				
		String update_user = "update users set team=?, modifiedAt=? where id=?";
			PreparedStatement st = conn.prepareStatement(update_user);
			st.setString(1, data.get("team").getAsString());
			st.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
			st.setString(3, id);
		
			int i = st.executeUpdate();  
			System.out.println(i+" records updated");  
			if(i==0)
				return null;
			return getUser(id);
	}
	
	
}

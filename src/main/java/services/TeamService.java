package services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import java.sql.*;
import api.MySQLJDBCUtil;
import models.Team;

public class TeamService {

	Connection conn;
	
	public TeamService() {
		// TODO Auto-generated constructor stub
		try {
			conn = MySQLJDBCUtil.getConnection();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public Team createTeam(JsonObject data) throws SQLException {
		
		String create_team = "insert into teams (id, name) values(?, ?)";
		String newTeamId = UUID.randomUUID().toString();
		
			PreparedStatement st = conn.prepareStatement(create_team);
			st.setString(1, newTeamId);
			st.setString(2, data.get("name").getAsString());
			
			int i = st.executeUpdate();  
			System.out.println(i+" records updated");
			if(i==0)
				return null;
			return getTeam(newTeamId);
	}
	
	public Team getTeam(String id) throws SQLException {
		String get_team = "select * from teams where id='"+id+"'";
		Team team = new Team();
	
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(get_team);
		
		if(rs.next()) {
			team.setId(rs.getString(1));
			team.setName(rs.getString(2));
			team.setCreatedAt(rs.getTimestamp(3));
			team.setModifiedAt(rs.getTimestamp(4));
		}
		if(team.getId() == null)
			return null;
		return team;
	}
	
public List<Team> getAllTeams() throws SQLException {
		
		String get_teams;
		get_teams = "select * from teams";

		List<Team> teams = new ArrayList<>();
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(get_teams);
		
		while(rs.next()) {
			Team team = new Team();
			team.setId(rs.getString(1));
			team.setName(rs.getString(2));
			team.setCreatedAt(rs.getTimestamp(3));
			team.setModifiedAt(rs.getTimestamp(4));
			teams.add(team);
		}
		System.out.println(teams.size());
		return teams;
	}
	
	public String deleteTeam(String id) throws SQLException {
		String delete_team = "delete from teams where id='"+id+"'";
			
		PreparedStatement st = conn.prepareStatement(delete_team);
		int i=st.executeUpdate();  
		System.out.println(i+" records deleted");  
		if(i==0)
			return null;
		return "Team successfully deleted";
	}
	
	public Team updateTeam(String id, JsonObject data) throws SQLException {
				
		String update_team = "update teams set name=?, modifiedAt=? where id=?";
			PreparedStatement st = conn.prepareStatement(update_team);
			st.setString(1, data.get("name").getAsString());
			st.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
			st.setString(3, id);
		
			int i = st.executeUpdate();  
			System.out.println(i+" records updated");  
			if(i==0)
				return null;
			return getTeam(id);
	}
	

}

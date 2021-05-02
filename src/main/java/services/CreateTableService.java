package services;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import api.MySQLJDBCUtil;

public class CreateTableService {
	String createTableSQL;
	Connection connection;
	
	public CreateTableService() {
		// TODO Auto-generated constructor stub
	}
	
	public void createTeamsTable() throws SQLException {
		createTableSQL = "CREATE TABLE teams(\n"
				+ "    id varchar(36) not null primary key,\n"
				+ "    name varchar(255) not null,\n"
				+ "    createdAt timestamp default CURRENT_TIMESTAMP,\n"
				+ "    modifiedAt timestamp default CURRENT_TIMESTAMP\n"
				+ "    );";
		System.out.println(createTableSQL);
        try {
        	connection = MySQLJDBCUtil.getConnection();
            // Step 1:Create a statement using connection object
            Statement statement = connection.createStatement();

            // Step 2: Execute the query or update query
            statement.execute(createTableSQL);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createUsersTable() throws SQLException {
		createTableSQL = "CREATE TABLE users(\n"
				+ "    id varchar(36) not null primary key,\n"
				+ "    email varchar(255) not null,\n"
				+ "    name varchar(255) not null,\n"
				+ "    team varchar(36),\n"
				+ "    createdAt timestamp default CURRENT_TIMESTAMP,\n"
				+ "    modifiedAt timestamp default CURRENT_TIMESTAMP,\n"
				+ "    FOREIGN KEY (team) REFERENCES teams (id)\n"
				+ "    );";

        System.out.println(createTableSQL);
        try {
        	connection = MySQLJDBCUtil.getConnection();
            // Step 1:Create a statement using connection object
            Statement statement = connection.createStatement();

            // Step 2: Execute the query or update query
            statement.execute(createTableSQL);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void createStatusTable() throws SQLException {
		createTableSQL = "CREATE TABLE status(\n"
				+ "    id varchar(36) not null primary key,\n"
				+ "    name varchar(255) not null,\n"
				+ "    description varchar(255) not null,\n"
				+ "    createdAt timestamp default CURRENT_TIMESTAMP,\n"
				+ "    modifiedAt timestamp default CURRENT_TIMESTAMP\n"
				+ "    );";

        System.out.println(createTableSQL);
        try {
        	connection = MySQLJDBCUtil.getConnection();
            // Step 1:Create a statement using connection object
            Statement statement = connection.createStatement();

            // Step 2: Execute the query or update query
            statement.execute(createTableSQL);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createInstanceLogTable() throws SQLException {
		createTableSQL = "CREATE TABLE instance-logs(\n"
				+ "    id varchar(36) not null primary key,\n"
				+ "    reason varchar(255) not null,\n"
				+ "    instance varchar(36),\n"
				+ "    user varchar(36),\n"
				+ "    startTime timestamp default CURRENT_TIMESTAMP,\n"
				+ "    endTime timestamp default CURRENT_TIMESTAMP,\n"
				+ "    totalTime int default 0,\n"
				+ "    createdAt timestamp default CURRENT_TIMESTAMP,\n"
				+ "    modifiedAt timestamp default CURRENT_TIMESTAMP,\n"
				+ "    FOREIGN KEY (instance) REFERENCES instances (id),\n"
				+ "    FOREIGN KEY (user) REFERENCES users (id)\n"
				+ "    );";

        System.out.println(createTableSQL);
        try {
        	connection = MySQLJDBCUtil.getConnection();
            // Step 1:Create a statement using connection object
            Statement statement = connection.createStatement();

            // Step 2: Execute the query or update query
            statement.execute(createTableSQL);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createInstanceTable() throws SQLException {
		createTableSQL = "CREATE TABLE instances(\n"
				+ "    id varchar(36) not null primary key,\n"
				+ "    name varchar(255) not null,\n"
				+ "    status varchar(36),\n"
				+ "    team varchar(36),\n"
				+ "    createdAt timestamp default CURRENT_TIMESTAMP,\n"
				+ "    modifiedAt timestamp default CURRENT_TIMESTAMP,\n"
				+ "    FOREIGN KEY (status) REFERENCES status (id),\n"
				+ "    FOREIGN KEY (team) REFERENCES teams (id)\n"
				+ "    );";

        System.out.println(createTableSQL);
        try {
        	connection = MySQLJDBCUtil.getConnection();
            // Step 1:Create a statement using connection object
            Statement statement = connection.createStatement();

            // Step 2: Execute the query or update query
            statement.execute(createTableSQL);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
